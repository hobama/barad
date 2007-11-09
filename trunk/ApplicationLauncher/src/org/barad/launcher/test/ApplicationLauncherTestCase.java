package org.barad.launcher.test;

import static org.barad.launcher.LauncherProperties.LOG4J_PROPERTIES_FILE_NAME;
import junit.framework.TestSuite;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

@SuppressWarnings("unused")
public class ApplicationLauncherTestCase extends TestSuite {
	private Logger log = Logger.getLogger(getClass());
	
	public ApplicationLauncherTestCase() {
		//configure log4j
		DOMConfigurator.configure(LOG4J_PROPERTIES_FILE_NAME);
	}
}
