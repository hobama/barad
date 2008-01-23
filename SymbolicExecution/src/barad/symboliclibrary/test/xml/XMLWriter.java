package barad.symboliclibrary.test.xml;

import java.util.Set;

import barad.symboliclibrary.test.Descriptor;
import barad.symboliclibrary.test.TestCase;

/**
 * Interface that defines the cotract between Barad and
 * the component responsible for writing the test suite 
 * to XML files
 * @author svetoslavganov
 *
 */
public interface XMLWriter {
	/**
	 * Writes the test suite generated for a mehtod to an 
	 * XML file.
	 * @param testCases The test suite
	 * @param executedMethod The method during the symbolic execution 
	 * of the test suite was generated
	 * @param triggeringEvent Description of the source and type of 
	 * the event that is processed by the event hanlder for which
	 * we generate test suite
	 */
	public void writeToXML(Set<TestCase> testCases, String executedMethod, Descriptor triggeringEvent);
}
