package barad.profiler;


import java.util.HashSet;

/**
 * Container class that stores some important parameters
 * @author svetoslavganov
 */
public interface ProfilerProperties {
	/**
	 * @return The full path to the folder containing the instrumentation classes
	 */
	public static final String INSTR_CLASSES_DIR = "c:\\Projects\\Workspace\\SymbolicExecution\\instrument\\";
	
	/**
	 * @return The name of the jar file with the instrumentation classes
	 */
	public static final String INSTR_JAR_FILE = "Instrument.jar";
	
	/**
	 * @return The name of package with the instrumentation classes
	 */
	public static final String INSTR_PACKAGE = "instrument\\";
	
	/**
	 * @return The name of the jar file with the instrumentation classes
	 */
	public static final String LOG_FILE = "ProfileLog.txt";
	
	/**
	 * @return If the system is in dubug mode
	 */
	public static final boolean DEBUG = true;
	
	/**
	 * @return The name of the path to the profiled application
	 */
	public static final String APP_PATH = "C:\\GUIApplications\\sf_crosswordsage\\CLASS FILES\\crosswordsage";//"C:\\GUIApplications\\sf_crosswordsage"; //"C:\\freemind\\";
	
	/**
	 * @return The name of the file with class names in the
	 * profiled application
	 */
	public static final String CLASSES_FILE = "Classes.txt";
	
	/**
	 * @return The name of the file with method profiles in the
	 * profiled application
	 */
	public static final String METHODS_FILE = "Methods.txt";
	
	/**
	 * @return The name of the file with fileds profiles in the
	 * profiled application
	 */
	public static final String FIELDS_FILE = "Fields.txt";
	
	/**
	 * @return The name of the file with instructions profiles in the
	 * profiled application
	 */
	public static final String INSTRUCTIONS_FILE = "Instructions.txt";
}
