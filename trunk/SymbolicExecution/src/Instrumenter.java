import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import static barad.util.Properties.LOG4J_PROPERTIES_FILE_NAME;
import static barad.util.Properties.PRINT_INSTRUMENTED_CLASS;
import static barad.util.Properties.PROPERTIES_FILE_NAME;
import static barad.util.Properties.SEPARATOR;
import static barad.util.Properties.VERBOSE;
import barad.instrument.SymbolicClassAdapter;
import barad.util.Util;

@SuppressWarnings("all")
public class Instrumenter {
	private static String arguments;
	private static Instrumentation instrumentation;
	private static ClassInstrumenter classInstrumenter;
    
	public static void premain(String arguments, Instrumentation instrumentation) {
		Instrumenter.arguments = arguments;
		Instrumenter.instrumentation = instrumentation;
		Instrumenter.classInstrumenter = new ClassInstrumenter();
		instrumentation.addTransformer(classInstrumenter);
	}
	
	public static class ClassInstrumenter implements ClassFileTransformer {
		
		private Logger log;
		private boolean bootstrapped;
		private String instrumentedClassesPath;
		
		public ClassInstrumenter() {
			DOMConfigurator.configure(LOG4J_PROPERTIES_FILE_NAME);
			log = Logger.getLogger(this.getClass());
			bootstrapped = false;
			instrumentedClassesPath = InitializeInstrumentedClassesPath(); 
		}
		
		/**
		 * Examines and instruments all classes that have widgets which register for event listeners.
		 * Logs the beginning and the end of the process. Multiple processing of the same class is
		 * avoided. 
		 * @param className The name of the class being examined
		 * @param classfileBuffer The class as bytes
		 * @return The examined class (Instrumented if neeed)
		 * @throws IllegalClassFormatException 
		 */
		public byte[] transform(ClassLoader loader, String className, Class classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
			byte[] result = null;
			byte[] classToExplore = classfileBuffer.clone();
			try{
				if (VERBOSE) {
					log.info("Processing of class " + className + " started.");
				}
				if (!Util.getLoadedClasses().containsKey(className)) {
					if (loader != null) {
						Util.addClassName(loader, bootstrapped);
						bootstrapped = bootstrapped || true;
						//Temporary if statement
						if (className.equals("barad/examples/IfExample") ||
						    className.equals("barad/examples/Example")) {
							result = exploreClass(classToExplore, className);
						}
					}
				}
				if (VERBOSE) {
					log.info("Processing of class " + className + " completed");
					Util.printLoadedClasses();
				}	
			}catch(Exception e){
				log.warn("Error during class instrumentation instrumentation. " + e);
			}	
			return result;
		}
		
		/**
		 * Instruments a GUI class
		 * 
		 * @param clazz The class to be instrumented
		 * FIXME: Fix the exception handling
		 * TODO: Add exception filtering
		 */
		private byte[] exploreClass(byte[] clazz, String className) {
			try{
				ClassWriter cw = new ClassWriter(0);
				SymbolicClassAdapter ia = new SymbolicClassAdapter(cw);
				ClassReader cr = new ClassReader(clazz);
				cr.accept(ia, 0);
				byte[] result = cw.toByteArray();
				if (PRINT_INSTRUMENTED_CLASS) {
					String fileName = generateFileName(className);
					Util.writeClassToFile(result, new File(fileName));
				}
				return result;
			}catch(Exception e){
				log.error("Uncaught exception during instrumentation of GUI class " + e);
			}
			return null;
		}
		
		/**
		 * Generate file name for temporary copies of the instrumented classes.
		 * This method has auxiliary purpose. @see TesterProperties.PRINT_INSTRUMENTED_CLASS 
		 * @param className The internal JVM class name
		 * @return Name for the temporary file
		 */
		private String generateFileName(String className) {
			String shortClassName = className.substring(className.lastIndexOf('/') + 1);
			return instrumentedClassesPath + SEPARATOR + shortClassName + ".class";			
		}
		
		private String InitializeInstrumentedClassesPath() {
			String path = Util.getProperties().getProperty("instrumented.classes.path", System.getProperty("user.dir"));
			File file = new File(path);
			if (!file.isDirectory()) {
				file.mkdirs();
			}
			return path;
		}
	}
}
