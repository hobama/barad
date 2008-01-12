package barad.instrument.adapters;

import java.util.HashSet;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import barad.instrument.instrumenter.Method;

/**
 * This class detects methos that are called from the
 * constructor and does not delegate to the next method
 * visitor in the chain since we remove the original
 * constructor.
 * @author svetoslavganov
 *
 */
public class MethodDetectorMethodVisitor implements MethodVisitor  {
	private HashSet<Method> removedMethods;
	private String owner;
	
	public MethodDetectorMethodVisitor(HashSet<Method> removedMethods, String owner) {
		this.removedMethods = removedMethods;
		this.owner = owner;
	}
	
	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {return null;}

	public AnnotationVisitor visitAnnotationDefault() {return null;}

	public void visitAttribute(Attribute attr) {}

	public void visitCode() {}

	public void visitEnd() {}

	public void visitFieldInsn(int opcode, String owner, String name, String desc) {}

	public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {}

	public void visitIincInsn(int var, int increment) {}

	public void visitInsn(int opcode) {}

	public void visitIntInsn(int opcode, int operand) {}

	public void visitJumpInsn(int opcode, Label label) {}

	public void visitLabel(Label label) {}

	public void visitLdcInsn(Object cst) {}

	public void visitLineNumber(int line, Label start) {}

	public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {}

	public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {}

	public void visitMaxs(int maxStack, int maxLocals) {}

	/**
	 * Add to the removed mathods every mathod of the instrumented 
	 * class called from the original constructore
	 */
	public void visitMethodInsn(int opcode, String owner, String name, String desc) {
		if (this.owner.equals(owner)) {
			Method method = new Method();
			method.setClazz(owner);
			method.setDesc(desc);
			method.setName(name);
			removedMethods.add(method);
		}
	}

	public void visitMultiANewArrayInsn(String desc, int dims) {}

	public AnnotationVisitor visitParameterAnnotation(int parameter, String desc, boolean visible) {return null;}

	public void visitTableSwitchInsn(int min, int max, Label dflt, Label[] labels) {}

	public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {}

	public void visitTypeInsn(int opcode, String desc) {}

	public void visitVarInsn(int opcode, int var) {}
}
