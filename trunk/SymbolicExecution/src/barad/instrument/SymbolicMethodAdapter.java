package barad.instrument;

import static barad.util.Properties.DEBUG;
import static barad.util.Properties.VERBOSE;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

import org.apache.log4j.Logger;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class SymbolicMethodAdapter implements MethodVisitor {
	private static final String IVAR = "barad/symboliclibrary/integers/IVAR";
	private static final String IVAR_SIGNATURE = "()V";
	private static final String ICONST = "barad/symboliclibrary/integers/ICONST";
	private static final String ICONST_SIGNATURE = "(I)V";
	private static final String INTEGER_INTERFACE = "Lbarad/symboliclibrary/integers/IntegerInterface;";
	//============================================================================================
	private static final String FVAR = "barad/symboliclibrary/floats/FVAR";
	private static final String FVAR_SIGNATURE = "()V";
	private static final String FCONST = "barad/symboliclibrary/floats/FCONST";
	private static final String FCONST_SIGNATURE = "(F)V";
	private static final String FLOAT_INTERFACE = "Lbarad/symboliclibrary/floats/FloatInterface;";
	//============================================================================================
	private static final String PATH = "barad/symboliclibrary/path/Path";
	private static final String PATH_ADD_LOCAL_FIELD_SIGNATURE = "(Ljava/lang/Object;Ljava/lang/String;)V";
	private static final String PATH_ADD_LOCAL_VARIABLE_SIGNATURE = "(Ljava/lang/Object;I)Ljava/lang/Object;";
	private static final String PATH_ADD_GET_VARIABLE_VALUE_SIGNATURE = "(I)Ljava/lang/Object;";
	private static final String PATH_ADD_BRANCH_CONSTRAINT = "(Ljava/lang/Object;)Ljava/lang/Object;";
	private static final String PATH_REMOVE_LAST_STATE_SIGNATURE = "()V";
	private static final String REVERSE_BRANCH_CONSTRAINTS_SIGNATURE = "()V";
	private static final String IFEQ  = "barad/symboliclibrary/path/IFEQ";
	private static final String IFNE  = "barad/symboliclibrary/path/IFNE";
	private static final String IFLT  = "barad/symboliclibrary/path/IFLT";
	private static final String IFLE  = "barad/symboliclibrary/path/IFLE";
	private static final String IFGT  = "barad/symboliclibrary/path/IFGT";
	private static final String IFGE  = "barad/symboliclibrary/path/IFGE";
	private static final String IF_ICMPEQ = "barad/symboliclibrary/path/IF_ICMPEQ";
	private static final String IF_ICMPGE = "barad/symboliclibrary/path/IF_ICMPGE";
	private static final String IF_ICMPGT = "barad/symboliclibrary/path/IF_ICMPGT";
	private static final String IF_ICMPLE = "barad/symboliclibrary/path/IF_ICMPLE";
	private static final String IF_ICMPLT = "barad/symboliclibrary/path/IF_ICMPLT";
	private static final String IF_ICMPNE = "barad/symboliclibrary/path/IF_ICMPNE";
	private static final String INTEGER_PATH_CONSTRAINT_SIGNATURE = "(Lbarad/symboliclibrary/integers/IntegerInterface;Lbarad/symboliclibrary/integers/IntegerInterface;)V";
	private static final String EQUALS = "barad/symboliclibrary/string/Equals";
	private static final String STRING_PATH_CONSTRAINT_SIGNATURE = "(Lbarad/symboliclibrary/string/StringInterface;Lbarad/symboliclibrary/string/StringInterface;)V";
	//============================================================================================
	private static final String IADD = "barad/symboliclibrary/integers/IADD";
	private static final String IDIV = "barad/symboliclibrary/integers/IDIV";
	private static final String IMUL = "barad/symboliclibrary/integers/IMUL";
	private static final String ISUB = "barad/symboliclibrary/integers/ISUB";
	private static final String INTEGER_OPERATION_SIGNATURE = "(Lbarad/symboliclibrary/integers/IntegerInterface;Lbarad/symboliclibrary/integers/IntegerInterface;)V";
	//============================================================================================
	private static final String SYMBOLICSTRING = "barad/symboliclibrary/string/SymbolicString";
	private static final String SYMBOLICSTRING_SIGNATURE = "(Ljava/lang/String;)V";
	private static final String SYMBOLICSTRING_INTRFACE = "Lbarad/symboliclibrary/string/StringInterface;";
	private static final String STRING = "Ljava/lang/String;";
	
	private MethodVisitor mv;
	private Logger log;
	private Stack<HashSet<Integer>> modifiedVariablesForState;
	private HashMap<Label, Integer> backtrackLabels;
	private HashMap<Label, Integer> lookupSwichValues;
	private Stack<Label> switchDefaultLabels; 
	private boolean lastMethodInstructionIsStringComparison = false;
	private boolean reverseBranchConstraints = false;
	private Stack<Integer> indexOfVariablesUsedForSwitch;
	
	public SymbolicMethodAdapter(MethodVisitor mv) {
		this.mv = mv;
		this.log = Logger.getLogger(this.getClass());
		this.modifiedVariablesForState = new Stack<HashSet<Integer>>();
		this.modifiedVariablesForState.push(new HashSet<Integer>());
		this.backtrackLabels = new HashMap<Label, Integer>();
		this.lookupSwichValues = new HashMap<Label, Integer>();
		this.indexOfVariablesUsedForSwitch = new Stack<Integer>();
		this.switchDefaultLabels = new Stack<Label>();
	}

	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		if (DEBUG && VERBOSE) log.debug("VISIT ANNOTATION Name: " + desc + ", Visible: " + visible + ";");
		return mv.visitAnnotation(desc, visible);
	}

	public AnnotationVisitor visitAnnotationDefault() {
		if (DEBUG && VERBOSE) log.debug("VISIT ANNOTATION" + ";");
		return mv.visitAnnotationDefault();
	}

	public void visitAttribute(Attribute attr) {
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
		if (opcode == Opcodes.PUTFIELD) {
			wirteCodeToStoreField(owner, name, desc);
		}	
		if (DEBUG && VERBOSE) log.debug("VISIT FIELD INSTRUCTION: Opcode: " + opcode + ", Owner: " + owner + ", Name: " + name + ", Descriptor: " + desc + ";");
		mv.visitFieldInsn(opcode, owner, name, modifyDescriptor(desc));
		lastMethodInstructionIsStringComparison = false;
	}

	public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
		if (DEBUG && VERBOSE) log.debug("VISIT FRAME: Type: " + type + ", Local vriables#: " + nLocal + ", Stack elements#: " + nStack + ";");
		mv.visitFrame(type, nLocal, local, nStack, stack);
	}

	public void visitIincInsn(int var, int increment) {
		if (DEBUG && VERBOSE) log.debug("VISIT INCREMENTAL INSTRUCTION: Variable: " + var + ", Increment: " + increment + ";");
		mv.visitIincInsn(var, increment);
		lastMethodInstructionIsStringComparison = false;
	}

	public void visitInsn(int opcode) {
		if (opcode == Opcodes.ICONST_0 ) {
			writeCodeToIntroduceSymbolicIntegerConstant(Opcodes.BIPUSH, 0);
		} else if (opcode == Opcodes.ICONST_1) {
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
			writeCodeToIntroduceSymbolicOperation(IADD, INTEGER_OPERATION_SIGNATURE);
		} else if (opcode == Opcodes.IDIV) {
			writeCodeToIntroduceSymbolicOperation(IDIV, INTEGER_OPERATION_SIGNATURE);
		} else if (opcode == Opcodes.IMUL) {
			writeCodeToIntroduceSymbolicOperation(IMUL, INTEGER_OPERATION_SIGNATURE);
		} else if (opcode == Opcodes.ISUB) {
			writeCodeToIntroduceSymbolicOperation(ISUB, INTEGER_OPERATION_SIGNATURE);
	    } else if (opcode == Opcodes.IRETURN || opcode == Opcodes.LRETURN || opcode == Opcodes.FRETURN ||
				   opcode == Opcodes.DRETURN || opcode == Opcodes.ARETURN || opcode == Opcodes.RETURN) {
			writeCodeToBacktrack();
			mv.visitInsn(opcode);
		} else {
			mv.visitInsn(opcode);
		}
		lastMethodInstructionIsStringComparison = false;
	}
	
	public void visitIntInsn(int opcode, int operand) {
		if (opcode == Opcodes.BIPUSH || opcode == Opcodes.SIPUSH) {
			if (DEBUG && VERBOSE) log.debug("Integer constant:" + operand + " replaced by symbolic integer constant");
			writeCodeToIntroduceSymbolicIntegerConstant(opcode, operand);	
		} else {
			if (DEBUG && VERBOSE) log.debug("VISIT INTEGER INSTRUCTION: Opcode: " + opcode + ", Operand: " + operand + ";");
			mv.visitIntInsn(opcode, operand);
		}
		lastMethodInstructionIsStringComparison = false;
	}
	
	public void visitJumpInsn(int opcode, Label label) {
		if (opcode == Opcodes.IFEQ) {
			addBacktrackMarker(label);
			if (lastMethodInstructionIsStringComparison) {
				writeCodeToIntroduceStringConstraint();
			} else {
				if (DEBUG && VERBOSE) log.debug("IFEQ replaced by Symbolic IFEQ");
				writeCodeToIntroduceIfConstraint(IFEQ, INTEGER_PATH_CONSTRAINT_SIGNATURE);
			}
		} else if (opcode == Opcodes.IFNE) {
			if (DEBUG && VERBOSE) log.debug("IFNE replaced by Symbolic IFNE");
			addBacktrackMarker(label);
			writeCodeToIntroduceIfConstraint(IFNE, INTEGER_PATH_CONSTRAINT_SIGNATURE);
		} else if (opcode == Opcodes.IFLT) {
			if (DEBUG && VERBOSE) log.debug("IFLT replaced by Symbolic IFLT");
			addBacktrackMarker(label);
			writeCodeToIntroduceIfConstraint(IFLT, INTEGER_PATH_CONSTRAINT_SIGNATURE);
		} else if (opcode == Opcodes.IFLE) {
			if (DEBUG && VERBOSE) log.debug("IFLE replaced by Symbolic IFLE");
			addBacktrackMarker(label);
			writeCodeToIntroduceIfConstraint(IFLE, INTEGER_PATH_CONSTRAINT_SIGNATURE);
		} else if (opcode == Opcodes.IFGT) {
			if (DEBUG && VERBOSE) log.debug("IFGT replaced by Symbolic IFGT");
			addBacktrackMarker(label);
			writeCodeToIntroduceIfConstraint(IFGT, INTEGER_PATH_CONSTRAINT_SIGNATURE);
		} else if (opcode == Opcodes.IFGE) {
			if (DEBUG && VERBOSE) log.debug("IFGE replaced by Symbolic IFGE");
			addBacktrackMarker(label);
			writeCodeToIntroduceIfConstraint(IFGE, INTEGER_PATH_CONSTRAINT_SIGNATURE);
		} else if (opcode == Opcodes.IF_ICMPEQ) {
			if (DEBUG && VERBOSE) log.debug("IF_ICMPEQ replaced by Symbolic IF_ICMPEQ");
			addBacktrackMarker(label);
			writeCodeToIntroduceIfConstraint(IF_ICMPEQ, INTEGER_PATH_CONSTRAINT_SIGNATURE);
		} else if (opcode == Opcodes.IF_ICMPGE) {
			if (DEBUG && VERBOSE) log.debug("IF_ICMPGE replaced by Symbolic IF_ICMPGE");
			addBacktrackMarker(label);
			writeCodeToIntroduceIfConstraint(IF_ICMPGE, INTEGER_PATH_CONSTRAINT_SIGNATURE);
		} else if (opcode == Opcodes.IF_ICMPGT) {
			if (DEBUG && VERBOSE) log.debug("IF_ICMPGT replaced by Symbolic IF_ICMPGT");
			addBacktrackMarker(label);
			writeCodeToIntroduceIfConstraint(IF_ICMPGT, INTEGER_PATH_CONSTRAINT_SIGNATURE);
		} else if (opcode == Opcodes.IF_ICMPLE) {
			if (DEBUG && VERBOSE) log.debug("IF_ICMPLE replaced by Symbolic IF_ICMPLE");
			addBacktrackMarker(label);
			writeCodeToIntroduceIfConstraint(IF_ICMPLE, INTEGER_PATH_CONSTRAINT_SIGNATURE);
		} else if (opcode == Opcodes.IF_ICMPLT) {
			if (DEBUG && VERBOSE) log.debug("IF_ICMPLT replaced by Symbolic IF_ICMPLT");
			addBacktrackMarker(label);
			writeCodeToIntroduceIfConstraint(IF_ICMPLT, INTEGER_PATH_CONSTRAINT_SIGNATURE);
		} else if (opcode == Opcodes.IF_ICMPNE) {
			if (DEBUG && VERBOSE) log.debug("IF_ICMPNE replaced by Symbolic IF_ICMPNE");
			addBacktrackMarker(label);
			writeCodeToIntroduceIfConstraint(IF_ICMPNE, INTEGER_PATH_CONSTRAINT_SIGNATURE);
		} else if (opcode == Opcodes.GOTO) {
			Integer backtrackSteps = backtrackLabels.get(label);
			if (backtrackSteps != null) {
				//Temporay remove if something breaks
				while (backtrackSteps > 1/*0*/) {
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
			if (DEBUG && VERBOSE) log.debug("VISIT JUMP INSTRUCTION: Opcode: " + opcode + ", Label: " + label.toString() + ";");
			mv.visitJumpInsn(opcode, label);
		}
		lastMethodInstructionIsStringComparison = false;
	}
	
	public void visitLabel(Label label) {	
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
						mv.visitMethodInsn(Opcodes.INVOKESTATIC, PATH, "reverseBranchConstraints", REVERSE_BRANCH_CONSTRAINTS_SIGNATURE);
						reverseBranchConstraints = false;
					}
					writeCodeToBacktrack();
					count--;
				}
			}
		}
		if (DEBUG && VERBOSE) log.debug("VISIT LABEL: Label: " + label.toString() + ";");
		mv.visitLabel(label);
		lastMethodInstructionIsStringComparison = false;
	}

	public void visitLdcInsn(Object cst) {
		if (DEBUG && VERBOSE) log.debug("VISIT LDC INSTRUCTION: Object: " + cst.toString() + ";");
	
		if (cst instanceof Integer) {
			Integer value = (Integer)cst;
			writeCodeToIntroduceSymbolicIntegerConstant(Opcodes.LDC, value);
		} else if (cst instanceof Float) {
			
		} else if (cst instanceof Long) {
			
		} else if (cst instanceof Double) {
			
		} else if (cst instanceof String) {
			writeCodeToIntroduceSymbolicStringConstant((String)cst);
		} else if (cst instanceof Type) {
			mv.visitLdcInsn(cst);
		}
		lastMethodInstructionIsStringComparison = false;
	}

	public void visitLineNumber(int line, Label start) {
		if (DEBUG && VERBOSE) log.debug("VISIT LINE: Number: " + line + ", Start: " + start.toString() + ";");
		mv.visitLineNumber(line, start);	
	}

	public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
		if (desc.equals("I")) {
			mv.visitLocalVariable(name, modifyDescriptor(desc), signature, start, end, index);
			writeCodeToIntroduceSymbolicIntegerVariable(index);
		} else if (desc.equals(STRING)) {
			mv.visitLocalVariable(name, modifyDescriptor(desc), signature, start, end, index);
			writeCodeToIntroduceSymbolicStringVariable(index);
		} else {
			mv.visitLocalVariable(name, desc, signature, start, end, index);
		}
		lastMethodInstructionIsStringComparison = false;
	}

	public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
		writeCodeToCreateNewState();
		writeCodeToIntroduceSwitchConstraint(keys[0]);	
		addBacktrackMarker(dflt);
		switchDefaultLabels.add(dflt);
		for (int i = 1; i < labels.length; i++) {
			lookupSwichValues.put(labels[i], keys[i]);
		}
		if (DEBUG && VERBOSE) log.debug("VISIT LOOKUP SWITCH INSTRUCTION: DefHandlerBlock: " + dflt.toString() + ";");
		lastMethodInstructionIsStringComparison = false;
	}

	public void visitMaxs(int maxStack, int maxLocals) {
		if (DEBUG && VERBOSE) log.debug("VISIT MAX: MaxStack: " + maxStack + ", Maxlocals: " + maxLocals + ";");
		mv.visitMaxs(maxStack + 20, maxLocals);
	}

	public void visitMethodInsn(int opcode, String owner, String name, String desc) {
		if (DEBUG && VERBOSE) log.debug("VISIT METHOD INSTRUCTION: Opcode: " + opcode + ", Owner: " + owner + ", Name: " + name + ", Descriptor: " + desc + ";");
		lastMethodInstructionIsStringComparison = false;
		if (opcode == Opcodes.INVOKEVIRTUAL && owner.equals("java/lang/String")) {
			if (name.equals("equals") && desc.equals("(Ljava/lang/Object;)Z")) {
		    writeCodeToIntroduceSymbolicOperation(EQUALS, STRING_PATH_CONSTRAINT_SIGNATURE);
			lastMethodInstructionIsStringComparison = true;
			} else if (name.equals("substring") && desc.equals("(I)Ljava/lang/String;")) {
				mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SYMBOLICSTRING, "substring", "(Lbarad/symboliclibrary/integers/ICONST;)Lbarad/symboliclibrary/string/StringInterface;");
			} else if (name.equals("substring") && desc.equals("(II)Ljava/lang/String")) {
				mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, SYMBOLICSTRING, "substring", "(II)Lbarad/symboliclibrary/string/StringInterface;");
			}
		} else {
			mv.visitMethodInsn(opcode, owner, name, modifyDescriptor(desc));
		}
	}

	public void visitMultiANewArrayInsn(String desc, int dims) {
		if (DEBUG && VERBOSE) log.debug("VISIT MULTIANEW INSTRUCTION: Descriptor: " + desc + ", Dimensions: " + dims + ";");
		mv.visitMultiANewArrayInsn(desc, dims);
		lastMethodInstructionIsStringComparison = false;
	}

	public AnnotationVisitor visitParameterAnnotation(int parameter, String desc, boolean visible) {
		if (DEBUG && VERBOSE) log.debug("VISIT PARAMETER ANNOTATION: Parameter: " + parameter + ", Descriptor: " + desc + ", Visible: " + visible + ";");
		lastMethodInstructionIsStringComparison = false;
		return mv.visitParameterAnnotation(parameter, desc, visible);
	}

	public void visitTableSwitchInsn(int min, int max, Label dflt, Label[] labels) {
		if (DEBUG && VERBOSE) log.debug("VISIT TABLE SWITCH INSTRUCTION: Min: " + min + ", Max: " + max + ", DefHandlerBlock: " + dflt.toString() + ";");
		mv.visitTableSwitchInsn(min, max, dflt, labels);
		lastMethodInstructionIsStringComparison = false;
	}

	public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
		if (DEBUG && VERBOSE) log.debug("VISIT TRY/CATCH BLOCK INSTRUCTION: Start: " + start.toString() + ", End: " + end.toString() + ", Handler: " + handler.toString()  + ", Type: " + type + ";");
		mv.visitTryCatchBlock(start, end, handler, type);
		lastMethodInstructionIsStringComparison = false;
	}

	public void visitTypeInsn(int opcode, String desc) {
		if (DEBUG && VERBOSE) log.debug("VISIT TYPE INSTRUCTION: Opcode: " + opcode + ", Descriptor: " + desc + ";");
		mv.visitTypeInsn(opcode, desc);
		lastMethodInstructionIsStringComparison = false;
	}

	/**
	 * This method is fully implemented and covesrs all variable instructions.
	 * All primitive variables are replaced by symbolic equivalents which are
	 * reference types.
	 */
	public void visitVarInsn(int opcode, int var) {
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
		lastMethodInstructionIsStringComparison = false;
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
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, PATH, "createNewState", "()V");
	}
	
	/**
	 * Writes code to replace integer constant with symbolic integer constant
	 * @param opcode The bytecode instruction used to add the integer to the stack.
	 * @param operand The integer value to be replaced
	 */
	private void writeCodeToIntroduceSymbolicIntegerConstant(int opcode, int operand) {
		mv.visitTypeInsn(Opcodes.NEW, ICONST);
		mv.visitInsn(Opcodes.DUP);
		mv.visitIntInsn(opcode, operand);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, ICONST, "<init>", ICONST_SIGNATURE);
	}
	
	/**
	 * Writes code to replace float constant with symbolic float constant
	 * @param opcode The bytecode instruction used to add the float to the stack.
	 * @param operand The float value to be replaced
	 */
	private void writeCodeToIntroduceSymbolicFloatConstant(int opcode, int operand) {
		mv.visitTypeInsn(Opcodes.NEW, ICONST);
		mv.visitInsn(Opcodes.DUP);
		mv.visitIntInsn(opcode, operand);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, ICONST, "<init>", ICONST_SIGNATURE);
	}
	
	private void writeCodeToIntroduceSymbolicIntegerVariable(int index) {
		mv.visitTypeInsn(Opcodes.NEW, IVAR);
		mv.visitInsn(Opcodes.DUP);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, IVAR, "<init>", IVAR_SIGNATURE);
		mv.visitVarInsn(Opcodes.ASTORE, index);
	}
	
	private void writeCodeToIntroduceSymbolicStringVariable(int index) {
		//mv.visitTypeInsn(Opcodes.NEW, FVAR);
		//mv.visitInsn(Opcodes.DUP);
		//mv.visitMethodInsn(Opcodes.INVOKESPECIAL, FVAR, "<init>", IVAR_SIGNATURE);
		//mv.visitVarInsn(Opcodes.ASTORE, index);
	}
	
	/**
	 * Writes code to replace string entity with symbolic string constant
	 * @param opcode The bytecode instruction used to add the string constant to the stack.
	 * @param operand The string value to be replaced
	 */
	private void writeCodeToIntroduceSymbolicStringConstant(String string) {
		mv.visitTypeInsn(Opcodes.NEW, SYMBOLICSTRING);
		mv.visitInsn(Opcodes.DUP);
		mv.visitLdcInsn(string);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, SYMBOLICSTRING, "<init>", SYMBOLICSTRING_SIGNATURE);
	}
	
	/**
	 * Writes bytecode to store modified local variable
	 * @param var The index of the variable in local variables table
	 */
	private void writeCodeToStoreLocalSymbolicVariable(int var) {
		modifiedVariablesForState.peek().add(var);
		mv.visitVarInsn(Opcodes.ALOAD, var);
		mv.visitIntInsn(Opcodes.BIPUSH, var);
    	mv.visitMethodInsn(Opcodes.INVOKESTATIC, PATH, "addLocalVariable", PATH_ADD_LOCAL_VARIABLE_SIGNATURE);
	}
	
	/**
	 * Writes bytecode to store modified local field
	 * @param owner Name of the wner class
	 * @param name Name of the field
	 * @param desc Descriptor of the field
	 */
	private void wirteCodeToStoreField(String owner, String name, String desc) {
		mv.visitFieldInsn(Opcodes.GETFIELD, owner, name, desc);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, PATH, "addLocalField", PATH_ADD_LOCAL_FIELD_SIGNATURE);
	}
	
	/**
	 * Writes bytecode to backtrack i.e. to restore all modified local 
	 * variables and remove the current state
	 * TODO: Should restore the modified class fields also
	 */
	private void writeCodeToBacktrack() {
		writeCodeToDisplayMessage("Backtracking...");
		if (modifiedVariablesForState.size() > 0) {
			for (Integer i: modifiedVariablesForState.peek()) {
				mv.visitIntInsn(Opcodes.BIPUSH, i);
				mv.visitMethodInsn(Opcodes.INVOKESTATIC, PATH, "getVariableValue", PATH_ADD_GET_VARIABLE_VALUE_SIGNATURE);
				mv.visitVarInsn(Opcodes.ASTORE, i);
			}
			modifiedVariablesForState.pop();
		}
		writeCodeToDisplayMessage("Backtracking complete");
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, PATH, "removeLastState", PATH_REMOVE_LAST_STATE_SIGNATURE);
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
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, PATH, "addBranchConstraint", PATH_ADD_BRANCH_CONSTRAINT);
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
	    mv.visitMethodInsn(Opcodes.INVOKESTATIC, PATH, "addBranchConstraint", PATH_ADD_BRANCH_CONSTRAINT);
	}
	
	/**
	 * Writes bytecode that that adds switch constraint to the path constraints
	 * @param index Value of the control variable that should be satisfied 
	 */
	private void writeCodeToIntroduceSwitchConstraint(int index) {
		mv.visitTypeInsn(Opcodes.NEW, IF_ICMPNE);
		mv.visitInsn(Opcodes.DUP);
		visitVarInsn(Opcodes.ALOAD, indexOfVariablesUsedForSwitch.peek());
		writeCodeToIntroduceSymbolicIntegerConstant(Opcodes.BIPUSH, index);
	    mv.visitMethodInsn(Opcodes.INVOKESPECIAL, IF_ICMPNE, "<init>", INTEGER_PATH_CONSTRAINT_SIGNATURE);
	    mv.visitMethodInsn(Opcodes.INVOKESTATIC, PATH, "addBranchConstraint", PATH_ADD_BRANCH_CONSTRAINT);
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
				resultBuilder.append(updateClassName(s));
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
		if (name.equals("I")) {
			newName = INTEGER_INTERFACE;
		} else if (name.equals("Ljava/lang/String")) {
			newName = SYMBOLICSTRING_INTRFACE;
		} else if (false) {
			//add conditions fo all basic interfaces i.e DoubleInterface and so on
		} else if (!name.equals("") && !name.equals("V") && !name.equals("Z")) {
			newName = newName + ";";
		}
		return newName;
	}
}
