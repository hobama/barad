package barad.util;

import java.io.File;

/**
 * Container class that stores some important parameters
 * @author svetoslavganov
 */
public interface Properties {	
	/**
	 * Specifies if the debug mode
	 */
	public static final boolean DEBUG = true;
	
	/**
	 * Specifies if the logging is verbose or not
	 */
	public static final boolean VERBOSE = false;

	/**
	 * The System line separator
	 */
	public static final String NEW_LINE = System.getProperty("line.separator");

	/**
	 * The System separator
	 */
	public static final String SEPARATOR = File.separator;
	
	/**
	 * The name of the properies xml file
	 */
	public static final String PROPERTIES_FILE_NAME = System.getProperty("user.dir") + SEPARATOR + ".." + SEPARATOR + "SymbolicExecutionProperties.xml";
	
	/**
	 * The name of the properies xml file
	 */
	public static final String LOG4J_PROPERTIES_FILE_NAME = "./log4jProperties.xml";
	
	/**
	 * The regular expression used to filter method descriptors with one parameter
	 * and void return type
	 */
	public static final boolean  PRINT_INSTRUMENTED_CLASS = true;
	
	/**
	 * Specifies if the embedded constraint solver based on Choco library will be
	 * used or the one specified in SymbolicExecutionProperies.xml
	 */
	public static final boolean  USE_EMBEDDED_NUMERIC_CONSTRAINT_SOLVER = false;
}















