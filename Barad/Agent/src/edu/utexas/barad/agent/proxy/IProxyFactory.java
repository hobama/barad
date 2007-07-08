package edu.utexas.barad.agent.proxy;

/**
 * University of Texas at Austin
 * Barad Project, Jul 7, 2007
 */
public interface IProxyFactory {
    public Object newProxy(Class clazz);

    public Object newProxy(Class clazz, Class[] constructorParameterTypes, Object[] constructorArguments);

    public Object newProxy(Object actualInstance);

    public Class[] getProxyInterfaces(Class actualClass);

    public boolean isProxyInterface(Class clazz);
}