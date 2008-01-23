package barad.instrument.adapters;

import static barad.util.Properties.VERBOSE;

import java.util.HashSet;

import org.apache.log4j.Logger;
import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;

import barad.instrument.instrumenter.Method;
import barad.instrument.util.Names;
import barad.util.Util;

public class SymbolicClassAdapter  extends ClassAdapter {
	private Logger log;
	private String instrumentedClass;
	private HashSet<Method> removedMethods;

	public SymbolicClassAdapter(ClassVisitor cv) {
		super(cv);
		this.log = Logger.getLogger(this.getClass().getName());
		this.removedMethods = new HashSet<Method>();
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		if (VERBOSE) log.debug("VISIT CLASS: " + name + ", Version: " + version + ", Access: " + access + ", SuperClass: " + superName + ";");
		instrumentedClass = name;
		String newName = Util.modifyDescriptor("L" + superName);
		if (newName != null) {
			superName = newName.substring(1,newName.length() - 1);
		}
		cv.visit(version, access, name, signature, superName, makeSerializable(interfaces));
	}

	@Override
	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		if (VERBOSE) log.debug("VISIT FIELD: " + name + ", Access: " + access + ", Descriptor: " + desc + ", Signature: " + signature + ";");
		desc = Util.modifyDescriptor(desc);
		return cv.visitField(access, name, desc, signature, value);
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		if (VERBOSE) log.debug("VISIT METHOD: " + name + ", Access: " + access + ", Descriptor: " + desc + ", Signature: " + signature + ";");
		MethodVisitor mv = null;
		Method method = new Method();
		method.setClazz(instrumentedClass);
		method.setDesc(desc);
		method.setName(name);
		if (!removedMethods.contains(method)) {
			//remove the main method and all methods called from the main method
			if ((name.equals(Names.MAIN_METHOD.getValue()) && desc.equals(Names.MAIN_METHOD_SIGNATURE.getValue()))) {
				//remove all methods called by the main method
				mv = new MethodDetectorMethodVisitor(removedMethods, instrumentedClass);
			} else {
				mv = cv.visitMethod(access, name, Util.modifyDescriptor(desc), signature, exceptions); 
				if (mv != null) {
					mv = new SymbolicMethodAdapter(mv);
				}
			}	
		} 
		return mv;
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