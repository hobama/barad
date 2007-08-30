package barad.instrument;

import static barad.util.Properties.VERBOSE;

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
	private MethodVisitor mv;
	private Logger log;
	private Stack<HashSet<Integer>> modifiedVariablesForState;
	private HashMap<Label, Integer> backtrackLabels;
	private boolean test = false;
	
	public SymbolicMethodAdapter(MethodVisitor mv) {
		this.mv = mv;
		this.log = Logger.getLogger(this.getClass());
		this.modifiedVariablesForState = new Stack<HashSet<Integer>>();
		this.backtrackLabels = new HashMap<Label, Integer>();
	}

	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		if (VERBOSE) log.debug("VISIT ANNOTATION Name: " + desc + ", Visible: " + visible + ";");
		return mv.visitAnnotation(desc, visible);
	}

	public AnnotationVisitor visitAnnotationDefault() {
		if (VERBOSE) log.debug("VISIT ANNOTATION" + ";");
		return mv.visitAnnotationDefault();
	}

	public void visitAttribute(Attribute attr) {
		if(VERBOSE) log.debug("VISIT ATTRIBUTE: Atribute: " + attr.toString() + ";");
		mv.visitAttribute(attr);
	}

	public void visitCode() {
		if (VERBOSE) log.debug("VISIT CODE" + ";");
		writeCodeToCreateNewState();
		mv.visitCode();
	}

	public void visitEnd() {
		if (VERBOSE) log.debug("VISIT END" + ";");
		mv.visitEnd();
	}

	public void visitFieldInsn(int opcode, String owner, String name, String desc) {
		if (opcode == Opcodes.PUTFIELD) {
			wirteCodeToStoreField(owner, name, desc);
		}	
		if (VERBOSE) log.debug("VISIT FIELD INSTRUCTION: Opcode: " + opcode + ", Owner: " + owner + ", Name: " + name + ", Descriptor: " + desc + ";");
		mv.visitFieldInsn(opcode, owner, name, modifyDescriptor(desc));
	}

	public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
		if (VERBOSE) log.debug("VISIT FRAME: Type: " + type + ", Local vriables#: " + nLocal + ", Stack elements#: " + nStack + ";");
		mv.visitFrame(type, nLocal, local, nStack, stack);
	}

	public void visitIincInsn(int var, int increment) {
		if (VERBOSE) log.debug("VISIT INCREMENTAL INSTRUCTION: Variable: " + var + ", Increment: " + increment + ";");
		mv.visitIincInsn(var, increment);
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
		} else if (opcode == Opcodes.IRETURN || opcode == Opcodes.LRETURN || opcode == Opcodes.FRETURN ||
		
				   opcode == Opcodes.DRETURN || opcode == Opcodes.ARETURN || opcode == Opcodes.RETURN) {
			writeCodeToBacktrack();
			mv.visitInsn(opcode);
		} else {
			mv.visitInsn(opcode);
		}
	}
	


	public void visitIntInsn(int opcode, int operand) {
		if (opcode == Opcodes.BIPUSH || opcode == Opcodes.SIPUSH) {
			if (VERBOSE) log.debug("Integer constant:" + operand + " replaced by symbolic integer constant");
			writeCodeToIntroduceSymbolicIntegerConstant(opcode, operand);	
		} else {
			if (VERBOSE) log.debug("VISIT INTEGER INSTRUCTION: Opcode: " + opcode + ", Operand: " + operand + ";");
			mv.visitIntInsn(opcode, operand);
		}
	}

	private void addBacktrackMarker(Label label) {
		Integer value = backtrackLabels.get(label); 
		if (value == null) {
			backtrackLabels.put(label, 1);
		} else {
			backtrackLabels.put(label, value + 1);
		}
	}
	
	public void visitJumpInsn(int opcode, Label label) {
		if (opcode == Opcodes.IF_ICMPEQ) {
			if (VERBOSE) log.debug("IF_ICMPEQ replaced by Symbolic IF_ICMPEQ");
			addBacktrackMarker(label);
			writeCodeToIntroduceSymbolicJumpInsn("barad/symboliclibrary/integer/IF_ICMPEQ", "(Lbarad/symboliclibrary/integer/IntegerInterface;Lbarad/symboliclibrary/integer/IntegerInterface;)V");
		} else if (opcode == Opcodes.IF_ICMPGE) {
			if (VERBOSE) log.debug("IF_ICMPGE replaced by Symbolic IF_ICMPGE");
			addBacktrackMarker(label);
			writeCodeToIntroduceSymbolicJumpInsn("barad/symboliclibrary/integer/IF_ICMPGE", "(Lbarad/symboliclibrary/integer/IntegerInterface;Lbarad/symboliclibrary/integer/IntegerInterface;)V");
		} else if (opcode == Opcodes.IF_ICMPGT) {
			if (VERBOSE) log.debug("IF_ICMPGT replaced by Symbolic IF_ICMPGT");
			addBacktrackMarker(label);
			writeCodeToIntroduceSymbolicJumpInsn("barad/symboliclibrary/integer/IF_ICMPGT", "(Lbarad/symboliclibrary/integer/IntegerInterface;Lbarad/symboliclibrary/integer/IntegerInterface;)V");
		} else if (opcode == Opcodes.IF_ICMPLE) {
			if (VERBOSE) log.debug("IF_ICMPLE replaced by Symbolic IF_ICMPLE");
			addBacktrackMarker(label);
			writeCodeToIntroduceSymbolicJumpInsn("barad/symboliclibrary/integer/IF_ICMPLE", "(Lbarad/symboliclibrary/integer/IntegerInterface;Lbarad/symboliclibrary/integer/IntegerInterface;)V");
		} else if (opcode == Opcodes.IF_ICMPLT) {
			if (VERBOSE) log.debug("IF_ICMPLT replaced by Symbolic IF_ICMPLT");
			addBacktrackMarker(label);
			writeCodeToIntroduceSymbolicJumpInsn("barad/symboliclibrary/integer/IF_ICMPLT", "(Lbarad/symboliclibrary/integer/IntegerInterface;Lbarad/symboliclibrary/integer/IntegerInterface;)V");
		} else if (opcode == Opcodes.IF_ICMPNE) {
			if (VERBOSE) log.debug("IF_ICMPNE replaced by Symbolic IF_ICMPNE");
			addBacktrackMarker(label);
			writeCodeToIntroduceSymbolicJumpInsn("barad/symboliclibrary/integer/IF_ICMPNE", "(Lbarad/symboliclibrary/integer/IntegerInterface;Lbarad/symboliclibrary/integer/IntegerInterface;)V");
		} else if (opcode == Opcodes.GOTO) {
			Integer backtrackSteps = backtrackLabels.get(label);
			if (backtrackSteps != null) {
				while (backtrackSteps > 0) {
					writeCodeToBacktrack();
					backtrackSteps--;
				}
				backtrackLabels.remove(label);
			}
			addBacktrackMarker(label);
			test = true;
		} else {
			if (VERBOSE) log.debug("VISIT JUMP INSTRUCTION: Opcode: " + opcode + ", Label: " + label.toString() + ";");
			mv.visitJumpInsn(opcode, label);
		}
	}
	
	public void visitLabel(Label label) {	
		Integer count;
		if ((count = backtrackLabels.remove(label)) != null) {
			while (count > 0) {
				if (test && count == 1) {
					mv.visitMethodInsn(Opcodes.INVOKESTATIC, "barad/symboliclibrary/path/Path", "reverseBranchConstraints", "()V");
					test = false;
				}
				count--;
				writeCodeToBacktrack();
			}
		}
		if (VERBOSE) log.debug("VISIT LABEL: Label: " + label.toString() + ";");
		mv.visitLabel(label);
	}

	public void visitLdcInsn(Object cst) {
		if (VERBOSE) log.debug("VISIT LDC INSTRUCTION: Object: " + cst.toString() + ";");
		mv.visitLdcInsn(cst);
	}

	public void visitLineNumber(int line, Label start) {
		if (VERBOSE) log.debug("VISIT LINE: Number: " + line + ", Start: " + start.toString() + ";");
		mv.visitLineNumber(line, start);	
	}

	public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
		if (desc.equals("I")) {
			mv.visitLocalVariable(name, modifyDescriptor(desc), signature, start, end, index);
			writeCodeToIntroduceSymbolicIntegerVariable(index);
		} else {
			mv.visitLocalVariable(name, desc, signature, start, end, index);
		}
	}

	public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
		if (VERBOSE) 	log.debug("VISIT LOOKUP SWITCH INSTRUCTION: DefHandlerBlock: " + dflt.toString() + ";");
		mv.visitLookupSwitchInsn(dflt, keys, labels);
	}

	public void visitMaxs(int maxStack, int maxLocals) {
		if (VERBOSE) log.debug("VISIT MAX: MaxStack: " + maxStack + ", Maxlocals: " + maxLocals + ";");
		mv.visitMaxs(maxStack + 20, maxLocals);
	}

	public void visitMethodInsn(int opcode, String owner, String name, String desc) {
		if (VERBOSE) log.debug("VISIT METHOD INSTRUCTION: Opcode: " + opcode + ", Owner: " + owner + ", Name: " + name + ", Descriptor: " + desc + ";");
		mv.visitMethodInsn(opcode, owner, name, modifyDescriptor(desc));
	}

	public void visitMultiANewArrayInsn(String desc, int dims) {
		if (VERBOSE) log.debug("VISIT MULTIANEW INSTRUCTION: Descriptor: " + desc + ", Dimensions: " + dims + ";");
		mv.visitMultiANewArrayInsn(desc, dims);
	}

	public AnnotationVisitor visitParameterAnnotation(int parameter, String desc, boolean visible) {
		if (VERBOSE) log.debug("VISIT PARAMETER ANNOTATION: Parameter: " + parameter + ", Descriptor: " + desc + ", Visible: " + visible + ";");
		return mv.visitParameterAnnotation(parameter, desc, visible);
	}

	public void visitTableSwitchInsn(int min, int max, Label dflt, Label[] labels) {
		if (VERBOSE) log.debug("VISIT TABLE SWITCH INSTRUCTION: Min: " + min + ", Max: " + max + ", DefHandlerBlock: " + dflt.toString() + ";");
		mv.visitTableSwitchInsn(min, max, dflt, labels);
	}

	public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
		if (VERBOSE) log.debug("VISIT TRY/CATCH BLOCK INSTRUCTION: Start: " + start.toString() + ", End: " + end.toString() + ", Handler: " + handler.toString()  + ", Type: " + type + ";");
		mv.visitTryCatchBlock(start, end, handler, type);
	}

	public void visitTypeInsn(int opcode, String desc) {
		if (VERBOSE) log.debug("VISIT TYPE INSTRUCTION: Opcode: " + opcode + ", Descriptor: " + desc + ";");
		mv.visitTypeInsn(opcode, desc);
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
			mv.visitVarInsn(Opcodes.ALOAD, var);
		} else {
			mv.visitVarInsn(opcode, var);
		}
	}
	
	/**
	 * Writes bytecode that generates a new program state
	 */
	private void writeCodeToCreateNewState() {
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "barad/symboliclibrary/path/Path", "createNewState", "()V");
		if (modifiedVariablesForState.size() > 0) {
			modifiedVariablesForState.push(new HashSet<Integer>(modifiedVariablesForState.peek()));
		} else {
			modifiedVariablesForState.push(new HashSet<Integer>());
		}
	}
	
	/**
	 * Writes code to replace integer constant with symbolic integer constant
	 * @param opcode The bytecode instruction used to add the integer to the stack.
	 * @param operand The integer value to be replaced
	 */
	private void writeCodeToIntroduceSymbolicIntegerConstant(int opcode, int operand) {
		mv.visitTypeInsn(Opcodes.NEW, "barad/symboliclibrary/integer/ICONST");
		mv.visitInsn(Opcodes.DUP);
		mv.visitIntInsn(opcode, operand);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "barad/symboliclibrary/integer/ICONST", "<init>", "(I)V");
	}
	
	private void writeCodeToIntroduceSymbolicIntegerVariable(int index) {
		mv.visitTypeInsn(Opcodes.NEW, "barad/symboliclibrary/integer/IVAR");
		mv.visitInsn(Opcodes.DUP);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "barad/symboliclibrary/integer/IVAR", "<init>", "()V");
		mv.visitVarInsn(Opcodes.ASTORE, index);
	}
	
	/**
	 * Writes bytecode to store modified local variable
	 * @param var The index of the variable in local variables table
	 */
	private void writeCodeToStoreLocalSymbolicVariable(int var) {
		if (modifiedVariablesForState.size() == 0) {
			modifiedVariablesForState.push(new HashSet<Integer>());
		}
		modifiedVariablesForState.peek().add(var);
		mv.visitVarInsn(Opcodes.ALOAD, var);
		mv.visitIntInsn(Opcodes.BIPUSH, var);
    	mv.visitMethodInsn(Opcodes.INVOKESTATIC, "barad/symboliclibrary/path/Path", "addLocalVariable", "(Ljava/lang/Object;I)Ljava/lang/Object;");
	}
	
	/**
	 * Writes bytecode to store modified local field
	 * @param owner Name of the wner class
	 * @param name Name of the field
	 * @param desc Descriptor of the field
	 */
	private void wirteCodeToStoreField(String owner, String name, String desc) {
		mv.visitFieldInsn(Opcodes.GETFIELD, owner, name, desc);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "barad/symboliclibrary/path/Path", "addLocalField", "(Ljava/lang/Object;Ljava/lang/String;)V");
	}
	
	/**
	 * Writes bytecode to backtrack i.e. to restore all modified local 
	 * variables and remove the current state
	 * TODO: Should restore the modifid class fields also
	 */
	private void writeCodeToBacktrack() {
		if (modifiedVariablesForState.size() > 0) {
			for (Integer i: modifiedVariablesForState.peek()) {
				mv.visitIntInsn(Opcodes.BIPUSH, i);
				mv.visitMethodInsn(Opcodes.INVOKESTATIC, "barad/symboliclibrary/path/Path", "getVariableValue", "(I)Ljava/lang/Object;");
				mv.visitVarInsn(Opcodes.ASTORE, i);
			}
			modifiedVariablesForState.pop();
		}
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "barad/symboliclibrary/path/Path", "removeLastState", "()V");
	}
	
	/**
	 * Writes bytecode that replaces jump instruction with symbolic one.
	 * The generated code adds the condition of the jump instruction as
	 * a symbolic path condition. 
	 * @param symbolicClassName The name of a symbolic class used to replace the jump instruction
	 * @param Descriptor of the symbolic operation's constructor
	 */
	private void writeCodeToIntroduceSymbolicJumpInsn (String symbolicClassName, String descriptor) {
		writeCodeToCreateNewState();
		writeCodeToIntroduceSymbolicOperation(symbolicClassName, descriptor);
	    mv.visitMethodInsn(Opcodes.INVOKESTATIC, "barad/symboliclibrary/path/Path", "addBranchConstraint", "(Ljava/lang/Object;)Ljava/lang/Object;");
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
			newName = "Lbarad/symboliclibrary/integer/IntegerInterface;";
		} else if (false) {
			//add conditions fo all basic interfaces i.e DoubleInterface and so on
		} else if (!name.equals("") && !name.equals("V") && !name.equals("Z")) {
			newName = newName + ";";
		}
		return newName;
	}
}
