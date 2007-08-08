package edu.utexas.barad.agent.proxy;

import java.util.HashMap;
import java.util.Map;

/**
 * University of Texas at Austin
 * Barad Project, Jul 4, 2007
 */
public class ExampleProxyFactory extends AbstractProxyFactory {
    private static final Map<Class, Class> actualClassToProxyClassMap = new HashMap<Class, Class>();

    static {
        actualClassToProxyClassMap.put(Example.class, ExampleProxy.class);
    }

    protected Map<Class, Class> getActualClassToProxyClassMap() {
        return actualClassToProxyClassMap;
    }

    public Class getProxyClass(Class actualClass) {
        return null;
    }

    public Class getActualClass(Class proxyClass) {
        return null;
    }
}