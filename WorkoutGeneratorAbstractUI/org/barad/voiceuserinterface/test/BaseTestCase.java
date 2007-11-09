package org.barad.voiceuserinterface.test;

import static org.barad.voiceuserinterface.VoiceInterfaceProperties.LOG4J_PROPERTIES_FILE_NAME;
import junit.framework.TestSuite;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

@SuppressWarnings("unused")
public class BaseTestCase extends TestSuite {
	private Logger log = Logger.getLogger(getClass());
	
	public BaseTestCase() {
		//configure log4j
		DOMConfigurator.configure(LOG4J_PROPERTIES_FILE_NAME);
	}
}