package barad.symboliclibrary.test.xml;

import org.apache.log4j.Logger;

import barad.util.Util;

/**
 * Class that is a factory for XMLWriter solvers. This
 * implementation allows modular architecture. The user
 * should just replace the name of the constraint solver
 * implementation in the properties file
 * @author svetoslavganov
 */
public class XMLWriterFactory {
	private Logger log;
	private static XMLWriterFactory instance;
	private String xmlWriterClassName;
	private XMLWriter xmlWriter;
	
	private XMLWriterFactory() {
		log = Logger.getLogger(this.getClass());
		xmlWriterClassName = Util.getProperties().getProperty("xml.writer");
	}
	
	/**
	 * Gets the instance of XMLWriterFactory if one
	 * does not exist it is created
	 * @return The XMLWriterFactory
	 */
	public static XMLWriterFactory getInstance() {
		if (instance == null) {
			instance = new XMLWriterFactory(); 
		}
		return instance;
	}
	
	/**
	 * Returns the xml writer instance. If such one
	 * does not exist it is created. The writer is a singleton 
	 * @return An xml writer
	 */
	public XMLWriter getXMLWriterInstance() {
		if (xmlWriter == null) {
			xmlWriter = (XMLWriter)getSolverInstance(xmlWriterClassName);
		}
		return xmlWriter;
	}
	
	/**
	 * Creates a new xml writer
	 * @return New xml writer instance
	 */
	private Object getSolverInstance(String solverClassName) {
		Class clazz = null;
		try {
			clazz = Class.forName(solverClassName);
		} catch(ClassNotFoundException cnfe) {
			log.error("Constraint solver class does not exist or is not specified in the properties file " + xmlWriterClassName, cnfe);
		}
		return getInstance(clazz);
	}
	
	/**
	 * Instantiates on object of a given class
	 * @param clazz The class
	 * @return Instance of the class, null if any exception 
	 * is thrown during the instantiation
	 */
	private Object getInstance(Class clazz) {
		Object instance = null;
		try {
			instance = clazz.newInstance();
		} catch(InstantiationException ie) {
			log.error(ie);
		} catch (IllegalAccessException iae) {
			log.error(iae);
		}
		return instance;
	}
}
