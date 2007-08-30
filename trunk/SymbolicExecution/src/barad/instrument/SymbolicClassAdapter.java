package barad.instrument;

import static barad.util.Properties.VERBOSE;

import org.apache.log4j.Logger;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import barad.util.Util;

public class SymbolicClassAdapter  extends ClassAdapter{
	
	private Logger log = Logger.getLogger(this.getClass().getName());
	
	public SymbolicClassAdapter(ClassVisitor cv) {
		super(cv);
	}
	
	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		if (VERBOSE) log.debug("VISIT CLASS: " + name + ", Version: " + version + ", Access: " + access + ", SuperClass: " + superName + ";");
		Util.increaseClassId();
		cv.visit(version, access, name, signature, superName, makeSerializable(interfaces));
	}
	
	@Override
	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		if (VERBOSE) log.debug("VISIT ANNOTATION: " + desc + ", Visible: " + visible + ";");
		return cv.visitAnnotation(desc, visible);
	}
	
	@Override 
	public void visitAttribute(Attribute attr)  {
		if (VERBOSE) log.debug("VISIT ATTRIBUTE: " + attr + ";");
		cv.visitAttribute(attr);
	}
	
	@Override
	public void visitEnd() {
		if (VERBOSE) log.debug("VISIT END;");
		cv.visitEnd();
	}
	
	@Override
	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		if (VERBOSE) log.debug("VISIT FIELD: " + name + ", Access: " + access + ", Descriptor: " + desc + ", Signature: " + signature + ";");
		if (access == Opcodes.ACC_PRIVATE) {
			access = Opcodes.ACC_PROTECTED;
		}
		return cv.visitField(access, name, desc, signature, value);
	}
	
	@Override
	public void visitInnerClass(String name, String outerName, String innerName, int access) {
		if (VERBOSE) log.debug("VISIT INNER CLASS: " + name + ", Access: " + access + ", Outer name: " + outerName + ", Inner name: " + innerName + ";");
		Util.increaseClassId();
		cv.visitInnerClass(name, outerName, innerName, access);
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		if (VERBOSE) log.debug("VISIT METHOD: " + name + ", Access: " + access + ", Descriptor: " + desc + ", Signature: " + signature + ";");
		Util.increaseMethodId();
		MethodVisitor mv = cv.visitMethod(access, name, SymbolicMethodAdapter.modifyDescriptor(desc), signature, exceptions); 
		if (mv != null) {
			mv = new SymbolicMethodAdapter(mv);
		}
		return mv;
	}
	
	@Override
	public void visitOuterClass(String owner, String name, String desc) {
		if (VERBOSE) log.debug("VISIT OUTERCLASS: " + name + ", Descriptor: " + desc + ", Owner: " + owner + ";");
		cv.visitOuterClass(owner, name, desc);
	}

	@Override
	public void visitSource(String source, String debug) {
		if (VERBOSE) log.debug("VISIT SOURCE: " + source + ", Debug: " + debug + ";");
		cv.visitSource(source, debug);
	}
	
	/**
	 * Adds the Serilaizable interface to the array of implemented 
	 * interfaces. 
	 * @param interfaces Interfaces implemented by the class
	 * @return Interfaces implemented by the class including Serializable
	 */
	private String[] makeSerializable(String[] interfaces) {
		String[] interfacez = new String[interfaces.length + 1];
		if (interfaces != null) {
			int i;
			for (i = 0; i < interfaces.length; i++) {
				if (interfaces[i].equals("java/io/Serializable")) {
					interfacez = interfaces;
					break;
				}
				interfacez[i] = interfaces[i];
			}
			if (!(interfacez == interfaces)) {				
				interfacez[i]  = "java/io/Serializable";
				log.debug("Class made Serializable");
			}
		} else {
			interfacez = interfaces;
		}
		return interfacez;
	}
}