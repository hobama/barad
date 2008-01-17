package barad.symboliclibrary.test.xml.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.log4j.Logger;

import barad.symboliclibrary.test.Descriptor;
import barad.symboliclibrary.test.TestCase;
import barad.symboliclibrary.test.TestInput;
import barad.symboliclibrary.test.xml.XMLWriter;
import barad.util.Util;
import edu.utexas.barad.test.input.ObjectFactory;

/**
 * Class that writes a test suite to XML file
 * @author svetoslavganov
 *
 */
public class BaradXMLWriter implements XMLWriter {
	private Logger log;
	private JAXBContext jaxbContext;
	private Marshaller marshaller;
	private String destinationFolder;
	
	public BaradXMLWriter() {
		log = Logger.getLogger(getClass());
		destinationFolder = Util.getProperties().getProperty("destination.folder");
		//check if is a directory
		File destFolder = new File(destinationFolder);
		if (!destFolder.exists() || !destFolder.isDirectory()) {
			log.info("Destination test folder: " + destFolder + " does not exist and will be created");
			throw new IllegalArgumentException();
		}
		//delete all xml files
		File[] files = destFolder.listFiles();
		for (File file: files) {
			if (file.getName().endsWith(".xml") && file.canWrite()) {
				file.delete();
			}
		}
		//initialize JAXB
		try {
			jaxbContext = JAXBContext.newInstance( "edu.utexas.barad.test.input" );
			marshaller = jaxbContext.createMarshaller();
		} catch (JAXBException je) {
			log.error("Error during JAXB initialization", je);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see barad.symboliclibrary.test.xml.XMLWriter#writeToXML(java.util.Set, java.lang.String)
	 */
	public void writeToXML(Set<TestCase> testCases, String executedMethod, Descriptor triggeringEvent) {
		edu.utexas.barad.test.input.TestSuite testSuite = new edu.utexas.barad.test.input.TestSuite();
		//add the first test case which contains information for the event and the source
		edu.utexas.barad.test.input.TestCase header = new edu.utexas.barad.test.input.TestCase();
		TestInput testInput = new TestInput();
		testInput.setDescriptor(triggeringEvent);
		testInput.setValue("*EVENT SOURCE*");
		header.getWidgetinput().add(populateWidgetInput(testInput));
		testSuite.getTestcase().add(header);
		//add the test cases
		for (TestCase tc: testCases) {
			//add test that have test inputs
			if (tc.getTestInputs().size() > 0) {
				edu.utexas.barad.test.input.TestCase testCase = new edu.utexas.barad.test.input.TestCase();
				for (TestInput ti: tc.getTestInputs()) {
					testCase.getWidgetinput().add(populateWidgetInput(ti));
				}
				testSuite.getTestcase().add(testCase);
			}
		}
		//write test files only if tests are present
		if (testSuite.getTestcase().size() > 1) {
			writeToFile(executedMethod, testSuite);
		}
	}
	
	
	/**
	 * Populate a Test JAXB object with test data
	 * @param testInput The test input
	 * @return Test JAXB object
	 */
	private edu.utexas.barad.test.input.WidgetInput populateWidgetInput(TestInput testInput) {
		edu.utexas.barad.test.input.WidgetInput widgetInput = new edu.utexas.barad.test.input.WidgetInput();
		widgetInput.setParentId(testInput.getDescriptor().getParentId());
		widgetInput.setParentClass(testInput.getDescriptor().getParentClass());
		widgetInput.setParentIndex(Short.parseShort(testInput.getDescriptor().getParentIndex()));
		widgetInput.setWidgetId(testInput.getDescriptor().getWidgetId());
		widgetInput.setWidgetClass(testInput.getDescriptor().getWidgetClass());
		widgetInput.setWidgetIndex(Short.parseShort(testInput.getDescriptor().getWidgetIndex()));
		widgetInput.setWidgetProperty(testInput.getDescriptor().getWidgetProperty());
		widgetInput.setPropertyValue(testInput.getValue());
		return widgetInput;
	}
	
	/**
	 * Write a JAXB object to an XML file
	 * @param executedMethod The method for which the test suite was generated
	 * @param testSuite The test suite object to be written to the XML file
	 */
	private void writeToFile(String executedMethod, edu.utexas.barad.test.input.TestSuite testSuite) {
		File xmlFile = null;
		try {
			//create the file
			xmlFile = createXMLFile(executedMethod);
			BufferedWriter printWriter = new BufferedWriter(new PrintWriter(xmlFile));
		    // create an element for marshalling
		    JAXBElement<edu.utexas.barad.test.input.TestSuite> jaxbElement = new ObjectFactory().createTestsuite(testSuite);
		    //write to file
			marshaller.marshal(jaxbElement, printWriter);
		} catch (IOException ioe) {
			log.error("Error during creation of xml file " + xmlFile.getName(), ioe);
		} catch (JAXBException je) {
			log.error("Error during marshalling to xml file " + xmlFile.getName(), je);
		}
	}
	
	/**
	 * Create an XML file with a name derived by the concatenation of the destination 
	 * folder property, the executed method name (i.e. the one for which the test
	 * suite was generated), and index for avoiding duplicate file names.
	 * @param executedMethod The method for which the test suite was generated
	 * @return New file instance
	 * @throws IOException If an error during file creation occurs
	 */
	private File createXMLFile(String executedMethod) throws IOException{
		int index = 0;
		File destFile = new File(destinationFolder, executedMethod + index + ".xml");
		while (!destFile.createNewFile() || index == 50) {
			destFile = new File(destinationFolder, executedMethod + index++ + ".xml");
		}
		return destFile;
	}
}
