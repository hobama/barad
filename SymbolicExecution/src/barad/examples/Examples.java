package barad.examples;

import static barad.util.Properties.LOG4J_PROPERTIES_FILE_NAME;

import org.apache.log4j.xml.DOMConfigurator;

import barad.util.Util;

@SuppressWarnings("all")
public class Examples {
	public static void main(String[] args) {
		DOMConfigurator.configure(LOG4J_PROPERTIES_FILE_NAME); 
		Util.loadFromSystemPropetiesXML();
		try {
			Class clazz = Class.forName("barad.examples.MainWindow"); 
		} catch (ClassNotFoundException cnfe) {
			System.out.println(cnfe);
		}
	}
}

