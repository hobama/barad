package barad.instrument.adapters;

import static barad.util.Properties.DEBUG;
import static barad.util.Properties.VERBOSE;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
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

import barad.instrument.instrumenter.Method;
import barad.instrument.util.Names;
import barad.util.Util;

public class SymbolicClassAdapter  extends ClassAdapter {
	private static final String EVENT_HANDLER_DESC_PATTERN = "\\(Lorg/eclipse/swt/events/[a-zA-Z]+;\\)V"; 
	private Logger log;
	private String instrumentedClass;
	private boolean instrument = true;
	private String className = null;
	private String signature = null;
	private List<Method> eventHandlers;
	private HashSet<Method> removedMethods;
	
	public SymbolicClassAdapter(ClassVisitor cv) {
		super(cv);
		this.log = Logger.getLogger(this.getClass().getName());
		this.eventHandlers = new LinkedList<Method>();
		this.removedMethods = new HashSet<Method>();
	}
	
	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		if (VERBOSE) log.debug("VISIT CLASS: " + name + ", Version: " + version + ", Access: " + access + ", SuperClass: " + superName + ";");
		Util.increaseClassId();
		instrumentedClass = name;
		superName = Util.modifyDescriptor(superName);
		superName = superName.substring(0, superName.length() - 1);
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
		//The new generated delegating methods are not instrumented
		instrument = false;
		for (Method method: eventHandlers) { 
			generateDelegatingMethod(method.getName(), method.getDesc());
		}
		//generate default constructor with no parameters
		generateDefaultConstructor();
		cv.visitEnd();
	}
	
	@Override
	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		if (VERBOSE) log.debug("VISIT FIELD: " + name + ", Access: " + access + ", Descriptor: " + desc + ", Signature: " + signature + ";");
		if (access == Opcodes.ACC_PRIVATE) {
			access = Opcodes.ACC_PROTECTED;
		}
		desc = Util.modifyDescriptor(desc);
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
		detectEventHandler(name, desc);
		HashMap<Integer, String> parameters = new HashMap<Integer, String>();
		identifyInputs(desc, parameters);
		Util.increaseMethodId();
		MethodVisitor mv = null;
		Method method = new Method();
		method.setClazz(instrumentedClass);
		method.setDesc(desc);
		method.setName(name);
		if (!removedMethods.contains(method)) {
			if (instrument) {
				if (name.equals(Names.CONSTRUCTOR.getValue()) 
						|| (name.equals(Names.MAIN_METHOD.getValue()) && desc.equals(Names.MAIN_METHOD_SIGNATURE.getValue()))) {
						mv = new MethodDetectorMethodVisitor(removedMethods, instrumentedClass);
				} else {
					mv = cv.visitMethod(access, name, Util.modifyDescriptor(desc), signature, exceptions); 
					if (mv != null) {
						mv = new SymbolicMethodAdapter(mv, parameters.size());
					}
				}	
			} else {
				mv = cv.visitMethod(access, name, desc, signature, exceptions);
				if (mv != null) {
					mv = new MethodAdapter(mv);
				}
			}
		} else {
			log.info(method.toString());
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
	private void generateDelegatingMethod(String methodName, String methodDesc) {
		HashMap<Integer, String> parameters = new HashMap<Integer, String>();
		identifyInputs(methodDesc, parameters);
		log.info("Generating main method...");
		MethodVisitor mv = visitMethod(Opcodes.ACC_PUBLIC, methodName, "()V", null, null);
		mv.visitCode();
		Label l0 = new Label();
		mv.visitLabel(l0);
		mv.visitLineNumber(15, l0);
		Label l1 = new Label();
		mv.visitLabel(l1);
		mv.visitLineNumber(16, l1);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		for (Map.Entry<Integer, String> e: parameters.entrySet()) {
			setVariableGenerationParameters(e.getValue());
			mv.visitTypeInsn(Opcodes.NEW, className);
			mv.visitInsn(Opcodes.DUP);
			mv.visitMethodInsn(Opcodes.INVOKESPECIAL, className, "<init>", signature);	
			mv.visitMethodInsn(Opcodes.INVOKESTATIC, Names.PATH.getValue(), "addInputVariable", Names.PATH_ADD_INPUT_VARIABLE_SIGNATURE.getValue());
			mv.visitTypeInsn(Opcodes.CHECKCAST, className);
		}
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, instrumentedClass, methodName, Util.modifyDescriptor(methodDesc));
		Label l2 = new Label();
		mv.visitLabel(l2);
		mv.visitLineNumber(17, l2);
		mv.visitInsn(Opcodes.RETURN);
		Label l3 = new Label();
		mv.visitLabel(l3);
		mv.visitLocalVariable("this", "L" + instrumentedClass + ";", null, l0, l3, 0);
		mv.visitMaxs(parameters.size() + 2, 1);
		mv.visitEnd();
		log.info("Generating main method completed");
	}
	
	/**
	 * Generates a default constructor with no parameters. We 
	 * remove all constructors and methods called from these 
	 * constructors
	 * NOTE: This could potentiall remove methods directly of 
	 * indirectly called from the event hanlers. However, such 
	 * case are rarely encountered
	 *
	 */
	private void generateDefaultConstructor() {
		MethodVisitor mv = visitMethod(Opcodes.ACC_PUBLIC, Names.CONSTRUCTOR.getValue(), Names.VOID_SIGNATURE.getValue(), null, null);
		mv.visitCode();
		Label l0 = new Label();
		mv.visitLabel(l0);
		mv.visitLineNumber(30, l0);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, Names.SYMBOLIC_COMPOSITE.getValue(), Names.CONSTRUCTOR.getValue(), Names.VOID_SIGNATURE.getValue());
		Label l1 = new Label();
		mv.visitLabel(l1);
		mv.visitLineNumber(32, l1);
		mv.visitInsn(Opcodes.RETURN);
		Label l2 = new Label();
		mv.visitLabel(l2);
		mv.visitLocalVariable(Names.THIS.getValue(), "L" + instrumentedClass + ";", null, l0, l2, 0);
		mv.visitMaxs(1, 1);
		mv.visitEnd();
	}
	
	private void setVariableGenerationParameters(String name) {
		if (name.equals("I") || name.equals("J")) {
			className = Names.IVAR.getValue();
			signature = Names.IVAR_SIGNATURE.getValue();
		} else if (name.equals("F") || name.equals("D")) {
			className = Names.FVAR.getValue();
			signature = Names.FVAR_SIGNATURE.getValue();
		} else if (name.equals("Ljava/lang/String")) {
			className = Names.SYMBOLIC_STRING.getValue();
			signature = Names.SYMBOLIC_STRING_VOID_SIGNATURE.getValue();
		} else if (name.equals("Lorg/eclipse/swt/events/SelectionEvent")) { 
			className = Names.SYMBOLIC_SELECTION_EVENT.getValue();
			signature = Names.VOID_SIGNATURE.getValue();
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
	public void identifyInputs(String desc, HashMap<Integer, String> methodParameters) {
		methodParameters.clear();
		int counter = 1;
		String parameters = desc.substring(desc.indexOf('(') + 1, desc.indexOf(')'));
		String[] groups = parameters.split(";");
		for (String g: groups) {
			if (g.contains("/") || g.toUpperCase() != g) {
				while (g.charAt(0) != 'L') {
					methodParameters.put(counter++, String.valueOf(g.charAt(0)));
					g = g.substring(1);
				}
				methodParameters.put(counter++, g);
			} else {
				for (Character c: g.toCharArray()) {
					methodParameters.put(counter++, c.toString());
				}
			}
		}
		if (DEBUG && VERBOSE) {
			for (Map.Entry<Integer, String> e: methodParameters.entrySet()) {
				log.info("Index: " + e.getKey() + " Type: " + e.getValue());
			}
		}
	}
	
	/**
	 * Detects if the mehtod is an event handler by matching it to
	 * e regular expression
	 * @param desc The method descriptor
	 */
	private void detectEventHandler(String name, String desc) {
		if (desc.matches(EVENT_HANDLER_DESC_PATTERN)) {
			Method method = new Method();
			method.setClazz(instrumentedClass);
			method.setName(name);
			method.setDesc(desc);
			eventHandlers.add(method);
		}	
	}
}