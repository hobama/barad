package barad.instrument;

import static barad.util.Properties.DEBUG;
import static barad.util.Properties.VERBOSE;

import java.util.Map;

import org.apache.log4j.Logger;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import barad.util.Util;

public class SymbolicClassAdapter  extends ClassAdapter{
	private Logger log = Logger.getLogger(this.getClass().getName());
	private String instrumentedClass;
	private String initialMethod = "test";
	private String initialMethodSignsture = "(IDLjava/lang/String;JF)V";
	private boolean instrument = true;
	private String className = null;
	private String signature = null;
	
	public SymbolicClassAdapter(ClassVisitor cv) {
		super(cv);
	}
	
	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		if (VERBOSE) log.debug("VISIT CLASS: " + name + ", Version: " + version + ", Access: " + access + ", SuperClass: " + superName + ";");
		Util.increaseClassId();
		
		name = name + "Test";
		
		instrumentedClass = name;
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
		//The new generated main method is not instrumented
		instrument = false;
		generateMainMethod(initialMethod, initialMethodSignsture);
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
		MethodVisitor mv = null;
		//Remove - public static void main(String[] args)
		if (!name.equals("main") || !desc.equals("([Ljava/lang/String)V")) {
			if (VERBOSE) log.debug("VISIT METHOD: " + name + ", Access: " + access + ", Descriptor: " + desc + ", Signature: " + signature + ";");
			if (name.equals(initialMethod)) {
				log.info("Generating main method ");
				identifyInputs(desc);
			}
			Util.increaseMethodId();
			if (instrument) {
				mv = cv.visitMethod(access, name, SymbolicMethodAdapter.modifyDescriptor(desc), signature, exceptions); 
				if (mv != null) {
					mv = new SymbolicMethodAdapter(mv);
				}
			} else {
				mv = cv.visitMethod(access, name, desc, signature, exceptions);
				if (mv != null) {
					mv = new MethodAdapter(mv);
				}
			}
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
	
	/**
	 * Generates a main method in which: 
	 * 1. For all symbolic parameters of the method from which the 
	 * symbolic execution begins are instantiated symbolic variables 
	 * 2. An instance of the Symbilically execueted class is instantiated
	 * 3. The method from which the symbolic execution begins is invoked
	 * @param methodName Name of the method form which the symbolic 
	 * execution begins
	 * @param methodDesc Descriptor of the method form which the symbolic 
	 * execution begins
	 */
	private void generateMainMethod(String methodName, String methodDesc) {
		MethodVisitor mv = visitMethod(Opcodes.ACC_PUBLIC, "symbolicExecution", "()V", null, null);
		mv.visitCode();
		Label l0 = new Label();
		mv.visitLabel(l0);
		mv.visitLineNumber(15, l0);
		Label l1 = new Label();
		mv.visitLabel(l1);
		mv.visitLineNumber(16, l1);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		for (Map.Entry<Integer, String> e: Util.getProgramInputs().entrySet()) {
			setVariableGenerationParameters(e.getValue());
			mv.visitTypeInsn(Opcodes.NEW, className);
			mv.visitInsn(Opcodes.DUP);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, className, "<init>", signature);			
		}
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, instrumentedClass, methodName, SymbolicMethodAdapter.modifyDescriptor(methodDesc));
		Label l2 = new Label();
		mv.visitLabel(l2);
		mv.visitLineNumber(17, l2);
		mv.visitInsn(Opcodes.RETURN);
		Label l3 = new Label();
		mv.visitLabel(l3);
		mv.visitLocalVariable("args", "[Ljava/lang/String;", null, l0, l3, 0);
		mv.visitLocalVariable("instrumentedClassInstance", "L" + instrumentedClass + ";", null, l1, l3, 1);
		mv.visitMaxs(Util.getProgramInputs().size() + 2, 2);
		mv.visitEnd();
	}
	
	private void setVariableGenerationParameters(String name) {
		if (name.equals("I") || name.equals("J")) {
			className = Names.IVAR.getValue();
			signature = Names.IVAR_SIGNATURE.getValue();
		} else if (name.equals("F") || name.equals("D")) {
			className = Names.FVAR.getValue();
			signature = Names.FVAR_SIGNATURE.getValue();
		} else if (name.equals("Ljava/lang/String;")) {
			className = Names.STRING.getValue();
			signature = Names.SYMBOLICSTRING_VAR_SIGNATURE.getValue();
		}
	}
	/**
	 * Identifies which variables in the method variable table are inputs
	 * of the method. The inputs of the first symbolically executed method
	 * are the ones for which we should generate concrete values. These sre
	 * stored in a HashMap in the Util static class as mappings of index to 
	 * type descriptor.  
	 * @param desc Descriptor of the method
	 */
	public void identifyInputs(String desc) {
		int counter = 1;
		String parameters = desc.substring(desc.indexOf('(') + 1, desc.indexOf(')'));
		String[] groups = parameters.split(";");
		for (String g: groups) {
			if (g.contains("/") || g.toUpperCase() != g) {
				while (g.charAt(0) != 'L') {
					Util.getProgramInputs().put(counter++, String.valueOf(g.charAt(0)));
					g = g.substring(1);
				}
				
				Util.getProgramInputs().put(counter++, g);
			} else {
				for (Character c: g.toCharArray()) {
					Util.getProgramInputs().put(counter++, c.toString());
				}
			}
		}
		if (DEBUG && VERBOSE) {
			for (Map.Entry<Integer, String> e: Util.getProgramInputs().entrySet()) {
				System.out.println("Index: " + e.getKey() + " Type: " + e.getValue());
			}
		}
	}
}