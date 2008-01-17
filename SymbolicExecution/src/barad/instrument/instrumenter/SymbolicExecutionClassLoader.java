package barad.instrument.instrumenter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import barad.instrument.adapters.SymbolicClassAdapter;
import barad.util.Util;

/**
 * Custom class loader that provides public method for
 * loading classes from a byte array definition and name. This 
 * class loader loads only from predefined URLs and in case
 * of failure to load a class delegates to the parent class 
 * loader (opposite to the Java class loading mechanism) The
 * classes loaded by this class loader (the classes of the wrapped
 * application) are passed to the instrumenterImpl
 * @author svetoslavganov
 */
public class SymbolicExecutionClassLoader extends URLClassLoader {
	private Logger log;
	private HashMap<String, JAREntryJARFilePair> jarResources;
	private HashSet<String> instrumentedPackages; 
	
	/**
	 * Creates a nee class loader that will load classes only from
	 * the URLs passed to as a parameter 
	 * @param urls Array of URL used fof class loading
	 */
	public SymbolicExecutionClassLoader(URL[] urls, List<File> jarFileNames, HashSet<String> instrumentedPackages) {
		super(urls);
		this.log = Logger.getLogger(getClass());
		this.jarResources = new HashMap<String, JAREntryJARFilePair>();
		this.instrumentedPackages = instrumentedPackages;
		//Load hadnles to all resources in jar files - this is workaround for JDK BUG:4238086	
		for (File file: jarFileNames) {
			try {
				log.info("Loading resources from file: " + file);
				JarFile jarFile = new JarFile(file);
				addJarEntries(jarFile, jarFile.entries());
			} catch (IOException ioe) {
				log.error(ioe);
			}
		}
	}

	/**
	 * Adds all non-directory and non-manifest entries in a jar file to a map for
	 * fast lookup from resource name to jar entry that defines it.
	 * @param jarFile The jar file that contains the resources
	 * @param jarEntries The resources in the jar file
	 * NOTE: Because of JDK BUG:4238086 URLClassLoader can not load
	 * resources as a stream from jar files. I add all resurces located
	 * in jar files in map from resource name to jar entry in jar file. 
	 * In case I need the resource the jar file gives an inpout stream 
	 * to the jar entry for that resource
	 */
	private void addJarEntries(JarFile jarFile, Enumeration<JarEntry> jarEntries) {
		while(jarEntries.hasMoreElements()) {
			JarEntry jarEntry = jarEntries.nextElement();
			if (!jarEntry.isDirectory() && !jarEntry.getName().equals("META-INF/MANIFEST.MF")) {
				String entryName = jarEntry.getName();
				int index = 0;
				if ((index = entryName.lastIndexOf('/')) > -1 && ++index < entryName.length()) {
					entryName = entryName.substring(index);
					jarResources.put(entryName, new JAREntryJARFilePair(jarFile, jarEntry));
					log.info("Adding JarEntry for file: " + entryName );
				}
			}
		}
	}

	@Override 
	public Class<?> loadClass(String name) {
		return loadClass(name, false);
	}

