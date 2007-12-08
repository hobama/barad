package barad.profiler;

import java.util.HashMap;
import java.util.HashSet;

import org.apache.log4j.Logger;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;

public class InstrAdapter  extends ClassAdapter implements ProfilerProperties{
	
	private Logger log;
	private HashSet<String> methodSet;
	private HashSet<String> fieldSet;
	private HashSet<String> instructionSet;
	private HashMap<Integer, String> map;
	
	public InstrAdapter(ClassVisitor cv, HashSet<String> methodSet, HashSet<String> fieldSet, HashSet<String> instructionSet) {
		super(cv);
		this.log = Logger.getLogger(this.getClass());
		this.methodSet = methodSet;
		this.fieldSet= fieldSet;
		this.instructionSet = instructionSet;
		map = OpcodeToMnemonicMap.getMap();
	}
	
	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		log.debug("VISIT CLASS: " + name + ", Version: " + version + ", Access: " + access + ", SuperClass: " + superName + ";");
		cv.visit(version, access, name, signature, superName, interfaces);
	}
	
	@Override
	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		log.debug("VISIT ANNOTATION: " + desc + ", Visible: " + visible + ";");
		return cv.visitAnnotation(desc, visible);
	}
	
	@Override 
	public void visitAttribute(Attribute attr)  {
		log.debug("VISIT ATTRIBUTE: " + attr + ";");
		cv.visitAttribute(attr);
	}
	
	@Override
	public void visitEnd() {
		log.debug("VISIT END;");
		cv.visitEnd();
	}
	
	@Override
	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		log.debug("VISIT FIELD: " + name + ", Access: " + access + ", Descriptor: " + desc + ", Signature: " + signature + ";");
		fieldSet.add(OpcodeToAccessorMap.getAccessor(access) + " " + name + " " + desc + " " + signature);
		return cv.visitField(access, name, desc, signature, value);
	}
	
	@Override
	public void visitInnerClass(String name, String outerName, String innerName, int access) {
		log.debug("VISIT INNER CLASS: " + name + ", Access: " + access + ", Outer name: " + outerName + ", Inner name: " + innerName + ";");
		cv.visitInnerClass(name, outerName, innerName, access);
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		log.debug("VISIT METHOD: Access: " + map.get(access) + ", Name: " + name + ", Descriptor: " + desc + ", Signature: " + signature + ";");
		methodSet.add(OpcodeToAccessorMap.getAccessor(access) + " " + name + " " + desc + " " + signature);
		MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions); 
		if (mv != null) mv = new InstrMethodAdapter(mv, instructionSet);
		return mv;
	}
	
	@Override
	public void visitOuterClass(String owner, String name, String desc) {
		log.debug("VISIT OUTERCLASS: " + name + ", Descriptor: " + desc + ", Owner: " + owner + ";");
		cv.visitOuterClass(owner, name, desc);
	}

	@Override
	public void visitSource(String source, String debug) {
		log.debug("VISIT SOURCE: " + source + ", Debug: " + debug + ";");
		cv.visitSource(source, debug);
	}
}