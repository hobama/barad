package barad.profiler;

import java.util.HashMap;
import java.util.HashSet;

import org.apache.log4j.Logger;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;


public class InstrMethodAdapter implements MethodVisitor, ProfilerProperties{

	private MethodVisitor mv;
	private Logger log;
	private HashSet<String> instructionSet;
	private HashMap<Integer, String> map;
	
	public InstrMethodAdapter(MethodVisitor mv, HashSet<String> instructionSet) {
		this.mv = mv;
		this.log = Logger.getLogger(this.getClass());
		this.instructionSet = instructionSet;
		map = OpcodeToMnemonicMap.getMap();
	}

	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		log.debug("VISIT ANNOTATION Name: " + desc + ", Visible: " + visible + ";");
		return mv.visitAnnotation(desc, visible);
	}

	public AnnotationVisitor visitAnnotationDefault() {
		log.debug("VISIT ANNOTATION" + ";");
		return mv.visitAnnotationDefault();
	}

	public void visitAttribute(Attribute attr) {
		log.debug("VISIT ATTRIBUTE: Atribute: " + attr.toString() + ";");
		mv.visitAttribute(attr);
	}

	public void visitCode() {
		log.debug("VISIT CODE" + ";");
		mv.visitCode();
	}

	public void visitEnd() {
		log.debug("VISIT END" + ";");
		mv.visitEnd();
	}

	public void visitFieldInsn(int opcode, String owner, String name, String desc) {
		log.debug("VISIT FIELD INSTRUCTION: Opcode: " + opcode + ", Owner: " + owner + ", Name: " + name + ", Descriptor: " + desc + ";");
		instructionSet.add(map.get(opcode) /*+ " " + owner + " " + name + " " + desc*/);
		mv.visitFieldInsn(opcode, owner, name, desc);
	}

	public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
		log.debug("VISIT FRAME: Type: " + type + ", Local vriables#: " + nLocal + ", Stack elements#: " + nStack + ";");
		mv.visitFrame(type, nLocal, local, nStack, stack);
	}

	public void visitIincInsn(int var, int increment) {
		log.debug("VISIT INCREMENTAL INSTRUCTION: Variable: " + var + ", Increment: " + increment + ";");
		//instructionSet.add(String.valueOf(var) + " " + String.valueOf(increment));
		mv.visitIincInsn(var, increment);
	}

	public void visitInsn(int opcode) {
		log.debug("VISIT INSTRUCTION: Opcode: " + opcode + ";");
		instructionSet.add(map.get(opcode));
		mv.visitInsn(opcode);
	}

	public void visitIntInsn(int opcode, int operand) {
		log.debug("VISIT INTEGER INSTRUCTION: Opcode: " + opcode + ", Operand: " + operand + ";");
		instructionSet.add(map.get(opcode) /* + " " + String.valueOf(operand)*/);
		mv.visitIntInsn(opcode, operand);
		}
	
	public void visitJumpInsn(int opcode, Label label) {
		log.debug("VISIT JUMP INSTRUCTION: Opcode: " + opcode + ", Label: " + label.toString() + ";");
		instructionSet.add(map.get(opcode) /* + " " + label.toString()*/);
		mv.visitJumpInsn(opcode, label);
	}

	public void visitLabel(Label label) {
		log.debug("VISIT LABEL: Label: " + label.toString() + ";");
		//instructionSet.add(label.toString());
		mv.visitLabel(label);
	}

	public void visitLdcInsn(Object cst) {
		log.debug("VISIT LDC INSTRUCTION: Object: " + cst.toString() + ";");
		//instructionSet.add(cst.toString());
		mv.visitLdcInsn(cst);
	}

	public void visitLineNumber(int line, Label start) {
		log.debug("VISIT LINE: Number: " + line + ", Start: " + start.toString() + ";");
		//instructionSet.add(String.valueOf(line) + " " + start.toString());
		mv.visitLineNumber(line, start);	
	}

	public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
		log.debug("VISIT LOCAL VARIABLE: Name: " + name + ", Descriptor: " + desc + ", Signature: " + signature + ", Start: " + start.toString() + ", End: " + end .toString()+ ", Index: " + index + ";");
		//instructionSet.add(name + " " + desc + " " + signature + " " + start.toString() + " " + end.toString() + " " + String.valueOf(index));
		mv.visitLocalVariable(name, desc, signature, start, end, index);
	}

	public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
		log.debug("VISIT LOOKUP SWITCH INSTRUCTION: DefHandlerBlock: " + dflt.toString() + ";");
		//instructionSet.add(dflt.toString());
		mv.visitLookupSwitchInsn(dflt, keys, labels);
	}

	public void visitMaxs(int maxStack, int maxLocals) {
		log.debug("VISIT MAX: MaxStack: " + maxStack + ", Maxlocals: " + maxLocals + ";");
		mv.visitMaxs(maxStack, maxLocals);
	}

	public void visitMethodInsn(int opcode, String owner, String name, String desc) {
		log.debug("VISIT METHOD INSTRUCTION: Opcode: " + opcode + ", Owner: " + owner + ", Name: " + name + ", Descriptor: " + desc + ";");
		instructionSet.add(map.get(opcode) /* + " " + owner + " " + name + " " + desc*/);
		mv.visitMethodInsn(opcode, owner, name, desc);
	}

	public void visitMultiANewArrayInsn(String desc, int dims) {
		log.debug("VISIT MULTIANEW INSTRUCTION: Descriptor: " + desc + ", Dimensions: " + dims + ";");
		//instructionSet.add(desc + " " + String.valueOf(dims));
		mv.visitMultiANewArrayInsn(desc, dims);
	}

	public AnnotationVisitor visitParameterAnnotation(int parameter, String desc, boolean visible) {
		log.debug("VISIT PARAMETER ANNOTATION: Parameter: " + parameter + ", Descriptor: " + desc + ", Visible: " + visible + ";");
		return mv.visitParameterAnnotation(parameter, desc, visible);
	}

	public void visitTableSwitchInsn(int min, int max, Label dflt, Label[] labels) {
		log.debug("VISIT TABLE SWITCH INSTRUCTION: Min: " + min + ", Max: " + max + ", DefHandlerBlock: " + dflt.toString() + ";");
		//instructionSet.add(String.valueOf(min)+ " " + String.valueOf(max) + " " + dflt.toString());
		mv.visitTableSwitchInsn(min, max, dflt, labels);
	}

	public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
		log.debug("VISIT TRY/CATCH BLOCK INSTRUCTION: Start: " + start.toString() + ", End: " + end.toString() + ", Handler: " + handler.toString()  + ", Type: " + type + ";");
		//instructionSet.add(start.toString()+ " " + end.toString() + " " + handler.toString() + " " + type);
		mv.visitTryCatchBlock(start, end, handler, type);
	}

	public void visitTypeInsn(int opcode, String desc) {
		log.debug("VISIT TYPE INSTRUCTION: Opcode: " + opcode + ", Descriptor: " + desc + ";");
		instructionSet.add(map.get(opcode) /*+ " " + desc*/);
		mv.visitTypeInsn(opcode, desc);
	}

	public void visitVarInsn(int opcode, int var) {
		log.debug("VISIT VARIABLE INSTRUCTION: Opcode: " + opcode + ", Variable: " + var + ";");
		instructionSet.add(map.get(opcode) /*+ " " + String.valueOf(var)*/);
		mv.visitVarInsn(opcode, var);
	}
}
