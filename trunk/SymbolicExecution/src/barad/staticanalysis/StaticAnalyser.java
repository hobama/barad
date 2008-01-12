package barad.staticanalysis;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import static barad.util.Properties.LOG4J_PROPERTIES_FILE_NAME;

/**
 * This class performs static analysis of a GUI application. It takes
 * as parameter a list of paths to the application class/jar files and
 * explores all class files while generating s test suite. 
 * The implememntation traverses recursively all subfolders and jar files. 
 * @output XML file with the generated test cases after the analysis
 * @author svetoslavganov
 */
public class StaticAnalyser {
	private static Logger log;
	private static StaticAnalisysClassLoader staticAnalisysClassLoader;
	private static String currentExploredPath;
	
	/**
	 * Performs static analysis of the target application. 
	 * @param args List of paths to the analysed application class/jar files
	 * @output XML file with the generated test cases after the analysis
	 * Invalid entries are ignored
	 */
	public static void main(String[] args) {
		//configure log4j
		log = Logger.getLogger(StaticAnalyser.class);
		DOMConfigurator.configure(LOG4J_PROPERTIES_FILE_NAME);
		//check parameters
		if (args.length < 1) {
			log.error("No paths to the analysed application class files provided. Terminating...");
			System.exit(0);
		}
		//create a class loader
		staticAnalisysClassLoader = new StaticAnalisysClassLoader();
		//explore classes
		for (String path: args) {
			currentExploredPath = path;
			File file = new File(path);
			if (file.exists()) {
				LinkedList<File> filesToExplore = new LinkedList<File>(Arrays.asList(file.listFiles()));
				if (filesToExplore.size() > 0) {
					explore(filesToExplore);
				} 
			} else {
				log.warn("Path parameter is invalid: " + path);
			}
		}
		//unload classes
		staticAnalisysClassLoader = null;
	}

	/**
	 * Explores Java Class files by visiting  recursively 
	 * all folders and Jar files 
	 * 
	 * @param files - List with file records to be explored
	 */
	private static void explore(LinkedList<File> files) {
		if (files.size() == 0) return;
		File file = files.removeFirst();
		if (file.isFile()) {	
			if (file.getName().endsWith("jar")) {
				try{
					JarFile jarFile = new JarFile(file);				
					Enumeration jarEntries = jarFile.entries();
					while (jarEntries.hasMoreElements()) {
						JarEntry nextEntry = (JarEntry)jarEntries.nextElement();
						if (!nextEntry.isDirectory() && nextEntry.getName().endsWith(".class")) {
							InputStream jis = jarFile.getInputStream(nextEntry);
							String className = nextEntry.getName();
							className = className.replace('/', '.');
							className = className.substring(0, className.lastIndexOf('.'));
							readClassAsByteArray(jis, className,nextEntry.getSize());
						}
					}					
				}catch(IOException ioe){
					log.error("Error when trying to explore jar file " + ioe);
				}
			}
			else if (file.getName().endsWith("class")) { 
				try{
					FileInputStream fis = new FileInputStream(file);
					String className = file.getAbsolutePath();
					className = className.substring(currentExploredPath.length() + 1);
					className = className.replace(File.separatorChar, '.');
					className = className.substring(0, className.lastIndexOf('.'));
					readClassAsByteArray(fis, className, file.length());
					fis.close();
				}catch(Exception e){e.printStackTrace();}
			}
		}else if (file.isDirectory()) {
			explore(new LinkedList<File>(Arrays.asList(new File(file.getPath()).listFiles())));
		}
		explore(files);
	}
	
	/**
	 * Reads a class definition from an input stream
	 * @param intpuStream The input stream
	 * @param className The name of the class
	 * @param classSize The size of the class defeinition
	 */
	private static void readClassAsByteArray(InputStream intpuStream, String className, long classSize) {
		byte[] classDefinition = new byte[(int)classSize];
		byte[] chunk = new byte[1024];
		int readBytes = 0;
		int latsReadIndex = 0;
		try {
			while ((readBytes = intpuStream.read(chunk)) > -1) {
				for (int i = 0; i < readBytes; i++) {
					classDefinition[latsReadIndex++] = chunk[i];
				}
			}
			staticAnalisysClassLoader.loadClassAsByteArray(classDefinition, className);
		} catch (IOException ioe) {
			log.error("Error while loading class " + className, ioe);
		}
	}
	
	/**
	 * Class loader that loads classes in the JVM and causes the 
	 * agent which performs the symbolic execution to explore these 
	 * the classes. Custom class loader is user because: 
	 * 	1. The path traversal visits class files and is more efficient
	 * 	to load the class directly form the file rather than calling
	 *  the class loader method loadClass. The latter would perform the
	 *  traversal again - unnecesary
	 *  2. After the analysis the class loader is disposed which would
	 *  unload all classes it has loaded i.e. the classes of the analysed 
	 *  application
	 * @author svetoslavganov
	 *
	 */
	public static class StaticAnalisysClassLoader extends ClassLoader {
		/**
		 * Loads a class from class definition as byte array and class name
		 * @param classDefinition The class as a byte array
		 * @param className the name of the class
		 * @return The Class object associated with this class
		 */
	     public void loadClassAsByteArray(byte[] classDefinition, String className) {
	    	 log.debug("Loading class: " + className);
	         Class clazz = super.defineClass(className, classDefinition, 0, classDefinition.length);
	         super.resolveClass(clazz);
	     } 
	}
}

