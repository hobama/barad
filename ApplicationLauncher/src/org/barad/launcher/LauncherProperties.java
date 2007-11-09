package org.barad.launcher;

import java.io.File;

public interface LauncherProperties {
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
	 * The regular expression used to filter method descriptors with one parameter
	 * and void return type
	 */
	public static final boolean  PRINT_INSTRUMENTED_CLASS = true;
	
	/**
	 * The name of the properies xml file
	 */
	public static final String LOG4J_PROPERTIES_FILE_NAME = System.getProperty("user.dir") + SEPARATOR + "log4jProperties.xml";
	
	/**
	 * The path to the xml file defining the mapping between the abstract and concrete interaces
	 */
	public static final String INTERFACE_MAPPING_PATH = System.getProperty("user.dir") + SEPARATOR;
}
