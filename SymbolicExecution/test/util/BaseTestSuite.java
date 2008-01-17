package util;

import static barad.util.Properties.LOG4J_PROPERTIES_FILE_NAME;

import java.net.URL;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * Base test class. Must be extended by all test classes.
 * Configures log4j
 * @author svetoslavganov
 *
 */
@SuppressWarnings("unused")
public class BaseTestSuite extends TestCase {
	private Logger log = Logger.getLogger(getClass());
	
	public BaseTestSuite() {
		//configure log4j
		URL log4jProperties = Thread.currentThread().getContextClassLoader().getResource(LOG4J_PROPERTIES_FILE_NAME);
		DOMConfigurator.configure(log4jProperties); 
	}
}
