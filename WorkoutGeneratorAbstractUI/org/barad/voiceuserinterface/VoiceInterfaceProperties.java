package org.barad.voiceuserinterface;

import java.io.File;

public interface VoiceInterfaceProperties {
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
	public static final String LOG4J_PROPERTIES_FILE_NAME = System.getProperty("user.dir") + SEPARATOR + "log4jProperties.xml";
	
	/**
	 * Specifies if the voice name used for speech generation
	 */
	public static final String VOICE_NAME = "kevin16";
}
