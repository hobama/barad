import java.util.LinkedList;

import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Widget;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Class that serves as intermediary during the instrumentation
 * 
 * @author svetoslavganov
 *
 */
public class InstrAdapter  extends ClassAdapter implements TesterProperties{

	private Logger log = Util.getLog();
	private String instrumentedClassName;

	public InstrAdapter(ClassVisitor cv) {
		super(cv);
	}
	
	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		if (VERBOSE) {
			log.debug(this, "VISIT CLASS: " + name + ", Version: " + version + ", Access: " + access + ", SuperClass: " + superName + ";");
		} 
		this.instrumentedClassName = name;	
		cv.visit(version, access, name, signature, superName, interfaces);
	}
	
	@Override
	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		if (VERBOSE) {
			log.debug(this, "VISIT ANNOTATION: " + desc + ", Visible: " + visible + ";");
		}
		return cv.visitAnnotation(desc, visible);
	}
	
	@Override 
	public void visitAttribute(Attribute attr)  {
		if (VERBOSE) {
			log.debug(this, "VISIT ATTRIBUTE: " + attr + ";");
		}
		cv.visitAttribute(attr);
	}
	
	@Override
	public void visitEnd() {
		if (VERBOSE) {
			log.debug(this, "VISIT END;");
		}
		writeEventGeneratorMethods();
		cv.visitEnd();
	}
	
	@Override
	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		if (VERBOSE) {
			log.debug(this, "VISIT FIELD: " + name + ", Access: " + access + ", Descriptor: " + desc + ", Signature: " + signature + ";");
		}
		return cv.visitField(access, name, desc, signature, value);
	}
	
	@Override
	public void visitInnerClass(String name, String outerName, String innerName, int access) {
		if (VERBOSE) {
			log.debug(this, "VISIT INNER CLASS: " + name + ", Access: " + access + ", Outer name: " + outerName + ", Inner name: " + innerName + ";");
		}
		cv.visitInnerClass(name, outerName, innerName, access);
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions); 
		if (mv != null) {
			mv = new InstrMethodAdapter(this, mv, instrumentedClassName);
		}
		
		return mv;
	}
	
	@Override
	public void visitOuterClass(String owner, String name, String desc) {
		if (VERBOSE) {
			log.debug(this, "VISIT OUTERCLASS: " + name + ", Descriptor: " + desc + ", Owner: " + owner + ";");
		}
		cv.visitOuterClass(owner, name, desc);
	}

	@Override
	public void visitSource(String source, String debug) {
		if (VERBOSE) {
			log.debug(this, "VISIT SOURCE: " + source + ", Debug: " + debug + ";");
		}
		cv.visitSource(source, debug);
	}
	
	public Widget widget;
	public SelectionListener listener;

	
	private void writeEventGeneratorMethods() {
		LinkedList<String[]> methodGenerationParameters  = Util.getMethodGenerationParameters();
		while (methodGenerationParameters.size() > 0) {
			String[] parameters = methodGenerationParameters.remove();
			writeMethodSource(parameters[0], parameters[1], parameters[2], parameters[3], parameters[4]);
		}
	}

	private void writeMethodSource(String methodName, String factoryClassName, String invokedFactoryMethod, String invokedListenerMethod, String generatedEventClassName) {
		if (VERBOSE){
			log.info(this, "Global parameters: [listenerClassName]: " + Util.getListenerClassName() + " [listenerFieldName]: " + Util.getListenerFieldName() + " [widgetClassName]: " + Util.getWidgetClassName() + " [widgetFieldName]: " + Util.getWidgetFieldName());
			log.info(this, "Generating method with parameters: [methodName]: " + methodName + " [factoryClassName]:" + factoryClassName + " [invokedFactoryMethod]: " + invokedFactoryMethod + " [invokedListenerMethod]: " + invokedListenerMethod + " [invokedListenerMethod]: " + invokedListenerMethod);
		}		
		String listenerClassName = Util.getListenerClassName().replace('.','/');
		String widgetClassName = Util.getWidgetClassName().replace('.', '/');
		MethodVisitor mv = visitMethod(Opcodes.ACC_PUBLIC, methodName, "(Ljava/util/Properties;)V", null, null);
		mv.visitCode();
		Label l0 = new Label();
		mv.visitLabel(l0);
		//mv.visitLineNumber(176, l0);
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, instrumentedClassName, Util.getListenerFieldName(), "L" + listenerClassName + ";");
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitFieldInsn(Opcodes.GETFIELD, instrumentedClassName, Util.getWidgetFieldName(), "L" + widgetClassName + ";");
		mv.visitVarInsn(Opcodes.ALOAD, 1);
		mv.visitMethodInsn(Opcodes.INVOKESTATIC, factoryClassName, invokedFactoryMethod, "(Lorg/eclipse/swt/widgets/Widget;Ljava/util/Properties;)" + generatedEventClassName + ";");
		mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, listenerClassName, "widgetSelected", "(" + generatedEventClassName + ";)V");
		Label l1 = new Label();
		mv.visitLabel(l1);
		//mv.visitLineNumber(177, l1);
		mv.visitInsn(Opcodes.RETURN);
		Label l2 = new Label();
		mv.visitLabel(l2);
		//FIXME: The class name shoul not be derived but apssed by the parameter only
		mv.visitLocalVariable("this", "L" + instrumentedClassName + ";", null, l0, l2, 0);
		mv.visitLocalVariable("properties", "Ljava/util/Properties;", null, l0, l2, 1);
		mv.visitMaxs(3, 2);
		mv.visitEnd();
	}
}
