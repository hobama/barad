package examples;

import static barad.util.Properties.LOG4J_PROPERTIES_FILE_NAME;

import java.net.URL;

import org.apache.log4j.xml.DOMConfigurator;

import barad.util.Util;

@SuppressWarnings("all")
public class Examples {
	public static void main(String[] args) {
		URL log4jProperties = Thread.currentThread().getContextClassLoader().getResource(LOG4J_PROPERTIES_FILE_NAME);
		DOMConfigurator.configure(log4jProperties); 
		Util.loadFromSystemPropetiesXML();
		try {
			Class clazz = Class.forName("examples.MainWindowInternal"); 
		} catch (ClassNotFoundException cnfe) {
			System.out.println(cnfe);
		}
	}
}

