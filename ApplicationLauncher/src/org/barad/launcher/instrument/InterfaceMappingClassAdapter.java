package org.barad.launcher.instrument;

import org.apache.log4j.Logger;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;

@SuppressWarnings("unused")
public class InterfaceMappingClassAdapter  extends ClassAdapter{
	private Logger log = Logger.getLogger(this.getClass().getName());
	
	public InterfaceMappingClassAdapter(ClassVisitor cv) {
		super(cv);
	}
	
	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		cv.visit(version, access, name, signature, superName, interfaces);
	}
	
	@Override
	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		return cv.visitAnnotation(desc, visible);
	}
	
	@Override 
	public void visitAttribute(Attribute attr)  {
		cv.visitAttribute(attr);
	}
	
	@Override
	public void visitEnd() {
		cv.visitEnd();
	}
	
	@Override
	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		return cv.visitField(access, name, desc, signature, value);
	}
	
	@Override
	public void visitInnerClass(String name, String outerName, String innerName, int access) {
		cv.visitInnerClass(name, outerName, innerName, access);
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
		if (mv != null) {
			mv = new MethodAdapter(mv);
		}
		return mv;
	}
	
	@Override
	public void visitOuterClass(String owner, String name, String desc) {
		cv.visitOuterClass(owner, name, desc);
	}

	@Override
	public void visitSource(String source, String debug) {
		cv.visitSource(source, debug);
	}
}