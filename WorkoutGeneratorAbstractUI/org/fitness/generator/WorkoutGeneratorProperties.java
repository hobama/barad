package org.fitness.generator;

import java.io.File;

public interface WorkoutGeneratorProperties {
	/**
	 * Specifies if the debug mode
	 */
	public static final boolean DEBUG = false;
	
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
	public static final String LOG4J_PROPERTIES_FILE_NAME = System.getProperty("user.dir") + SEPARATOR + "log4jProperties.xml";
}
