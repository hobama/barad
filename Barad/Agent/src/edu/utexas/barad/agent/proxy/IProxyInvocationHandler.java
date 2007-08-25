package edu.utexas.barad.agent.proxy;

import java.lang.reflect.InvocationHandler;

/**
 * University of Texas at Austin
 * Barad Project, Jul 7, 2007
 */
public interface IProxyInvocationHandler extends InvocationHandler {
    public Class getActualClass();

    public Object getActualInstance();

    public void setActualInstance(Object object);

    public IProxyFactory getProxyFactory();
}