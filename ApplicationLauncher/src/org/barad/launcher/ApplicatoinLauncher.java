package org.barad.launcher;

import static org.barad.launcher.LauncherProperties.LOG4J_PROPERTIES_FILE_NAME;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.barad.interfacemapping.Element;
import org.barad.interfacemapping.Map;
import org.barad.interfacemapping.Mapping;
import org.barad.interfacemapping.To;
import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

/**
 * Class that is intended to serve as a wrapper to the launched application.
 * The reason for that is that we want to use our own class loader for this 
 * particular appliaction. Such a class loader is needed because we want to
 * instrument classes before loading those in JVM. We do not use the package
 * java.lang.instrument because we plan to instrument classes under J2ME
 * environment which lacks the above mentioned package.
 * @author svetoslavganov
 */
@SuppressWarnings("all")
public class ApplicatoinLauncher {
	 private static HashMap<Class, HashMap<Method, HashMap<Class, Set<Method>>>> interfaceMap = new HashMap<Class, HashMap<Method, HashMap<Class, Set<Method>>>>();
	 private static Logger log;
	 
	 /**
	  * The main method of the wrapper application.
	  * @param args[0] The name of the main class of the wrapped application
	  * 	   args[1] The name of interface mapping file wich should match the following regex: 
	  * 	   [a-zA-Z]+?InterfaceMapping.xml 
	  *        args[2...n] The names of folders/jars which contain classes of 
	  *        the wrapped appliaction 
	  */
	 public static void main(String[] args) {
		 //make sure we have main class
		 if (args.length < 1) {
			 System.out.println("Main class name not provided. See javadoc dor instructions");
			 return;
		 }
		 if (args.length < 2) {
			 System.out.println("Interface mapping file name not provided. See javadoc dor instructions");
			 return;
		 }
		 if (!args[1].matches("[a-zA-Z]+?InterfaceMapping.xml")) {
			 System.out.println("Interface mapping name is invalid. See javadoc dor instructions");
			 return;
		 }
		 String mainClassName = args[0];
		 //configure log4j
		 DOMConfigurator.configure(LOG4J_PROPERTIES_FILE_NAME);
		 URL[] classpathURLs = null;
		 if (args.length > 1) { 
			 classpathURLs = getClasspathURLs(args);
		 }
		 //use custom class loader
		 ApplicationLauncherClassLoader loader = new ApplicationLauncherClassLoader(classpathURLs);  
		 //invoke the wrapped application
		 try {  
			 Class mainClass = loader.loadClass(mainClassName);  
			 Method main = mainClass.getMethod("main", new Class[] {String[].class});  
			 int modifiers = main.getModifiers();  
			 if (Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers)) {
				 main.invoke(null, new Object[] {new String[] {}});  
			 } else {  
				 throw new NoSuchMethodException();  
			 }  
		 } catch (Exception e) {  
			 log.error("Error running class " + mainClassName, e);  
		 }  
	 }

	 /**
	  * Parsees the input argumets array and generates the corresponding URLs
	  * @param args The program arguments array
	  * @param begIndex Index where paths begin
	  * @return
	  */
	 private static URL[] getClasspathURLs(String[] args) {  
		 int addedURLCount = 0;
		 URL[] urls = new URL[args.length - 1]; 
		 for (int i = 1; i < args.length; i++) {
			try {
				urls[addedURLCount++] = new File(args[i]).toURI().toURL();
			} catch (MalformedURLException mue) {
				log.warn("Path: " + args[i] + " can not be converto to a URL" + mue);
			}
		 }	
		 return urls;  
	 }  
	 
	 /**
	  * Custom class loader that provides public method for
	  * loading classes from a byte array definition and name
	  * @author svetoslavganov
	  */
	 public static class ApplicationLauncherClassLoader extends URLClassLoader {
		/**
	     * Creates a nee class loader that will load classes only from
	   	 * the URLs passed to as a parameter 
	   	 * @param urls Array of URL used fof class loading
	     */
		 public ApplicationLauncherClassLoader(URL[] urls) {
			super(urls);
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
	     */
		 @Override
		 public Class<?> loadClass(String className, boolean resolve) {
			 Class clazz = null;
			 int begIndex = 0;
			 String resourceName = className;
			 if ((begIndex = resourceName.lastIndexOf('.')) > -1) {
				 resourceName = resourceName.substring(begIndex + 1) + ".class";
			 }
			 URL classURL = findResource(resourceName);
			 if (classURL != null) {
				InputStream is = null;
				try {
					is = classURL.openStream();
				} catch (IOException ioe) {
					log.error("Error while trying to open an input stream to resource: " + classURL.toString(), ioe);	
				}
				byte[] instrumentedClass = exploreClass(is, className);
				clazz = getClass(instrumentedClass, className);
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
		  * Instruments classes replacing the abstract UI with
		  * concrete device speciffic library (SWT, voice interface etc.)
		  * 
		  * @param clazz The class to be instrumented
		  * FIXME: Fix the exception handling
		  */
		 private byte[] exploreClass(InputStream clazz, String className) {
			 byte[] instrumentedClass = null; 
			 try{
				 ClassWriter cw = new ClassWriter(0);
				 ClassAdapter ia = new ClassAdapter(cw);
				 ClassReader cr = new ClassReader(clazz);
				 cr.accept(ia, 0);
				 instrumentedClass = cw.toByteArray();
				 if (LauncherProperties.PRINT_INSTRUMENTED_CLASS) {
					 //String fileName = generateFileName(className);
					 //Util.writeClassToFile(result, new File(fileName));
				 }
			 }catch(Exception e){
				 log.error("Error during instrumentation of class " + className, e);
			 }
			 return instrumentedClass;
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
	}
}