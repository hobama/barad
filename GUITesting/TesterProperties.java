import java.io.File;

/**
 * Inretface that stores some important parameters
 * TODO: Maybe I should implement this as xml file and Properties object
 * @author svetoslavganov
 */
public interface TesterProperties {
	/**
	 * The new line character for the current system
	 */
	public static final String SEPARATOR_CHAR = File.separator;
	
	/**
	 * The name of the jar file with the instrumentation classes
	 */
	public static final String LOG_PATH = System.getProperty("user.dir");

	/**
	 * The name of the jar file with the instrumentation classes
	 */
	public static final String LOG_FILE = "GUITesterLog.txt";
	
	/**
	 * The name of the jar file with the instrumentation classes
	 */
	public static final String BOOTSTRAP_PATH = System.getProperty("user.dir");
	
	/**
	 * The name of the widgets file
	 */
	public static final String SWT_WIDGETS_FILE = BOOTSTRAP_PATH + SEPARATOR_CHAR + "SWTWidgetClassNames.txt";
	
	/**
	 * The name of the event generators file
	 */
	public static final String EVENT_GENERATORS_FILE = BOOTSTRAP_PATH + SEPARATOR_CHAR + "EventGeneratorsClassNames.txt";
	
	/**
	 * If the system is in dubug mode
	 */
	public static final boolean DEBUG = false;

	/**
	 * If the logging is verbose
	 */
	public static final boolean VERBOSE = false;
	
	/**
	 * The name of the file with instructions profiles in the
	 * profiled application
	 */
	public static final String INSTRUCTIONS_FILE = "Instructions.txt";
	
	/**
	 * The new line character for the current system
	 */
	public static final String NEW_LINE = System.getProperty("line.separator");
	
	/**
	 * The regular expression used to filter method descriptors with one parameter
	 * and void return type
	 */
	public static final String  LISTENER_REGISTRATION_DESCRIPRTOR = "[(][^;]+[;][)][V]";
	
	/**
	 * The regular expression used to filter method descriptors with one parameter
	 * and void return type
	 */
	public static final boolean  PRINT_INSTRUMENTED_CLASS = false;
}