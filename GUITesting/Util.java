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
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Vector;
import java.util.WeakHashMap;

public class Util {
	private static Logger log = new Logger();
	private static WeakHashMap<String, String> loadedClasses = new WeakHashMap<String, String>();
	private static HashSet<String> widgetClassNames = new HashSet<String>();
	private static HashSet<String> eventGeneratorsClassNames = new HashSet<String>();
	private static LinkedList<String[]> methodGenerationParameters = new LinkedList<String[]>();
	private static String listenerFieldName;
	private static String listenerClassName;
	private static String widgetFieldName;
	private static String widgetClassName;
	private static int lineNumber; 
	
	public static int count = 0;
	

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
			log.error(Util.class.getName(), "Error during copying " + fromFile + " to " + toFile + " " + e);
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
			log.error(Util.class.getName(), "Error while writing class to file " + file + " " + e);
		}
		log.info(Util.class.getName(), "Error while writing class to file " + file);
	}

	/**
	 * Reads all SWT widget class names. Only the classes which could register
	 * an event listener are listed. 
	 * @param classSet The set to which to add the class names
	 * @param fileName The name of the text file with the widget names
	 * TODO: Maybe I should implement this as xml file and Properties class
	 */
	public static void loadClassNames(HashSet<String> classSet, String fileName) {
		if (TesterProperties.VERBOSE) {
			log.debug(Util.class, "[" + fileName + "] "  + fileName);
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
			log.error(Util.class, "The file with class names does not exist or is not properly set " + fnfe);
		}
		catch(IOException ioe) {
			log.error(Util.class, "Error occured during loading of class names " + ioe);
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
			log.error(Util.class, "Error while obtaining the declared classes by " + loader.toString() + t);
		}
	}
	
	/**
	 * Prints all loaded classes. Auxiliary method. 
	 */
	public static void printLoadedClasses() {
		if (loadedClasses == null) {
			log.warn(Util.class, "[EMPTY CLASS SET]");
			return;
		}
		log.info(Util.class, "[LOADEDED CLASSES]");
		try{
			for (Map.Entry<String, String> e: loadedClasses.entrySet()) {
				String printString = "";
				if (e.getKey() != null) {
					printString = printString + "[LOADED CLASS]" + e.getKey();
					log.info(Util.class, printString);
				}
			}
		}catch(ConcurrentModificationException cme) {
			log.warn(Util.class, "Some classes were grabage collected and the iterator is invalid!" + cme);
		}
	}
	
	/**
	 * Get the logger with appropriate setup for the current class
	 * @param loggedClass The name of the logged class
	 * @return The logger
	 */
	public static Logger getLog() {
		return log;
	}

	public static void setLog(Logger log) {
		Util.log = log;
	}

	public static HashSet<String> getWidgetClassNames() {
		return widgetClassNames;
	}

	public static void setWidgetClassNames(HashSet<String> widgetClassNames) {
		Util.widgetClassNames = widgetClassNames;
	}
	
	public static WeakHashMap<String, String> getLoadedClasses() {
		return loadedClasses;
	}

	public static void setLoadedClasses(WeakHashMap<String, String> loadedClasses) {
		Util.loadedClasses = loadedClasses;
	}

	public static HashSet<String> getEventGeneratorClassNames() {
		return eventGeneratorsClassNames;
	}

	public static void setEventGeneratorClassNames(HashSet<String> eventGeneratorsClassNames) {
		Util.eventGeneratorsClassNames = eventGeneratorsClassNames;
	}

	public static LinkedList<String[]> getMethodGenerationParameters() {
		return methodGenerationParameters;
	}

	public static void setMethodGenerationParameters(
			LinkedList<String[]> methodGenerationParameters) {
		Util.methodGenerationParameters = methodGenerationParameters;
	}

	public static String getListenerClassName() {
		return listenerClassName;
	}

	public static String getListenerFieldName() {
		return listenerFieldName;
	}

	public static void setListenerFieldName(String listenerFieldName) {
		Util.listenerFieldName = listenerFieldName;
	}

	public static String getWidgetClassName() {
		return widgetClassName;
	}

	public static void setWidgetClassName(String widgetClassName) {
		Util.widgetClassName = widgetClassName;
	}

	public static String getWidgetFieldName() {
		return widgetFieldName;
	}

	public static void setWidgetFieldName(String widgetFieldName) {
		Util.widgetFieldName = widgetFieldName;
	}

	public static void setListenerClassName(String listenerClassName) {
		Util.listenerClassName = listenerClassName;
	}

	public static int getLineNumber() {
		return lineNumber;
	}

	public static void setLineNumber(int lineNumber) {
		Util.lineNumber = lineNumber;
	}
	
	public static void incrementLineNumber() {
		Util.lineNumber++;
	}
}
