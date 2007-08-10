import java.io.File;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

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
		
		public ClassInstrumenter() {
			log = Util.getLog();
			Util.loadClassNames(Util.getWidgetClassNames(), TesterProperties.SWT_WIDGETS_FILE);
			Util.loadClassNames(Util.getEventGeneratorClassNames(), TesterProperties.EVENT_GENERATORS_FILE);
			bootstrapped = false;
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
				if (TesterProperties.VERBOSE) {
					log.info(this, "Processing of class " + className + " started.");
				}
				if (!Util.getLoadedClasses().containsKey(className)) {
					if (loader != null) {
						Util.addClassName(loader, bootstrapped);
						bootstrapped = bootstrapped || true;
						result = exploreClass(classToExplore, className);
					}
				}
				if (TesterProperties.VERBOSE) {
					log.info(this, "Processing of class " + className + " completed");
					Util.printLoadedClasses();
				}	
			}catch(Exception e){
				log.warn(this, "Error during class instrumentation instrumentation. " + e);
			}	
			return result;
		}
		
		/**
		 * Instruments a GUI class
		 * 
		 * @param clazz The class to be instrumented
		 * FIXME: Fix the exception handling
		 */
		private byte[] exploreClass(byte[] clazz, String className) {
			try{
				ClassWriter cw = new ClassWriter(0);
				InstrAdapter ia = new InstrAdapter(cw);
				ClassReader cr = new ClassReader(clazz);
				cr.accept(ia, 0);
				byte[] result = cw.toByteArray();
				if (TesterProperties.PRINT_INSTRUMENTED_CLASS) {
					String fileName = generateFileName(className);
					Util.writeClassToFile(result, new File(fileName));
				}
				return result;
			}catch(Exception e){
				log.error(Util.class.getName(), "Error during instrumentation of GUI class " + e);
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
			return TesterProperties.LOG_PATH + TesterProperties.SEPARATOR_CHAR + shortClassName + ".class"; 
		}
	}
}