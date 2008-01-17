package barad.instrument.instrumenter;
import static barad.util.Properties.LOG4J_PROPERTIES_FILE_NAME;
import static barad.util.Properties.PRINT_INSTRUMENTED_CLASS;
import static barad.util.Properties.SEPARATOR;
import static barad.util.Properties.TEMP_FOLDER;
import static barad.util.Properties.TESTED_APPLICATION_DESCRIPTOR_FILE;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.ProtectionDomain;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import barad.instrument.adapters.SymbolicClassAdapter;
import barad.instrument.thread.SymbolicExecutionThread;
import barad.util.Util;

/**
 * This class is a Java agent that performs symbolic execution of GUI 
 * event hanlers and generates test cases with inputs for tools that
 * generate and execute event sequences.
 * Note that in order the agent to work information aboout the tested
 * application schould be provided in: TestApplicationDescriptor.xml 
 * 
 * @param instrumentation
 */
@SuppressWarnings("all")
public class Instrumenter {
	private static Instrumentation instrumentation;
	private static ClassInstrumenter classInstrumenter;
	
	public static void premain(String agentArguments, Instrumentation instrumentation) {
		Instrumenter.instrumentation = instrumentation;
		ClassInstrumenter classInstrumenter = new ClassInstrumenter(agentArguments);
	}

	public static class ClassInstrumenter {	
		private Logger log;
		private boolean bootstrapped;
		private List<File> testedApplicationJarFiles;
		private URL[] testedApplicationPathURLs;
		private SymbolicExecutionThread symbolicExecutionThread;
		private String mainClassName;
		private HashSet<String> instrumentedPackages;
		private SymbolicExecutionClassLoader symbolicExecutionClassLoader;
		private boolean test = false;
		
		public ClassInstrumenter(String agentArguments) {
			//configure log4j
			URL log4jProperties = Thread.currentThread().getContextClassLoader().getResource(LOG4J_PROPERTIES_FILE_NAME);
			DOMConfigurator.configure(log4jProperties);
			this.log = Logger.getLogger(this.getClass());
			Util.loadFromSystemPropetiesXML();
			this.bootstrapped = false;
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(TESTED_APPLICATION_DESCRIPTOR_FILE);
			if (is == null) {
				log.error("Tested application descriptor " + TESTED_APPLICATION_DESCRIPTOR_FILE + " not found. System will exit!!!");
				System.exit(-1);
			}
			//load the tested application descriptor
			Properties testedAppDesc = new Properties();  
			try {
				testedAppDesc.loadFromXML(is);
				//process the descriptor
				processTestedApplicationDescriptor(testedAppDesc);
			} catch (Exception e) {
				log.error("Tested application descriptor " + TESTED_APPLICATION_DESCRIPTOR_FILE + " is invalid or error occured during its processing. System will exit!!!", e);
				System.exit(-1);
			}
			//create s custom class loader
			symbolicExecutionClassLoader = new SymbolicExecutionClassLoader(testedApplicationPathURLs, testedApplicationJarFiles, instrumentedPackages);
			//Start symbolic execution thread
			symbolicExecutionThread = new SymbolicExecutionThread(Thread.currentThread(), symbolicExecutionClassLoader);
			symbolicExecutionThread.start();
			symbolicExecutionThread.executeSymbolically(mainClassName);
		}

		/**
		 * Parse the input argumets array and generate the corresponding URLs
		 * @param args The program arguments array
		 * @param begIndex Index where paths begin
		 * @return Array of the URLs that define the class path of the
		 * custom class loader that I use to load the wrapped application
		 */
		private URL[] getClasspathURLs(String[] args) {
			int addedURLCount = 0;
			URL[] urls = new URL[args.length - 1]; 
			for (int i = 1; i < args.length; i++) {
				try {	
					StringBuilder fileName = new StringBuilder("");
					fileName.append("file:///");
					fileName.append(args[i]);
					if (args[i].endsWith(".jar")) {
						fileName.insert(0, "jar:");
						fileName.append("!/");
					}
					urls[addedURLCount++] = new URL(fileName.toString());
				} catch (MalformedURLException mue) {
					log.error("Path: " + args[i] + " can not be converto to a URL" + mue);
				}
			}	
			return urls;  
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
				return result;
			}catch(Exception e){
				log.error("Uncaught exception during instrumentation of GUI class " + e, e);
			}
			return null;
		}

		/**
		 * Loads the the file with packages that are excluded from the
		 * symbolic excecution analysis. The name of the file is passed
		 * as a paramenter to the java agent like this:
		 * -cp .. -javaagent:./build/barad/staticanalysis/Instrumenter.jar=ExcludedPackages.txt
		 * The above line is example of how to specify the java agent
		 * in the java launch configuration
		 */
		private void processTestedApplicationDescriptor(Properties testedAppDesc) throws FileNotFoundException, MalformedURLException {
			instrumentedPackages = new HashSet<String>();
			testedApplicationJarFiles = new LinkedList<File>();
			testedApplicationPathURLs = new URL[testedAppDesc.size() - 1];
			int index = 0;
			for (Map.Entry<Object, Object> e: testedAppDesc.entrySet()) {
				String key = (String)e.getKey();
				String value = (String)e.getValue();
				if (key.equals("main.class")) {
					mainClassName = value;
					log.debug("Tested application main class: " + key);
				} else {
					instrumentedPackages.add(key);
					log.debug("Tested application package: " + value);
					File file = new File(value);
					if (!file.exists()) {
						throw new FileNotFoundException("File: " + file.toString());
					} else if (file.getName().endsWith(".jar")) {
						testedApplicationJarFiles.add(file);
					}
					URL url = file.toURI().toURL();
					testedApplicationPathURLs[index++] = url;
				}		
			}			
		}	
	}
}
