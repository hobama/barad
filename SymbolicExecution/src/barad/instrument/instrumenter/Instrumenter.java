package barad.instrument.instrumenter;
import static barad.util.Properties.DEBUG;
import static barad.util.Properties.LOG4J_PROPERTIES_FILE_NAME;
import static barad.util.Properties.PRINT_INSTRUMENTED_CLASS;
import static barad.util.Properties.SEPARATOR;
import static barad.util.Properties.VERBOSE;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import barad.instrument.adapters.SymbolicClassAdapter;
import barad.instrument.thread.SymbolicExecutionThread;
import barad.instrument.thread.SymbolicExecutionThread.SymbolicClassLoader;
import barad.util.Util;

@SuppressWarnings("all")
public class Instrumenter {
	private static String agentArgs;
	private static Instrumentation instrumentation;
	private static ClassInstrumenter classInstrumenter;
    
	public static void premain(String agentArgs, Instrumentation instrumentation) {
		Instrumenter.agentArgs = agentArgs;
		Instrumenter.instrumentation = instrumentation;
		Instrumenter.classInstrumenter = new ClassInstrumenter();
		instrumentation.addTransformer(classInstrumenter);
	}
	
	public static class ClassInstrumenter implements ClassFileTransformer {	
		private Logger log;
		private boolean bootstrapped;
		private String instrumentedClassesPath;
		private SymbolicExecutionThread symbolicExecutionThread;
		private HashSet<String> excludedPackages;
		
		public ClassInstrumenter() {
			DOMConfigurator.configure(LOG4J_PROPERTIES_FILE_NAME);
			log = Logger.getLogger(this.getClass());
			bootstrapped = false;
			instrumentedClassesPath = InitializeInstrumentedClassesPath();
			//Start symbolic execution thread
			symbolicExecutionThread = new SymbolicExecutionThread(Thread.currentThread());
			symbolicExecutionThread.start();
			excludedPackages = new HashSet<String>();
			if (agentArgs != null) {
				loadExcludedPackages();
			}
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
			//Do not process already instrumented classes
			if (loader instanceof SymbolicClassLoader) {
				if (DEBUG && VERBOSE) {
					log.info("Loading instrumented class: Loader: " + loader.toString() + " Class name: " + className);
				}
				return null;
			}
			byte[] instrumentedClass = null;
			byte[] classToExplore = classfileBuffer.clone();		
			try{				
				if (VERBOSE) {
					log.info("Processing of class " + className + " started.");
				}
				if (!Util.getLoadedClasses().containsKey(className.replace('/', '.'))) {
					if (loader != null) {
						Util.addClassName(loader, bootstrapped);
						bootstrapped = bootstrapped || true;
						String packageName = className.substring(0, className.lastIndexOf('/'));
						if (!excludedPackages.contains(packageName)) {
							//TODO: Remove. Added for testing
							if (className.contains("MainWindow")) {
							    instrumentedClass = exploreClass(classToExplore, className);
								List<String> methodsToExecute = new LinkedList<String>();
								methodsToExecute.add("button1WidgetSelected");
								methodsToExecute.add("button2WidgetSelected");
								symbolicExecutionThread.executeSymbolically(className, instrumentedClass, methodsToExecute);
								Util.writeClassToFile(instrumentedClass, new File("C:/workspace_symbolicexecution/SymbolicExecution/build/barad/examples/MainWindow.class"));
							}
						}
					}
				}
				if (VERBOSE) {
					log.info("Processing of class " + className + " completed");
					Util.printLoadedClasses();
				}	
			}catch(Exception e){
				log.warn("Error during class instrumentation. " + e, e);
			}
			//Load the original class in JVM
			return null;
		}
	
		/**
		 * Instruments a GUI class. This method first checks if the class 
		 * contains an event hanler and then performes instrumentation with
		 * symbolic classes. This check is needed to determine if the class
		 * is to be executed symbolically. This is done only with classes
		 * with event handlers.
		 * @param clazz The class to be instrumented
		 * FIXME: Fix the exception handling
		 * TODO: Add exception filtering
		 */
		private byte[] exploreClass(byte[] clazz, String className) {
			try {
				ClassReader cr = new ClassReader(clazz);
				ClassWriter cw = new ClassWriter(cr, 0);
				SymbolicClassAdapter sca = new SymbolicClassAdapter(cw);
				cr.accept(sca, 0);
				byte[] result = cw.toByteArray();			
				if (PRINT_INSTRUMENTED_CLASS) {
					String fileName = generateFileName(className);
					//Util.writeClassToFile(result, new File(fileName));
				}
				return result;
			}catch(Exception e){
				log.error("Uncaught exception during instrumentation of GUI class " + e, e);
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
			return instrumentedClassesPath + SEPARATOR + "barad" + SEPARATOR + "examples" + SEPARATOR + shortClassName + ".class";			
		}
		
		/**
		 * Loads the the file with packages that are excluded from the
		 * symbolic excecution analysis. The name of the file is passed
		 * as a paramenter to the java agent like this:
		 * -cp .. -javaagent:./build/barad/staticanalysis/Instrumenter.jar=ExcludedPackages.txt
		 * The above line is example of how to specify the java agent
		 * in the java launch configuration
		 */
		private void loadExcludedPackages() {
			File file = new File(agentArgs);
			if (file.exists() && file.isFile()) {
				BufferedReader reader = null;
				try {
					reader = new BufferedReader(new FileReader(file));
					String packageName = null;
					while((packageName = reader.readLine()) != null) {
						excludedPackages.add(packageName);
					}
				} catch (FileNotFoundException fnfe) {
					/*ignore*/ 
				} catch(IOException ioe) {
					log.error("Error while loading the file with excluded packages", ioe);
				}
				if (DEBUG) {
					for (String s: excludedPackages) {
						log.info("Excluded package: " + s);
					}
				}	
			} else {
				log.warn("The parameter passed to the java agent is not a valud file name: " + agentArgs);
			}
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