	/**
	 * Loads a class. If the class is located in any of the URLs from
	 * which this loader loads classes, loaded class is passed to the
	 * instrumentation routine. Otherwise, the class is loaded as it is.
	 * @param name The name of the class
	 * @param resolve Flag if true the class is resolved
	 * @return The Class object associated with this class
	 * NOTE: Because of JDK BUG:4238086 URLClassLoader can not load
	 * resources as a stream from jar files. I add all resurces located
	 * in jar files in map from resource name to jar entry in jar file. 
	 * In case I need the resource the jar file gives an inpout stream 
	 * to the jar entry for that resource
	 */
	@Override
	public Class<?> loadClass(String className, boolean resolve) {
		Class clazz = null;
		int begIndex = 0;
		String resourceName = className;
		if ((begIndex = resourceName.lastIndexOf('.')) > -1) {
			resourceName = resourceName.substring(begIndex + 1);
		}
		resourceName = resourceName + ".class";
		InputStream is = null;
		//try to load resource from jar URLs 
		try {
			JAREntryJARFilePair jAREntryJARFilePair = jarResources.get(resourceName);
			if (!(jAREntryJARFilePair == null) && !(jAREntryJARFilePair.getJarEntry() == null)) {
				is = jAREntryJARFilePair.jarFile.getInputStream(jAREntryJARFilePair.getJarEntry());
				log.info(jAREntryJARFilePair.getJarEntry().getName());
			}
		} catch (IOException ioe) {
			/*ignore we try to load the class outside of the jar file*/
		}
		//try to load the resource on paths defined by non jar URLs
		if (is == null) {
			URL classURL = findResource(resourceName);
			if (classURL != null) {
				try {
					is = classURL.openStream();
				} catch (IOException ioe) {
					log.error("Error while trying to open an input stream to resource: " + classURL.toString(), ioe);	
				}
			}
		}
		//if the class is not loaded => delegate to the parent class loader
		if (is != null) {
			try {
				byte[] buffer = new byte[1048576];//1MB
				int pos = 0;
				byte[] chunk = new byte[1];
				while (is.read(chunk) > -1) {
					buffer[pos++] = chunk[0];
				}
				byte[] classDefinition = new byte[pos]; 
				for (int i = 0; i < pos; i++) {
					classDefinition[i] = buffer[i];
				}
				log.info("Instrumenting class " + className + "...");
				classDefinition = processClass(classDefinition, className);
				log.info("Class instrumented");
				log.info("Loading symbolically instumented class into JVM " + className + "...");
				clazz = getClass(classDefinition, className);
				log.info("Symbolically instumented class loaded into JVM");
			} catch (IOException ioe) {
				log.error("Error occured while reading class definition", ioe);
			}			
		} else {
			try {
				clazz = super.loadClass(className, resolve);
			} catch (ClassNotFoundException cnfe) {
				log.error("Error during loading of class: " + className, cnfe);
			}
		}
		return clazz;
	}

	/**
	 * Loads a class from class definition as byte array
	 * and class name
	 * @param classDefinition The class as a byte array
	 * @param className the name of the class
	 * @return The Class object associated with this class
	 */
	public Class getClass(byte[] classDefinition, String className) {
		Class clazz = super.defineClass(className, classDefinition, 0, classDefinition.length);
		super.resolveClass(clazz);
		return clazz;
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
	private byte[] processClass(byte[] processedClass, String className) {
		String packageName = className.substring(0, className.lastIndexOf('.'));
		//instrument only classes of the tested application
		if (instrumentedPackages.contains(packageName)) {
			try {
				ClassReader cr = new ClassReader(processedClass);
				ClassWriter cw = new ClassWriter(cr, 0);
				SymbolicClassAdapter sca = new SymbolicClassAdapter(cw);
				cr.accept(sca, 0);
				processedClass= cw.toByteArray();
				//store to file - for debugging
				if (!className.contains("$")) {
					Util.writeClassToFile(processedClass, new File("C:/workspace_symbolicexecution/SymbolicExecution/build/examples/MainWindow.class"));
				}
			}catch(Exception e){
				log.error("Uncaught exception during instrumentation of GUI class " + e, e);
			}
		}
		return processedClass;
	}
	
	/**
	 * Auxiliary class that serves to keep handle
	 * to the file that contains a jar entry
	 * @author svetoslavganov
	 *
	 */
	private static class JAREntryJARFilePair {
	
		public JAREntryJARFilePair (JarFile jarFile, JarEntry jarEntry) {
			this.jarFile = jarFile;
			this.jarEntry = jarEntry;
		}
	
		private JarFile jarFile;
		private JarEntry jarEntry;
	
		public JarEntry getJarEntry() {
			return jarEntry;
		}
	
		public JarFile getJarFile() {
			return jarFile;
		}
	}
}