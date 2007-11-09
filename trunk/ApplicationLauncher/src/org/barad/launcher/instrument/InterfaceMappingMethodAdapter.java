package org.barad.launcher.instrument;

import org.apache.log4j.Logger;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

@SuppressWarnings("unused")
public class InterfaceMappingMethodAdapter implements MethodVisitor {
	private MethodVisitor mv;
	private Logger log;
	
	public InterfaceMappingMethodAdapter(MethodVisitor mv) {
		this.mv = mv;
		this.log = Logger.getLogger(this.getClass());
	}

	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		return mv.visitAnnotation(desc, visible);
	}

	public AnnotationVisitor visitAnnotationDefault() {
		return mv.visitAnnotationDefault();
	}

	public void visitAttribute(Attribute attr) {
		mv.visitAttribute(attr);
	}

	public void visitCode() {
		mv.visitCode();
	}

	public void visitEnd() {
		mv.visitEnd();
	}

	public void visitFieldInsn(int opcode, String owner, String name, String desc) {
		mv.visitFieldInsn(opcode, owner, name, desc);
	}

	public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
		mv.visitFrame(type, nLocal, local, nStack, stack);
	}

	public void visitIincInsn(int var, int increment) {
		mv.visitIincInsn(var, increment);
	}

	public void visitInsn(int opcode) {
		mv.visitInsn(opcode);
	}
	
	public void visitIntInsn(int opcode, int operand) {
		mv.visitIntInsn(opcode, operand);
	}
	
	public void visitJumpInsn(int opcode, Label label) {
		mv.visitJumpInsn(opcode, label);
	}
	
	public void visitLabel(Label label) {	
		mv.visitLabel(label);
	}

	public void visitLdcInsn(Object cst) {
		mv.visitLdcInsn(cst);
	}

	public void visitLineNumber(int line, Label start) {
		mv.visitLineNumber(line, start);	
	}

	public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
		mv.visitLocalVariable(name, desc, signature, start, end, index);
	}

	public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
		mv.visitLookupSwitchInsn(dflt, keys, labels);
	}

	public void visitMaxs(int maxStack, int maxLocals) {
		mv.visitMaxs(maxStack, maxLocals);
	}

	public void visitMethodInsn(int opcode, String owner, String name, String desc) {
		mv.visitMethodInsn(opcode, owner, name, desc);
	}

	public void visitMultiANewArrayInsn(String desc, int dims) {
		mv.visitMultiANewArrayInsn(desc, dims);
	}

	public AnnotationVisitor visitParameterAnnotation(int parameter, String desc, boolean visible) {
		return mv.visitParameterAnnotation(parameter, desc, visible);
	}

	public void visitTableSwitchInsn(int min, int max, Label dflt, Label[] labels) {
		mv.visitTableSwitchInsn(min, max, dflt, labels);
	}

	public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
		mv.visitTryCatchBlock(start, end, handler, type);
	}

	public void visitTypeInsn(int opcode, String desc) {
		mv.visitTypeInsn(opcode, desc);
	}

	public void visitVarInsn(int opcode, int var) {
		mv.visitVarInsn(opcode, var);
	}
}
