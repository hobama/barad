package edu.utexas.barad.agent.proxy;

import edu.utexas.barad.agent.exceptions.AgentRuntimeException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * University of Texas at Austin
 * Barad Project, Jul 4, 2007
 */
public abstract class AbstractProxyFactory implements IProxyFactory {
    public Object newProxy(Class clazz) {
        try {
            InvocationHandler invocationHandler = new DefaultProxyInvocationHandler(clazz, this);
            Class[] interfaces = new Class[]{getProxyClass(clazz)};
            return Proxy.newProxyInstance(getClass().getClassLoader(), interfaces, invocationHandler);
        } catch (Exception e) {
            throw new AgentRuntimeException("Couldn't create new proxy, class=" + clazz, e);
        }
    }

    public Object newProxy(Class clazz, Class[] constructorParameterTypes, Object[] constructorArguments) {
        if (clazz == null) {
            throw new NullPointerException("clazz");
        }
        try {
            Constructor constructor = clazz.getConstructor(constructorParameterTypes);
            Object actualInstance = constructor.newInstance(constructorArguments);
            InvocationHandler invocationHandler = new DefaultProxyInvocationHandler(actualInstance, this);
            Class[] interfaces = new Class[]{getProxyClass(clazz)};
            return Proxy.newProxyInstance(getClass().getClassLoader(), interfaces, invocationHandler);
        } catch (Exception e) {
            throw new AgentRuntimeException("Couldn't create new proxy, class=" + clazz, e);
        }
    }

    public Object newProxy(Object actualInstance) {
        if (actualInstance == null) {
            throw new NullPointerException("actualInstance");
        }
        Class[] interfaces = new Class[]{getProxyClass(actualInstance.getClass())};
        InvocationHandler invocationHandler = new DefaultProxyInvocationHandler(actualInstance, this);
        return Proxy.newProxyInstance(getClass().getClassLoader(), interfaces, invocationHandler);
    }

    public abstract Class getProxyClass(Class actualClass);

    public abstract Class getActualClass(Class proxyClass);
    
    public boolean isProxyClass(Class clazz) {
        Map<Class, Class> classNameToProxyClassMap = getActualClassToProxyClassMapInternal();
        return classNameToProxyClassMap.containsValue(clazz);
    }

    protected abstract Map<Class, Class> getActualClassToProxyClassMap();

    private Map<Class, Class> getActualClassToProxyClassMapInternal() {
        Map<Class, Class> actualClassToProxyClassMap = getActualClassToProxyClassMap();
        if (actualClassToProxyClassMap == null) {
            throw new NullPointerException("getActualClassToProxyClassMap() returned null");
        }
        return actualClassToProxyClassMap;
    }
}