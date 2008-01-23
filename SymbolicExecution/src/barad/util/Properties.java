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
	public static final String PROPERTIES_FILE_NAME = "SymbolicExecutionProperties.xml";
	
	/**
	 * The name of the properies xml file 
	 */
	public static final String LOG4J_PROPERTIES_FILE_NAME = "log4jProperties.xml";
	
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
	
	/**
	 * @return The file that contains the mapping from opcodes to mnemonic representation
	 */
	public static final String OPCODE_TO_MNEMONIC_MAP_FILE = "OPCODES.txt";
	
	/**
	 * @return The file that contains description of the application under test
	 */
	public static final String TESTED_APPLICATION_DESCRIPTOR_FILE = "TestedApplicationDescriptor.xml";
	
	/**
	 * @return The teamp folder for storing copied of the instrumented classes
	 */
	public static final String TEMP_FOLDER = "Blah";
}
