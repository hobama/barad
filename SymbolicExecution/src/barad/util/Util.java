package barad.util;

import static barad.util.Properties.PROPERTIES_FILE_NAME;
import static barad.util.Properties.VERBOSE;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;
import java.util.WeakHashMap;

import org.apache.log4j.Logger;

public class Util {
	private static Logger log = Logger.getLogger(Util.class);
	private static Properties properties = new Properties();
	private static int classId;
	private static int methodId;
	private static WeakHashMap<String, String> loadedClasses = new WeakHashMap<String, String>();
	private static HashMap<Integer, String> programInputs = new HashMap<Integer, String>(); 
	
	public static void loadFromSystemPropetiesXML() {
		Util.loadFromSystemPropetiesXML(null);
	}
	
	/**
	 * Reads properties framework properties from user defined file xml.
	 * If it is invalid reads from the default @see FrameworkProperties
	 * @param file The file to be read from
	 */
	public static void loadFromSystemPropetiesXML(File file) {
		BufferedInputStream bis = null;
		try {
			if (file == null) throw new FileNotFoundException();
			bis = new BufferedInputStream(new FileInputStream(file));
		} catch (FileNotFoundException fnfe) {
			log.info("The propertis file provided by the user was invaid. Trying to read the default path. ");
			try {
				bis = new BufferedInputStream(new FileInputStream(new File(PROPERTIES_FILE_NAME)));
			} catch (FileNotFoundException fnfee) {
				log.error("No properties file loaded. " + fnfee);
				}
		} finally {
			if (bis != null) {
				try {
					properties.loadFromXML(bis);
				} catch (IOException ioe) {
					log.error("No properties file loaded. " + ioe);
				}
			}
		}
	}
	
	/**
	 * AuxiliaryMethod that copies one file to another
	 * @param fromFile the file to copy from
	 * @param The file to copy to
	 */
	public static void copyClassFile(File fromFile, File toFile) {
		try{			
			byte[] chunk = new byte[1024];
			BufferedInputStream reader = new BufferedInputStream(new FileInputStream(fromFile));
			BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(toFile));
			int size = 0;
			while ((size = reader.read(chunk)) > 0) {
				writer.write(chunk, 0, size);
			}
			writer.flush();
			reader.close();
			writer.close();
		}catch(Exception e){
			log.error("Error during copying " + fromFile + " to " + toFile + " " + e);
		}
	}
	
	/**
	 * Writes a class to e class file
	 * @param clazz The class bytecode as byte array
	 * @param fileName The file name 
	 */
	public static void writeClassToFile(byte[] clazz, File file) {
		try{
			BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(file));
			writer.write(clazz);
			writer.close();
		}catch(Exception e){
			log.error("Error while writing class to file " + file + " " + e);
		}
		log.info("File " + file + " written successfuly");
	}

	/**
	 * Reads all SWT widget class names. Only the classes which could register
	 * an event listener are listed. 
	 * @param classSet The set to which to add the class names
	 * @param fileName The name of the text file with the widget names
	 * TODO: Maybe I should implement this as xml file and Properties class
	 */
	public static void loadClassNames(HashSet<String> classSet, String fileName) {
		if (VERBOSE) {
			log.debug( "[" + fileName + "] "  + fileName);
		}
		File file = new File(fileName);
		try{
			BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line = null;
			while ((line = input.readLine()) != null) {
				classSet.add(line);
			}
			input.close();
		}catch(FileNotFoundException fnfe){
			log.error("The file with class names does not exist or is not properly set " + fnfe);
		}
		catch(IOException ioe) {
			log.error("Error occured during loading of class names " + ioe);
		}
	}
	
	/**
	 * Add all new classes from the current class loader in a weak hash map. 
	 * If the method is called for the first time is adds all the classes
	 * loaded by the current class loader. Otherwise, only the last class is 
	 * added. Not that this method is called after loading of a class that is
	 * not present in the map.
	 * @param loader The class loader that loaded the last class
	 */
	@SuppressWarnings("unchecked")
	public static void addClassName(ClassLoader loader, boolean addAllClasses) {
		Field classesField = null;
		try{
			classesField = ClassLoader.class.getDeclaredField("classes");
			classesField.setAccessible(true);
			if (classesField != null) {
				Vector<Class> classes = (Vector<Class>)classesField.get(loader);
				if (classes.size() > 0) {
					if (addAllClasses) {
						for (int i = 0; i < classes.size(); i++) {
							Util.getLoadedClasses().put(classes.get(i).getName(), null);
						}
					} else {
						Util.getLoadedClasses().put(classes.lastElement().getName(), null);
					}
				}
			}
		} catch (Throwable t) {
			log.error("Error while obtaining the declared classes by " + loader.toString() + t);
		}
	}
	
	/**
	 * Prints all loaded classes. Auxiliary method. 
	 */
	public static void printLoadedClasses() {
		if (loadedClasses.size() == 0) {
			log.warn("[EMPTY CLASS SET]");
			return;
		}
		log.info("[LOADEDED CLASSES]");
		try{
			for (Map.Entry<String, String> e: loadedClasses.entrySet()) {
				String printString = "";
				if (e.getKey() != null) {
					printString = printString + "[LOADED CLASS]" + e.getKey();
					log.info(printString);
				}
			}
		}catch(ConcurrentModificationException cme) {
			log.warn("Some classes were grabage collected and the iterator is invalid!" + cme);
		}
	}

	public static WeakHashMap<String, String> getLoadedClasses() {
		return loadedClasses;
	}

	public static void setLoadedClasses(WeakHashMap<String, String> loadedClasses) {
		Util.loadedClasses = loadedClasses;
	}

	public static Properties getProperties() {
		return Util.properties;
	}

	public static void setProperties(Properties properties) {
		Util.properties = properties;
	}

	public static int getClassId() {
		return classId;
	}

	public static void increaseClassId() {
		Util.classId++;
	}

	public static int getMethodId() {
		return methodId;
	}

	public static void increaseMethodId() {
		Util.methodId++;
	}

	public static HashMap<Integer, String> getProgramInputs() {
		return programInputs;
	}

	public static void setProgramInputs(HashMap<Integer, String> programInputs) {
		Util.programInputs = programInputs;
	}
}

