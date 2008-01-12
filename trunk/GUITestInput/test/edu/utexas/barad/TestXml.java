package edu.utexas.barad;

import edu.utexas.barad.test.input.ObjectFactory;
import edu.utexas.barad.test.input.Testcase;
import edu.utexas.barad.test.input.Testsuite;
import edu.utexas.barad.test.input.Widgetinput;
import junit.framework.TestCase;

import javax.xml.bind.Unmarshaller;
import java.io.FileReader;
import java.util.List;

/**
 * @author Chip Killmar
 */
public class TestXml extends TestCase {
    @SuppressWarnings("unchecked")
    public void testXml() throws Exception {
        ObjectFactory objectFactory = new ObjectFactory();
        Unmarshaller unmarshaller = objectFactory.createUnmarshaller();

        FileReader fileReader = new FileReader("GUITestInput-example.xml");
        Testsuite testsuite = (Testsuite) unmarshaller.unmarshal(fileReader);

        List<Testcase> testcases = testsuite.getTestcase();
        assertEquals(2, testcases.size());

        Testcase testcase = testcases.get(0);
        List<Widgetinput> widgetinputs = testcase.getWidgetinput();
        assertEquals(2, widgetinputs.size());

        Widgetinput widgetinput = widgetinputs.get(0);
        assertEquals("widgetid1", widgetinput.getWidgetid());
        assertEquals("parentclass1", widgetinput.getParentclass());
        assertEquals("parentid1", widgetinput.getParentid());
        assertEquals("widgetclass1", widgetinput.getWidgetclass());
        assertEquals("widgetproperty1", widgetinput.getWidgetproperty());
    }
}