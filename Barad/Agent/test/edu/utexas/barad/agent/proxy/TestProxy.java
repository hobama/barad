package edu.utexas.barad.agent.proxy;

import edu.utexas.barad.agent.exceptions.AgentRuntimeException;
import junit.framework.TestCase;

/**
 * University of Texas at Austin
 * Barad Project, Jul 4, 2007
 *
 *
 */
public class TestProxy extends TestCase {
    public void testProxy1() throws Exception {
        ExampleProxyFactory factory = new ExampleProxyFactory();
        Example actual = new Example();
        Object proxy = factory.newProxy(actual);
        assertNotNull(proxy);
        assertTrue(proxy instanceof ExampleProxy);
        ExampleProxy exampleProxy = (ExampleProxy) proxy;
        ExampleProxy defaultProxy = exampleProxy.getDefault();
        assertNotNull(defaultProxy);

        exampleProxy.setData("Test");
        assertEquals("Test", exampleProxy.getData());
        assertEquals("Test", actual.getData());

        actual.setData("Test2");
        assertEquals("Test2", exampleProxy.getData());
    }

    public void testProxy2() throws Exception {
        ExampleProxyFactory factory = new ExampleProxyFactory();
        Example actual = new Example();
        Example another = new Example();
        actual.setTest(another);

        ExampleProxy exampleProxy = (ExampleProxy) factory.newProxy(actual);
        ExampleProxy anotherProxy = (ExampleProxy) factory.newProxy(another);
        ExampleProxy temp = exampleProxy.getTest();
        assertEquals(anotherProxy, temp);
        assertNotSame(anotherProxy, another);
        assertNotSame(another, anotherProxy);
        assertEquals(anotherProxy.hashCode(), another.hashCode());
        assertEquals(another.toString(), anotherProxy.toString());
    }

    public void testProxy3() throws Exception {
        ExampleProxyFactory factory = new ExampleProxyFactory();
        ExampleProxy exampleProxy = (ExampleProxy) factory.newProxy(Example.class, new Class[]{String.class}, new Object[]{"Test"});
        assertNotNull(exampleProxy);
        assertEquals("Test", exampleProxy.getData());

        exampleProxy = (ExampleProxy) factory.newProxy(Example.class, null, null);
        assertNotNull(exampleProxy);
        assertNull(exampleProxy.getData());
    }

    public void testProxy4() throws Exception {
        ExampleProxyFactory factory = new ExampleProxyFactory();
        AgentRuntimeException e = null;
        try {
            factory.newProxy(String.class);
        } catch (AgentRuntimeException e2) {
            e = e2;
        }
        assertNotNull(e);

        e = null;
        try {
            factory.newProxy("Test");
        } catch (AgentRuntimeException e2) {
            e = e2;
        }
        assertNotNull(e);
    }

    public void testProxy5() throws Exception {
        ExampleProxyFactory factory = new ExampleProxyFactory();
        Example actual = new Example();
        Example another = new Example();
        actual.setTest(another);

        ExampleProxy exampleProxy = (ExampleProxy) factory.newProxy(actual);
        assertEquals(actual.x, exampleProxy.__fieldGetx());
        assertEquals(42, exampleProxy.__fieldGetx());
        exampleProxy.__fieldSetx(43);
        assertEquals(actual.x, exampleProxy.__fieldGetx());
        assertEquals(43, exampleProxy.__fieldGetx());

        assertEquals(Example.y, exampleProxy.__fieldGety());
        assertEquals("Test", exampleProxy.__fieldGety());
        exampleProxy.__fieldSety("Test2");
        assertEquals(Example.y, exampleProxy.__fieldGety());
        assertEquals("Test2", exampleProxy.__fieldGety());

        ExampleProxy[] array = exampleProxy.getArray();
        assertEquals(1, array.length);
        assertEquals(array[0].hashCode(), another.hashCode());
        assertEquals(array[0].toString(), another.toString());

        exampleProxy = null;
        exampleProxy = (ExampleProxy) factory.newProxy(ExampleSubClass.class);
        assertNotNull(exampleProxy);
    }
}