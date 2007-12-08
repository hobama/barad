package barad.instrument;

import static barad.util.Properties.DEBUG;
import static barad.util.Properties.VERBOSE;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Stack;

import org.apache.log4j.Logger;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import barad.profiler.OpcodeToMnemonicMap;

public class SymbolicMethodAdapter implements MethodVisitor {
	private MethodVisitor mv;
	private Logger log;
	private Stack<HashSet<Integer>> modifiedVariablesForState;
	private HashMap<Label, Integer> backtrackLabels;
	private HashMap<Label, Integer> lookupSwichValues;
	private Stack<Label> switchDefaultLabels;
	private boolean reverseBranchConstraints = false;
	private Stack<Integer> indexOfVariablesUsedForSwitch;
	private BytecodeQueue bytecodeQueue;
	private Map<Integer, String> opcodeToMnemonicMap;
	
	public SymbolicMethodAdapter(MethodVisitor mv) {
		this.mv = mv;
		log = Logger.getLogger(this.getClass());
		modifiedVariablesForState = new Stack<HashSet<Integer>>();
		modifiedVariablesForState.push(new HashSet<Integer>());
		backtrackLabels = new HashMap<Label, Integer>();
		lookupSwichValues = new HashMap<Label, Integer>();
		indexOfVariablesUsedForSwitch = new Stack<Integer>();
		switchDefaultLabels = new Stack<Label>();
		bytecodeQueue = new BytecodeQueue();
		opcodeToMnemonicMap = OpcodeToMnemonicMap.getMap();
	}

	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		bytecodeQueue.enqueue("visitAnnotation", new String[]{desc, String.valueOf(visible)});
		if (DEBUG && VERBOSE) log.debug("VISIT ANNOTATION Name: " + desc + ", Visible: " + visible + ";");
		return mv.visitAnnotation(desc, visible);
	}

	public AnnotationVisitor visitAnnotationDefault() {
		bytecodeQueue.enqueue("visitAnnotationDefault", new String[]{});
		if (DEBUG && VERBOSE) log.debug("VISIT ANNOTATION" + ";");
		return mv.visitAnnotationDefault();
	}

	public void visitAttribute(Attribute attr) {
		bytecodeQueue.enqueue("visitAttribute", new String[]{attr.toString()});
		if(DEBUG && VERBOSE) log.debug("VISIT ATTRIBUTE: Atribute: " + attr.toString() + ";");
		mv.visitAttribute(attr);
	}

	public void visitCode() {
		if (DEBUG && VERBOSE) log.debug("VISIT CODE" + ";");
		writeCodeToCreateNewState();
		mv.visitCode();
	}

	public void visitEnd() {
		if (DEBUG && VERBOSE) log.debug("VISIT END" + ";");
		mv.visitEnd();
	}

	public void visitFieldInsn(int opcode, String owner, String name, String desc) {
		bytecodeQueue.enqueue("visitFieldInsn", new String[]{opcodeToMnemonicMap.get(opcode), owner, name, desc});
		if (opcode == Opcodes.PUTFIELD) {
			wirteCodeToStoreField(owner, name, desc);
			bytecodeQueue.enqueue(opcodeToMnemonicMap.get(opcode), new String[]{owner, name, desc});
		}	
		if (DEBUG && VERBOSE) log.debug("VISIT FIELD INSTRUCTION: Opcode: " + opcode + ", Owner: " + owner + ", Name: " + name + ", Descriptor: " + desc + ";");
		mv.visitFieldInsn(opcode, owner, name, modifyDescriptor(desc));
	}

	public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
		bytecodeQueue.enqueue("visitFrame", new String[]{String.valueOf(type), String.valueOf(nLocal) /*the rest is ignored for now*/});
		if (DEBUG && VERBOSE) log.debug("VISIT FRAME: Type: " + type + ", Local vriables#: " + nLocal + ", Stack elements#: " + nStack + ";");
		mv.visitFrame(type, nLocal, local, nStack, stack);
	}

	public void visitIincInsn(int var, int increment) {
		bytecodeQueue.enqueue("visitIincInsn", new String[]{String.valueOf(var), String.valueOf(increment)});
		if (DEBUG && VERBOSE) log.debug("VISIT INCREMENTAL INSTRUCTION: Variable: " + var + ", Increment: " + increment + ";");
		mv.visitIincInsn(var, increment);
	}

	public void visitInsn(int opcode) {
		bytecodeQueue.enqueue("visitInsn", new String[]{opcodeToMnemonicMap.get(opcode)});
		if (opcode == Opcodes.ICONST_0 || opcode == Opcodes.FCONST_0) {
			writeCodeToIntroduceSymbolicIntegerConstant(Opcodes.BIPUSH, 0);
		} else if (opcode == Opcodes.ICONST_1 || opcode == Opcodes.LCONST_1) {
			writeCodeToIntroduceSymbolicIntegerConstant(Opcodes.BIPUSH, 1);
		} else if (opcode == Opcodes.ICONST_2) {
			writeCodeToIntroduceSymbolicIntegerConstant(Opcodes.BIPUSH, 2);
		} else if (opcode == Opcodes.ICONST_3) {
			writeCodeToIntroduceSymbolicIntegerConstant(Opcodes.BIPUSH, 3);
		} else if (opcode == Opcodes.ICONST_4) {
			writeCodeToIntroduceSymbolicIntegerConstant(Opcodes.BIPUSH, 4);
		} else if (opcode == Opcodes.ICONST_5) {
			writeCodeToIntroduceSymbolicIntegerConstant(Opcodes.BIPUSH, 5);
		} else if (opcode == Opcodes.IADD) { 
			writeCodeToIntroduceSymbolicOperation(Names.IADD.getValue(), Names.INTEGER_OPERATION_SIGNATURE.getValue());
		} else if (opcode == Opcodes.IDIV) {
			writeCodeToIntroduceSymbolicOperation(Names.IDIV.getValue(), Names.INTEGER_OPERATION_SIGNATURE.getValue());
		} else if (opcode == Opcodes.IMUL) {
			writeCodeToIntroduceSymbolicOperation(Names.IMUL.getValue(), Names.INTEGER_OPERATION_SIGNATURE.getValue());
		} else if (opcode == Opcodes.ISUB) {
			writeCodeToIntroduceSymbolicOperation(Names.ISUB.getValue(), Names.INTEGER_OPERATION_SIGNATURE.getValue());
	    } else if (opcode == Opcodes.FADD) {
	    	writeCodeToIntroduceSymbolicOperation(Names.FADD.getValue(), Names.FLOAT_OPERATION_SIGNATURE.getValue());
	    } else if (opcode == Opcodes.FDIV) {
	    	writeCodeToIntroduceSymbolicOperation(Names.FDIV.getValue(), Names.FLOAT_OPERATION_SIGNATURE.getValue());
	    } else if (opcode == Opcodes.FMUL) {
	    	writeCodeToIntroduceSymbolicOperation(Names.FMUL.getValue(), Names.FLOAT_OPERATION_SIGNATURE.getValue());
	    } else if (opcode == Opcodes.FSUB) {
	    	writeCodeToIntroduceSymbolicOperation(Names.FSUB.getValue(), Names.FLOAT_OPERATION_SIGNATURE.getValue());
	    } else if (opcode == Opcodes.IRETURN || opcode == Opcodes.LRETURN || opcode == Opcodes.FRETURN ||
				   opcode == Opcodes.DRETURN || opcode == Opcodes.ARETURN || opcode == Opcodes.RETURN) {
			writeCodeToBacktrack();
			mv.visitInsn(opcode);
		} else if (opcode == Opcodes.FCMPG || opcode == Opcodes.FCMPL) {
			/*ignore*/
		} else if (opcode == Opcodes.FCONST_0 || opcode == Opcodes.DCONST_0) {
			writeCodeToIntroduceSymbolicFloatConstant(0.0f);
		} else if (opcode == Opcodes.FCONST_1 || opcode == Opcodes.DCONST_1) {
			writeCodeToIntroduceSymbolicFloatConstant(1.0f);
		} else if (opcode == Opcodes.FCONST_2) {
			writeCodeToIntroduceSymbolicFloatConstant(2);
		} else {
			mv.visitInsn(opcode);
		}
	}
	
	public void visitIntInsn(int opcode, int operand) {
		bytecodeQueue.enqueue("visitIntInsn", new String[]{opcodeToMnemonicMap.get(opcode), String.valueOf(operand)});
		if (opcode == Opcodes.BIPUSH || opcode == Opcodes.SIPUSH) {
			if (DEBUG && VERBOSE) log.debug("Integer constant:" + operand + " replaced by symbolic integer constant");
			writeCodeToIntroduceSymbolicIntegerConstant(opcode, operand);	
		} else {
			if (DEBUG && VERBOSE) log.debug("VISIT INTEGER INSTRUCTION: Opcode: " + opcode + ", Operand: " + operand + ";");
			mv.visitIntInsn(opcode, operand);
		}
	}
	
	public void visitJumpInsn(int opcode, Label label) {
		String[] lastOperationParameters = bytecodeQueue.getLatestEntry(0).getParameters();
		boolean floatComparison = lastOperationParameters[0].toUpperCase().equals("FCMPL") || lastOperationParameters[0].toUpperCase().equals("FCMPG");
		bytecodeQueue.enqueue("visitJumpInsn", new String[]{opcodeToMnemonicMap.get(opcode), label.toString()});
		if (opcode == Opcodes.IFEQ) {
			addBacktrackMarker(label);
			if (floatComparison) {
				writeCodeToIntroduceIfConstraint(Names.IF_FCMPEQ.getValue(), Names.BINARY_FLOAT_PATH_CONSTRAINT_SIGNATURE.getValue());
			} else if (lastOperationParameters[0].equals("INVOKEVIRTUAL") && lastOperationParameters[1].equals("java/lang/String") &&
					   lastOperationParameters[2].equals("equals") && lastOperationParameters[3].equals("(Ljava/lang/Object;)Z")) {
				writeCodeToIntroduceStringConstraint();
			} else {
				writeCodeToIntroduceIfConstraint(Names.IFEQ.getValue(), Names.UNARY_INTEGER_PATH_CONSTRAINT_SIGNATURE.getValue());
			}
		} else if (opcode == Opcodes.IFNE) {
			addBacktrackMarker(label);
			if (floatComparison) {
				writeCodeToIntroduceIfConstraint(Names.IF_FCMPNE.getValue(), Names.BINARY_FLOAT_PATH_CONSTRAINT_SIGNATURE.getValue());
			} else {
				writeCodeToIntroduceIfConstraint(Names.IFNE.getValue(), Names.BINARY_INTEGER_PATH_CONSTRAINT_SIGNATURE.getValue());
			}
		} else if (opcode == Opcodes.IFLT) {
			addBacktrackMarker(label);
			if (floatComparison) {
				writeCodeToIntroduceIfConstraint(Names.IF_FCMPLT.getValue(), Names.BINARY_FLOAT_PATH_CONSTRAINT_SIGNATURE.getValue());
			} else {
				writeCodeToIntroduceIfConstraint(Names.IFLT.getValue(), Names.BINARY_INTEGER_PATH_CONSTRAINT_SIGNATURE.getValue());
			}
		} else if (opcode == Opcodes.IFLE) {
			addBacktrackMarker(label);
			if (floatComparison) {
				writeCodeToIntroduceIfConstraint(Names.IF_FCMPLE.getValue(), Names.BINARY_FLOAT_PATH_CONSTRAINT_SIGNATURE.getValue());
			} else {
				writeCodeToIntroduceIfConstraint(Names.IFLE.getValue(), Names.BINARY_INTEGER_PATH_CONSTRAINT_SIGNATURE.getValue());
			}
		} else if (opcode == Opcodes.IFGT) {
			addBacktrackMarker(label);
			if (floatComparison) {
				writeCodeToIntroduceIfConstraint(Names.IF_FCMPGT.getValue(), Names.BINARY_FLOAT_PATH_CONSTRAINT_SIGNATURE.getValue());
			} else {
				writeCodeToIntroduceIfConstraint(Names.IFGT.getValue(), Names.BINARY_INTEGER_PATH_CONSTRAINT_SIGNATURE.getValue());
			}
		} else if (opcode == Opcodes.IFGE) {
			addBacktrackMarker(label);
			if (floatComparison) {
				writeCodeToIntroduceIfConstraint(Names.IF_FCMPGE.getValue(), Names.BINARY_FLOAT_PATH_CONSTRAINT_SIGNATURE.getValue());
			} else {
				writeCodeToIntroduceIfConstraint(Names.IFGE.getValue(), Names.BINARY_INTEGER_PATH_CONSTRAINT_SIGNATURE.getValue());
			}
		} else if (opcode == Opcodes.IF_ICMPEQ) {
			addBacktrackMarker(label);
			writeCodeToIntroduceIfConstraint(Names.IF_ICMPEQ.getValue(), Names.BINARY_INTEGER_PATH_CONSTRAINT_SIGNATURE.getValue());
		} else if (opcode == Opcodes.IF_ICMPGE) {
			addBacktrackMarker(label);
			writeCodeToIntroduceIfConstraint(Names.IF_ICMPGE.getValue(), Names.BINARY_INTEGER_PATH_CONSTRAINT_SIGNATURE.getValue());
		} else if (opcode == Opcodes.IF_ICMPGT) {
			addBacktrackMarker(label);
			writeCodeToIntroduceIfConstraint(Names.IF_ICMPGT.getValue(), Names.BINARY_INTEGER_PATH_CONSTRAINT_SIGNATURE.getValue());
		} else if (opcode == Opcodes.IF_ICMPLE) {
			addBacktrackMarker(label);
			writeCodeToIntroduceIfConstraint(Names.IF_ICMPLE.getValue(), Names.BINARY_INTEGER_PATH_CONSTRAINT_SIGNATURE.getValue());
		} else if (opcode == Opcodes.IF_ICMPLT) {
			addBacktrackMarker(label);
			writeCodeToIntroduceIfConstraint(Names.IF_ICMPLT.getValue(), Names.BINARY_INTEGER_PATH_CONSTRAINT_SIGNATURE.getValue());
		} else if (opcode == Opcodes.IF_ICMPNE) {
			addBacktrackMarker(label);
			writeCodeToIntroduceIfConstraint(Names.IF_ICMPNE.getValue(), Names.BINARY_INTEGER_PATH_CONSTRAINT_SIGNATURE.getValue());
		} else if (opcode == Opcodes.GOTO) {
			Integer backtrackSteps = backtrackLabels.get(label);
			if (backtrackSteps != null) {
				//Temporay remove if something breaks
				while (backtrackSteps > /*1*/0) {
					writeCodeToBacktrack();
					backtrackSteps--;
				}
				backtrackLabels.remove(label);
			}
			addBacktrackMarker(label);
			if (!switchDefaultLabels.contains(label)) {
				reverseBranchConstraints = true;
			}
		} else {
			mv.visitJumpInsn(opcode, label);
		}
	}
	
	public void visitLabel(Label label) {	
		bytecodeQueue.enqueue("visitLabel", new String[]{label.toString()});
		if (lookupSwichValues.size() > 0 && lookupSwichValues.containsKey(label)) {
			Integer value;
			if ((value = lookupSwichValues.remove(label)) != null) {
				writeCodeToCreateNewState();
				writeCodeToIntroduceSwitchConstraint(value);	
			}
		} else {	
			Integer count;
			if ((count = backtrackLabels.remove(label)) != null) {
				while (count > 0) {
					//reverse constrains only if this is the else
					if (reverseBranchConstraints && count == 1 && !switchDefaultLabels.remove(label)) {
						mv.visitMethodInsn(Opcodes.INVOKESTATIC, Names.PATH.getValue(), "reverseBranchConstraints", Names.REVERSE_BRANCH_CONSTRAINTS_SIGNATURE.getValue());
						writeCodeToGenerateConcerteValues();
						reverseBranchConstraints = false;
					}
					writeCodeToBacktrack();
					count--;
				}
			}
		}
		if (DEBUG && VERBOSE) log.debug("VISIT LABEL: Label: " + label.toString() + ";");
		mv.visitLabel(label);
	}

	public void visitLdcInsn(Object cst) {
		bytecodeQueue.enqueue("visitLdcInsn", new String[]{cst.toString()});
		if (cst instanceof Integer) {
			Integer value = (Integer)cst;
			writeCodeToIntroduceSymbolicIntegerConstant(Opcodes.LDC, value);
		} else if (cst instanceof Float) {
			Float value = (Float)cst;
			writeCodeToIntroduceSymbolicFloatConstant(value);
		} else if (cst instanceof Long) {
			
		} else if (cst instanceof Double) {
			
		} else if (cst instanceof String) {
			writeCodeToIntroduceSymbolicStringConstant((String)cst);
		} else if (cst instanceof Type) {
			mv.visitLdcInsn(cst);
		}
	}

	public void visitLineNumber(int line, Label start) {
		bytecodeQueue.enqueue("visitLineNumber", new String[]{String.valueOf(line), start.toString()});
		if (DEBUG && VERBOSE) log.debug("VISIT LINE: Number: " + line + ", Start: " + start.toString() + ";");
		mv.visitLineNumber(line, start);	
	}

	public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
		bytecodeQueue.enqueue("visitLocalVariable", new String[]{desc, signature, start.toString(), end.toString(), String.valueOf(index)});
		if (desc.equals("I") || desc.equals("J")) {
			mv.visitLocalVariable(name, modifyDescriptor(desc), signature, start, end, index);
			//writeCodeToIntroduceSymbolicIntegerVariable(index);
		} else if (desc.equals("F") || desc.equals("D")) {
			mv.visitLocalVariable(name, modifyDescriptor(desc), signature, start, end, index);
			//writeCodeToIntroduceSymbolicFloatVariable(index);
		} else if (desc.equals(Names.STRING.getValue())) {
			mv.visitLocalVariable(name, modifyDescriptor(desc), signature, start, end, index);
			//TODO: Make the length of string variable parametrized
			//writeCodeToIntroduceSymbolicStringVariable(index, 10);
		} else {
			mv.visitLocalVariable(name, desc, signature, start, end, index);
		}
	}

	public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
		bytecodeQueue.enqueue("visitLookupSwitchInsn", new String[]{dflt.toString() /*keys and labels would be added if needed*/});
		writeCodeToCreateNewState();
		writeCodeToIntroduceSwitchConstraint(keys[0]);	
		addBacktrackMarker(dflt);
		switchDefaultLabels.add(dflt);
		for (int i = 1; i < labels.length; i++) {
			lookupSwichValues.put(labels[i], keys[i]);
		}
		if (DEBUG && VERBOSE) log.debug("VISIT LOOKUP SWITCH INSTRUCTION: DefHandlerBlock: " + dflt.toString() + ";");
	}

	public void visitMaxs(int maxStack, int maxLocals) {
		if (DEBUG && VERBOSE) log.debug("VISIT MAX: MaxStack: " + maxStack + ", Maxlocals: " + maxLocals + ";");
		mv.visitMaxs(maxStack + 20, maxLocals);
	}

	public void visitMethodInsn(int opcode, String owner, String name, String desc) {
		bytecodeQueue.enqueue("visitMethodInsn", new String[]{opcodeToMnemonicMap.get(opcode), owner, name, desc});
		if (DEBUG && VERBOSE) log.debug("VISIT METHOD INSTRUCTION: Opcode: " + opcode + ", Owner: " + owner + ", Name: " + name + ", Descriptor: " + desc + ";");
		if (opcode == Opcodes.INVOKEVIRTUAL && owner.equals("java/lang/String")) {
			if (name.equals("equals") && desc.equals("(Ljava/lang/Object;)Z")) {
		    writeCodeToIntroduceSymbolicOperation(Names.EQUALS.getValue(), Names.STRING_PATH_CONSTRAINT_SIGNATURE.getValue());
			} else if (name.equals("substring") && desc.equals("(I)Ljava/lang/String;")) {
				mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, Names.SYMBOLICSTRING.getValue(), "substring", "(Lbarad/symboliclibrary/integers/ICONST;)Lbarad/symboliclibrary/string/StringInterface;");
			} else if (name.equals("substring") && desc.equals("(II)Ljava/lang/String")) {
				mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, Names.SYMBOLICSTRING.getValue(), "substring", "(II)Lbarad/symboliclibrary/string/StringInterface;");
			}
		} else {
			mv.visitMethodInsn(opcode, owner, name, modifyDescriptor(desc));
		}
	}

	public void visitMultiANewArrayInsn(String desc, int dims) {
		bytecodeQueue.enqueue("visitMultiANewArrayInsn", new String[]{desc, String.valueOf(dims)});
		if (DEBUG && VERBOSE) log.debug("VISIT MULTIANEW INSTRUCTION: Descriptor: " + desc + ", Dimensions: " + dims + ";");
		mv.visitMultiANewArrayInsn(desc, dims);
	}

	public AnnotationVisitor visitParameterAnnotation(int parameter, String desc, boolean visible) {
		bytecodeQueue.enqueue("visitParameterAnnotation", new String[]{String.valueOf(parameter), desc, String.valueOf(visible)});
		if (DEBUG && VERBOSE) log.debug("VISIT PARAMETER ANNOTATION: Parameter: " + parameter + ", Descriptor: " + desc + ", Visible: " + visible + ";");
		return mv.visitParameterAnnotation(parameter, desc, visible);
	}

	public void visitTableSwitchInsn(int min, int max, Label dflt, Label[] labels) {
		bytecodeQueue.enqueue("visitTableSwitchInsn", new String[]{String.valueOf(min), String.valueOf(max), dflt.toString() /*;labels ignored for now*/});
		if (DEBUG && VERBOSE) log.debug("VISIT TABLE SWITCH INSTRUCTION: Min: " + min + ", Max: " + max + ", DefHandlerBlock: " + dflt.toString() + ";");
		mv.visitTableSwitchInsn(min, max, dflt, labels);
	}

	public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
		bytecodeQueue.enqueue("visitTryCatchBlock", new String[]{start.toString(), end.toString(), handler.toString(),type});
		if (DEBUG && VERBOSE) log.debug("VISIT TRY/CATCH BLOCK INSTRUCTION: Start: " + start.toString() + ", End: " + end.toString() + ", Handler: " + handler.toString()  + ", Type: " + type + ";");
		mv.visitTryCatchBlock(start, end, handler, type);
	}

	public void visitTypeInsn(int opcode, String desc) {
		if (DEBUG && VERBOSE) log.debug("VISIT TYPE INSTRUCTION: Opcode: " + opcode + ", Descriptor: " + desc + ";");
		mv.visitTypeInsn(opcode, desc);
	}

	/**
	 * This method is fully implemented and covesrs all variable instructions.
	 * All primitive variables are replaced by symbolic equivalents which are
	 * reference types.
	 */
	public void visitVarInsn(int opcode, int var) {
		bytecodeQueue.enqueue("visitVarInsn", new String[]{opcodeToMnemonicMap.get(opcode), String.valueOf(var)});
		if (opcode == Opcodes.ISTORE || opcode == Opcodes.LSTORE || 
			opcode == Opcodes.FSTORE || opcode == Opcodes.DSTORE || 
			opcode == Opcodes.ASTORE) {
			mv.visitVarInsn(Opcodes.ASTORE, var);
			writeCodeToStoreLocalSymbolicVariable(var);
		} else if (opcode == Opcodes.ILOAD || opcode == Opcodes.LLOAD || 
				   opcode == Opcodes.FLOAD || opcode == Opcodes.DLOAD ||
				   opcode == Opcodes.ALOAD) {
			indexOfVariablesUsedForSwitch.push(var);
			mv.visitVarInsn(Opcodes.ALOAD, var);
		} else {
			mv.visitVarInsn(opcode, var);
		}
	}
	
	/**
	 * Add a label at which the program should backtrack. Note
	 * that at a label multiple backtracks are possible
	 * @param label The label
	 */
	private void addBacktrackMarker(Label label) {
		Integer value = backtrackLabels.get(label); 
		if (value == null) {
			backtrackLabels.put(label, 1);
		} else {
			backtrackLabels.put(label, value + 1);
		}
	}
	
	/**
	 * Writes bytecode that generates a new program state
	 */
	private void writeCodeToCreateNewState() {
		modifiedVariablesForState.push(new HashSet<Integer>());
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, Names.PATH.getValue(), "createNewState", "()V");
	}
	
	/**
	 * Writes code to replace integer constant with symbolic integer constant
	 * @param opcode The bytecode instruction used to add the integer to the stack.
	 * @param operand The integer value to be replaced
	 */
	private void writeCodeToIntroduceSymbolicIntegerConstant(int opcode, int operand) {
		mv.visitTypeInsn(Opcodes.NEW, Names.ICONST.getValue());
		mv.visitInsn(Opcodes.DUP);
		mv.visitIntInsn(opcode, operand);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, Names.ICONST.getValue(), "<init>", Names.ICONST_SIGNATURE.getValue());
	}
	
	/**
	 * Writes code to replace float constant with symbolic float constant
	 * @param operand The float value to be replaced
	 */
	private void writeCodeToIntroduceSymbolicFloatConstant(float operand) {
		mv.visitTypeInsn(Opcodes.NEW, Names.FCONST.getValue());
		mv.visitInsn(Opcodes.DUP);
		mv.visitLdcInsn(operand);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, Names.FCONST.getValue(), "<init>", Names.FCONST_SIGNATURE.getValue());
	}
	
	private void writeCodeToIntroduceSymbolicIntegerVariable(int index) {
		mv.visitTypeInsn(Opcodes.NEW, Names.IVAR.getValue());
		mv.visitInsn(Opcodes.DUP);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, Names.IVAR.getValue(), "<init>", Names.IVAR_SIGNATURE.getValue());
		mv.visitVarInsn(Opcodes.ASTORE, index);
	}
	
	
	private void writeCodeToIntroduceSymbolicStringVariable(int index, int length) {
		mv.visitTypeInsn(Opcodes.NEW, Names.SYMBOLICSTRING.getValue());
		mv.visitInsn(Opcodes.DUP);
		mv.visitIntInsn(Opcodes.BIPUSH, length);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, Names.SYMBOLICSTRING.getValue(), "<init>", Names.SYMBOLICSTRING_VAR_SIGNATURE.getValue());
		mv.visitVarInsn(Opcodes.ASTORE, index);
	}
	
	private void writeCodeToIntroduceSymbolicFloatVariable(int index) {
		mv.visitTypeInsn(Opcodes.NEW, Names.FVAR.getValue());
		mv.visitInsn(Opcodes.DUP);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, Names.FVAR.getValue(), "<init>", Names.FVAR_SIGNATURE.getValue());
		mv.visitVarInsn(Opcodes.ASTORE, index);
	}
	
	/**
	 * Writes code to replace string entity with symbolic string constant
	 * @param opcode The bytecode instruction used to add the string constant to the stack.
	 * @param operand The string value to be replaced
	 */
	private void writeCodeToIntroduceSymbolicStringConstant(String string) {
		mv.visitTypeInsn(Opcodes.NEW, Names.SYMBOLICSTRING.getValue());
		mv.visitInsn(Opcodes.DUP);
		mv.visitLdcInsn(string);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, Names.SYMBOLICSTRING.getValue(), "<init>", Names.SYMBOLICSTRING_CONST_SIGNATURE.getValue());
	}
	
	/**
	 * Writes bytecode to store modified local variable
	 * @param var The index of the variable in local variables table
	 */
	private void writeCodeToStoreLocalSymbolicVariable(int var) {
		modifiedVariablesForState.peek().add(var);
		mv.visitVarInsn(Opcodes.ALOAD, var);
		mv.visitIntInsn(Opcodes.BIPUSH, var);
    	mv.visitMethodInsn(Opcodes.INVOKESTATIC, Names.PATH.getValue(), "addLocalVariable", Names.PATH_ADD_LOCAL_VARIABLE_SIGNATURE.getValue());
	}
	
	/**
	 * Writes bytecode to store modified local field
	 * @param owner Name of the wner class
	 * @param name Name of the field
	 * @param desc Descriptor of the field
	 */
	private void wirteCodeToStoreField(String owner, String name, String desc) {
		mv.visitFieldInsn(Opcodes.GETFIELD, owner, name, desc);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, Names.PATH.getValue(), "addLocalField", Names.PATH_ADD_LOCAL_FIELD_SIGNATURE.getValue());
	}
	
	/**
	 * Writes bytecode to backtrack i.e. to restore all modified local 
	 * variables and remove the current state
	 * TODO: Should restore the modified class fields also
	 */
	private void writeCodeToBacktrack() {
		if (DEBUG) {
			writeCodeToDisplayMessage("Backtracking...");
		}
		if (modifiedVariablesForState.size() > 0) {
			for (Integer i: modifiedVariablesForState.peek()) {
				mv.visitIntInsn(Opcodes.BIPUSH, i);
				mv.visitMethodInsn(Opcodes.INVOKESTATIC, Names.PATH.getValue(), "getVariableValue", Names.PATH_ADD_GET_VARIABLE_VALUE_SIGNATURE.getValue());
				mv.visitVarInsn(Opcodes.ASTORE, i);
			}
			modifiedVariablesForState.pop();
		}
		//writeCodeToGenerateConcerteValues();
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, Names.PATH.getValue(), "removeLastState", Names.PATH_REMOVE_LAST_STATE_SIGNATURE.getValue());
	}
	
	private void writeCodeToGenerateConcerteValues() {
		if (DEBUG) {
			writeCodeToDisplayMessage("Backtracking completed");
			writeCodeToDisplayMessage("Genrating concrete values....");
		}
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, Names.PATH.getValue(), "generateInputs", Names.PATH_GENERATE_INPUTS_SIGNATURE.getValue());
		if (DEBUG) {
			writeCodeToDisplayMessage("Genrating concrete values completed");
		}
	}
	
	/**
	 * Writtes code to display a message. For testing purposes.
	 * @param message The text to be displayed
	 */
	private void writeCodeToDisplayMessage(String message) {
		if (DEBUG) {
			mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
			mv.visitLdcInsn(message);
			mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");
		}
	}
	
	private void writeCodeToIntroduceStringConstraint() {
		writeCodeToCreateNewState();
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, Names.PATH.getValue(), "addBranchConstraint", Names.PATH_ADD_BRANCH_CONSTRAINT.getValue());
	
	}
	
	/**
	 * Writes bytecode that replaces jump instruction with symbolic one.
	 * The generated code adds the condition of the jump instruction as
	 * a symbolic path condition. 
	 * @param symbolicClassName The name of a symbolic class used to replace the jump instruction
	 * @param Descriptor of the symbolic operation's constructor
	 */
	private void writeCodeToIntroduceIfConstraint (String symbolicClassName, String descriptor) {
		writeCodeToCreateNewState();
		writeCodeToIntroduceSymbolicOperation(symbolicClassName, descriptor);
	    mv.visitMethodInsn(Opcodes.INVOKESTATIC, Names.PATH.getValue(), "addBranchConstraint", Names.PATH_ADD_BRANCH_CONSTRAINT.getValue());
	    writeCodeToGenerateConcerteValues();
	}
	
	/**
	 * Writes bytecode that that adds switch constraint to the path constraints
	 * @param index Value of the control variable that should be satisfied 
	 */
	private void writeCodeToIntroduceSwitchConstraint(int index) {
		mv.visitTypeInsn(Opcodes.NEW, Names.IF_ICMPNE.getValue());
		mv.visitInsn(Opcodes.DUP);
		visitVarInsn(Opcodes.ALOAD, indexOfVariablesUsedForSwitch.peek());
		writeCodeToIntroduceSymbolicIntegerConstant(Opcodes.BIPUSH, index);
	    mv.visitMethodInsn(Opcodes.INVOKESPECIAL, Names.IF_ICMPNE.getValue(), "<init>", Names.BINARY_INTEGER_PATH_CONSTRAINT_SIGNATURE.getValue());
	    mv.visitMethodInsn(Opcodes.INVOKESTATIC, Names.PATH.getValue(), "addBranchConstraint", Names.PATH_ADD_BRANCH_CONSTRAINT.getValue());
	    writeCodeToGenerateConcerteValues();
	}
	
	/**
	 * Writes bytecode that replaces bytecode instruction with symbolic one.
	 * @param symbolicClassName The name of a symbolic class used to replace the bytecode instruction
	 * @param descriptor Descriptor of the symbolic operation's constructor
	 */
	private void writeCodeToIntroduceSymbolicOperation(String symbolicClassName, String descriptor) {
		mv.visitTypeInsn(Opcodes.NEW, symbolicClassName);
		mv.visitInsn(Opcodes.DUP);
		mv.visitInsn(Opcodes.DUP2_X2);
		mv.visitInsn(Opcodes.POP);
		mv.visitInsn(Opcodes.POP);
	    mv.visitMethodInsn(Opcodes.INVOKESPECIAL, symbolicClassName, "<init>", descriptor);
	}
	
	/**
	 * Modify a method/field descriptor by replacing all primitive class names
	 * with corresponding symbolic ones (interfaces in this implementation)
	 * @param desc Field/Method descriptor
	 * @return Modified descriptor
	 */
	public static String modifyDescriptor(String desc) {
		StringBuilder resultBuilder = new StringBuilder();
		String[] names = null;
		int index = desc.indexOf('(');
		String temp = desc;
		if (index > -1) {
			resultBuilder.append('(');
			temp = temp.substring(1);
			names = temp.split("[)]"); 
		} else {
			names = new String[]{temp};
		}
		for (int i = 0; i < names.length; i++) {
			String[] classNames = names[i].split(";");
			//BUG
			for (String s: classNames) {
				while (s.length() > 1 && s.charAt(0) != 'L') {
					resultBuilder.append(updateClassName(String.valueOf(s.charAt(0))));	
					s = s.substring(1);
				}
				if (s.length() > 0) {
					resultBuilder.append(updateClassName(s));
				}
			}
			if (index > -1) {
				resultBuilder.append(')');
				index = -1;
			}	
		}
		return resultBuilder.toString();
	}

	/**
	 * Replace the name of a primitive type with its symbolic version.
	 * If the class is not a primitive no modification to the name are made
	 * @param name Class name 
	 * @return Class name. Same if class is not primitive type. the correspoding
	 *         symbolic class name in case of primitive type
	 */
	private static String updateClassName(String name) {
		String newName = name;
		if (name.equals("I") || name.equals("J")) {
			newName = Names.INTEGER_INTERFACE.getValue();
		} else if (name.equals("F") || name.equals("D")) {
			newName = Names.FLOAT_INTERFACE.getValue();
		} else if (name.equals("Ljava/lang/String")) {
			newName = Names.SYMBOLICSTRING_INTRFACE.getValue();
		} else if (false) {
			//add conditions fo all basic interfaces i.e DoubleInterface and so on
		} else if (!name.equals("") && !name.equals("V") && !name.equals("Z")) {
			newName = newName + ";";
		}
		return newName;
	}
	
}
