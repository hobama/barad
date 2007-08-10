import java.util.HashSet;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class InstrMethodAdapter implements MethodVisitor, TesterProperties {
	private Logger log = Util.getLog();
	private static int nextId = 0;
	private ClassVisitor cv;
	private MethodVisitor mv;
	private String instrumentedClassName;
	private int currentLine;
	
	public InstrMethodAdapter(ClassVisitor cv, MethodVisitor mv, String instrumentedClassName) {
		this.cv = cv;
		this.mv = mv;		
		this.instrumentedClassName = instrumentedClassName;
	}

	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		if (VERBOSE){
			log.debug(this, "[VISIT ANNOTATION] Name: " + desc + ", Visible: " + visible + ";");
		}
		return mv.visitAnnotation(desc, visible);
	}

	public AnnotationVisitor visitAnnotationDefault() {
		if (VERBOSE){
			log.debug(this, "[VISIT ANNOTATION]" + ";");
		}
		return mv.visitAnnotationDefault();
	}

	public void visitAttribute(Attribute attr) {
		if (VERBOSE){
			log.debug(this, "[VISIT ATTRIBUTE] Atribute: " + attr.toString() + ";");
		}
		mv.visitAttribute(attr);
	}

	public void visitCode() {
		if (VERBOSE){
			log.debug(this, "[VISIT CODE]" + ";");
		}
		mv.visitCode();
	}

	public void visitEnd() {
		if (VERBOSE){
			log.debug(this, "[VISIT END]" + ";");
		}
		mv.visitEnd();
	}

	public void visitFieldInsn(int opcode, String owner, String name, String desc) {
		if (VERBOSE){
			log.debug(this, "[VISIT FIELD INSTRUCTION] Opcode: " + opcode + ", Owner: " + owner + ", Name: " + name + ", Descriptor: " + desc + ";");
		}
		mv.visitFieldInsn(opcode, owner, name, desc);
	}

	public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
		if (VERBOSE){
			log.debug(this, "[VISIT FRAME] Type: " + type + ", Local vriables#: " + nLocal + ", Stack elements#: " + nStack + ";");
		}
		mv.visitFrame(type, nLocal, local, nStack, stack);
	}

	public void visitIincInsn(int var, int increment) {
		if (VERBOSE){
			log.debug(this, "[VISIT INCREMENTAL INSTRUCTION] Variable: " + var + ", Increment: " + increment + ";");
		}
		mv.visitIincInsn(var, increment);
	}

	public void visitInsn(int opcode) {
		if (VERBOSE){
			log.debug(this, "[VISIT INSTRUCTION] Opcode: " + opcode + ";");
		}
		mv.visitInsn(opcode);
	}

	public void visitIntInsn(int opcode, int operand) {
		if (VERBOSE){
			log.debug(this, "[VISIT INTEGER INSTRUCTION] Opcode: " + opcode + ", Operand: " + operand + ";");
		}
		mv.visitIntInsn(opcode, operand);
		}
	
	public void visitJumpInsn(int opcode, Label label) {
		if (VERBOSE){
			log.debug(this, "[VISIT JUMP INSTRUCTION] Opcode: " + opcode + ", Label: " + label.toString() + ";");
		}
		mv.visitJumpInsn(opcode, label);
	}

	public void visitLabel(Label label) {
		if (VERBOSE){
			log.debug(this, "[VISIT LABEL] Label: " + label.toString() + ";");
		}
		mv.visitLabel(label);
	}

	public void visitLdcInsn(Object cst) {
		if (VERBOSE){
			log.debug(this, "[VISIT LDC INSTRUCTION] Object: " + cst.toString() + ";");
		}
		mv.visitLdcInsn(cst);
	}

	public void visitLineNumber(int line, Label start) {
		if (VERBOSE){
			log.debug(this, "[VISIT LINE: Number] " + line + ", Start: " + start.toString() + ";");
		}
		/*
		int currentLineNumber = Util.getLineNumber();
		currentLineNumber = currentLineNumber + currentLineNumber - 
		Util.setLineNumber(Util.)
		*/
		mv.visitLineNumber(currentLine, start);
	}

	public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
		if (VERBOSE){
			log.debug(this, "[VISIT LOCAL VARIABLE] Name: " + name + ", Descriptor: " + desc + ", Signature: " + signature + ", Start: " + start.toString() + ", End: " + end .toString()+ ", Index: " + index + ";");
		}
		mv.visitLocalVariable(name, desc, signature, start, end, index);
	}

	public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
		if (VERBOSE){
			log.debug(this, "[VISIT LOOKUP SWITCH INSTRUCTION] DefHandlerBlock: " + dflt.toString() + ";");
		}
		mv.visitLookupSwitchInsn(dflt, keys, labels);
	}

	public void visitMaxs(int maxStack, int maxLocals) {
		if (VERBOSE){
			log.debug(this, "[VISIT MAX] MaxStack: " + maxStack + ", Maxlocals: " + maxLocals + ";");
		}
		mv.visitMaxs(maxStack, maxLocals);
	}

	public void visitMethodInsn(int opcode, String owner, String name, String desc) {	
		if (Util.getWidgetClassNames().contains(owner)) {
			if (opcode == Opcodes.INVOKEVIRTUAL && desc.length() > 3) {	
				if (desc.matches(LISTENER_REGISTRATION_DESCRIPRTOR) && isEventListener(extractParameterClassName(desc))) {
					log.debug(this, "[GENERATE EVENT LISTENER FILED] " + desc);
					initializeAuxiliaryFields(owner, desc);
					generateEventListenerField(owner, desc);	
				}
			}
		}
		if (VERBOSE){
			log.debug(this, "[VISIT METHOD INSTRUCTION] Opcode: " + opcode + ", Owner: " + owner + ", Name: " + name + " Descriptor: " + desc + ";");
		}
		mv.visitMethodInsn(opcode, owner, name, desc);
	}

	/**
	 * Extracts the name of the first parameter class from bytecode representation
	 * of a method descriptor. Returns the class name of the first parameter.
	 * It is inteneded to be used by classes that take only one parameter.
	 * @param desc The method descriptor
	 * @return The name class of the firs method parameter
	 */
	private String extractParameterClassName(String desc) {
		int endIndex = desc.indexOf(';');
		if (endIndex == -1) {
			endIndex = desc.length();
		}
		int begIndex = 1;
		if (desc.charAt(0) != '(') {
			begIndex = 0;
		} 
		String methodParameter = desc.substring(begIndex, endIndex);	
		methodParameter = methodParameter.replace('/', '.');
		while (methodParameter.charAt(0) == 'I') {
			methodParameter = methodParameter.substring(1);
		}
		while (methodParameter.charAt(0) == 'L') {
			methodParameter = methodParameter.substring(1);
		}
		if (DEBUG) {
			log.error(this, "Parameter class name: " + methodParameter);
		}
		return methodParameter;
	}
	
	/**
	 * Set the auxiliary fields of the Util class. They are
	 * used for method generation from the InstrAdapter.
	 * @param owner The widget the registers the listener
	 * @param desc Descriptor of the metod that adds the listener
	 */
	private void initializeAuxiliaryFields(String owner, String desc) {
		Util.setListenerFieldName(generateListenerFieldName(owner, desc));
		Util.setListenerClassName(extractParameterClassName(desc));
		Util.setWidgetFieldName(generateWidgetFieldName(owner));
		Util.setWidgetClassName(extractParameterClassName(owner));
	}

	/**
	 * Determines if a class is an event listener by verifying
	 * if the class implements org.eclipse.swt.internal.SWTEventListener
	 * @param className The name of the class
	 * @return True if this class is an event listener, false otherwise.
	 */
	private boolean isEventListener(String className) {
		if (!className.equals(instrumentedClassName)) {
			try {
				Class clazz = Class.forName(className);
				Class[] interfaces = clazz.getInterfaces();
				for (Class c: interfaces) {
					if (c.getName().equals("org.eclipse.swt.internal.SWTEventListener")) {
						if (VERBOSE) {
							log.info(this, "Identified event lsitener " + className);
						}
						return true;
					}
				}
			} catch (ClassNotFoundException cnfe) {
				log.error(this, "Error during class loading " + cnfe);
			}
		} else {
			log.warn(this, "The interface is the instrumented class.");
		}
		return false;
	}

	/**
	 * Creates a public field to store a reference to an event handler
	 * and add the widget/listener pair to the registry
	 * @param The class owner (See ASM documentation)
	 * @param The descriptor (See ASM documentation)
	 */
	private void generateEventListenerField(String owner, String desc) {
		String filedDesc = desc.substring(1, desc.lastIndexOf(')'));
		String listenerFieldName = Util.getListenerFieldName();
		String widgetFieldName = Util.getWidgetFieldName();
		//null values should be considered if generics are used
		cv.visitField(Opcodes.ACC_PUBLIC, listenerFieldName, filedDesc, null, null).visitEnd();
		currentLine++;
		//maybe I should change the frame size manually
		mv.visitInsn(Opcodes.DUP);
		currentLine++;
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		currentLine++;
		mv.visitInsn(Opcodes.SWAP);
		currentLine++;
		//Store the widget to a public field
		mv.visitFieldInsn(Opcodes.PUTFIELD, instrumentedClassName, listenerFieldName, filedDesc);
		currentLine++;
		//Add a field for storing a reference to the widget
		cv.visitField(Opcodes.ACC_PUBLIC, widgetFieldName, "Lorg/eclipse/swt/widgets/Widget;", null, null).visitEnd();
		mv.visitInsn(Opcodes.DUP2);
		mv.visitInsn(Opcodes.POP);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitInsn(Opcodes.SWAP);
		mv.visitFieldInsn(Opcodes.PUTFIELD, instrumentedClassName, widgetFieldName, "Lorg/eclipse/swt/widgets/Widget;");
		currentLine++;
		//Add the widget/listener pair to the registry
		mv.visitInsn(Opcodes.DUP2);
		currentLine++;
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/webcodefocus/blogcaster/view/WidgetRegistry", "addWidget", "(Lorg/eclipse/swt/widgets/Widget;Lorg/eclipse/swt/internal/SWTEventListener;)V");
		currentLine++;
		log.debug(this, "Generated field: " + listenerFieldName + " " + filedDesc);
		generateEventTriggerMethodParameters();
	}
	
	/**
	 * Creates a public method to invoke the method of an event listener
	 * that is waiting for a specific event 
	 * The Method has the following structure:
	 * 
	 * public void [MethodName](Properties properties) {
	 * 		[EventGenerator].generate[TypeOfTheEvent]([FieldWithReferenvceToWidget], properties);
	 * }
	 * 
	 * @param The class owner (See ASM documentation)
	 * @param The descriptor (See ASM documentation)
	 */
	
	private HashSet<String> getClassInterfaces(String className) {
		HashSet<String> interfacesNameSet = null;
		try {
			Class clazz = Class.forName(className);
			Class[] interfaces = clazz.getInterfaces();
			interfacesNameSet = new HashSet<String>();
			if (clazz.isInterface()) {
				interfacesNameSet.add(clazz.getName());
			}
			for (Class c: interfaces) {
				interfacesNameSet.add(c.getName());
			}
			if (VERBOSE) {
				for (String s: interfacesNameSet) {
					log.debug(this, "ADDED INTERFACE: " + s);
				}
			}
		} catch (ClassNotFoundException cnfe) {
			log.error(this, "Wrong class type or class name " + cnfe);
		}
		return interfacesNameSet;
	}
	
	private void generateEventTriggerMethodParameters() {
		String methodName = generatMethodName(Util.getListenerFieldName());
		HashSet<String> interfacesNameSet = getClassInterfaces(Util.getListenerClassName());
		String[] parameters = null;
		if (interfacesNameSet.contains("org.eclipse.swt.events.SelectionListener")) {
			parameters = new String[] {methodName + "WidgetSelected", "eventgenerators/SelectionEventGenerator", "generateSelectionEvent", "widgetSelected", "Lorg/eclipse/swt/events/SelectionEvent"};
			Util.getMethodGenerationParameters().add(parameters);
			parameters = new String[] {methodName + "WidgetDefaultSelected", "eventgenerators/SelectionEventGenerator", "generateSelectionEvent", "widgetDefaultSelected", "Lorg/eclipse/swt/events/SelectionEvent"};
			Util.getMethodGenerationParameters().add(parameters);
		}
		/*
		 else if (interfacesSet.contains("Lorg/eclipse/swt/internal/MouseListener")) {
		} else if (interfacesSet.contains("Lorg/eclipse/swt/internal/MenuListener")) {
		} else if (interfacesSet.contains("Lorg/eclipse/swt/internal/KeyListener")) {
		} else if (interfacesSet.contains("Lorg/eclipse/swt/internal/ModifyListener")) {
		} else if (interfacesSet.contains("Lorg/eclipse/swt/internal/MouseMoveListener")) {
		} else if (interfacesSet.contains("Lorg/eclipse/swt/internal/ControlListener")) {
		} else if (interfacesSet.contains("Lorg/eclipse/swt/internal/DisposeListener")) {
		} else if (interfacesSet.contains("Lorg/eclipse/swt/internal/MouseTrackListener")) {
		} else if (interfacesSet.contains("Lorg/eclipse/swt/internal/FocusListener")) {
		} else if (interfacesSet.contains("Lorg/eclipse/swt/internal/ShellListener")) {
		} else if (interfacesSet.contains("Lorg/eclipse/swt/internal/TraverseListener")) {
		} else if (interfacesSet.contains("Lorg/eclipse/swt/internal/ArmListener")) {	
		} else if (interfacesSet.contains("Lorg/eclipse/swt/internal/TreeListener")) {
		} else if (interfacesSet.contains("Lorg/eclipse/swt/internal/VerifyListener")) {
		} else if (interfacesSet.contains("Lorg/eclipse/swt/internal/HelpListener")) {
		} else if (interfacesSet.contains("Lorg/eclipse/swt/internal/PaintListener")) {				
		}
		*/
	}
	
	/**
	 * Generates a new field name. The name is obtained by concatenantion 
	 * of the widget type, the listener type and the current index of the 
	 * added by instrumentation field/method.
	 * Example: MenuItemSelectionListener1 
	 * @param owner The name of the class owner (See ASM documantation)
	 * @param desc The descriptor of the filed (See ASM documantation)
	 * @return New field name
	 */
	private String generateListenerFieldName(String owner, String desc) {
		String prefix = owner.substring(owner.lastIndexOf('/') + 1);
		String suffix = desc.substring(desc.lastIndexOf('/') + 1, desc.indexOf(';'));
		String id = String.valueOf(nextId);
		return prefix + suffix + id;
	}
	
	private String generateWidgetFieldName(String owner) {
		String prefix = owner.substring(owner.lastIndexOf('/') + 1);
		String id = String.valueOf(nextId++);
		return prefix + id;
	}
	
	/**
	 * Generates a new method name. The name is obtained by concatenantion 
	 * of "Trigger", and the filed name that stores a handle to the listener
	 * whhose trigger method is to be invoked
	 * Example: TriggerMenuItemSelectionListener1 
	 * @param fieldName
	 * @return New method name
	 */
	private String generatMethodName(String fieldName) {
		return "Trigger" + fieldName;
	}

	public void visitMultiANewArrayInsn(String desc, int dims) {
		if (VERBOSE){
			log.debug(this, "[VISIT MULTIANEW INSTRUCTION] Descriptor: " + desc + ", Dimensions: " + dims + ";");
		}
		mv.visitMultiANewArrayInsn(desc, dims);
	}

	public AnnotationVisitor visitParameterAnnotation(int parameter, String desc, boolean visible) {
		if (VERBOSE){
			log.debug(this, "[VISIT PARAMETER ANNOTATION] Parameter: " + parameter + ", Descriptor: " + desc + ", Visible: " + visible + ";");
		}
		return mv.visitParameterAnnotation(parameter, desc, visible);
	}

	public void visitTableSwitchInsn(int min, int max, Label dflt, Label[] labels) {
		if (VERBOSE){
			log.debug(this, "[VISIT TABLE SWITCH INSTRUCTION] Min: " + min + ", Max: " + max + ", DefHandlerBlock: " + dflt.toString() + ";");
		}
		mv.visitTableSwitchInsn(min, max, dflt, labels);
	}

	public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
		if (VERBOSE){
			log.debug(this, "[VISIT TRY/CATCH BLOCK INSTRUCTION] Start: " + start.toString() + ", End: " + end.toString() + ", Handler: " + handler.toString()  + ", Type: " + type + ";");
		}
		mv.visitTryCatchBlock(start, end, handler, type);
	}

	public void visitTypeInsn(int opcode, String desc) {
		if (VERBOSE){
			log.debug(this, "[VISIT TYPE INSTRUCTION] Opcode: " + opcode + ", Descriptor: " + desc + ";");
		}
		mv.visitTypeInsn(opcode, desc);
	}

	public void visitVarInsn(int opcode, int var) {
		if (VERBOSE){
			log.debug(this, "[VISIT VARIABLE INSTRUCTION] Opcode: " + opcode + ", Variable: " + var + ";");
		}
		mv.visitVarInsn(opcode, var);
	}
}

