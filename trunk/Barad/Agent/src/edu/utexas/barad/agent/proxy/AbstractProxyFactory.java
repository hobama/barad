package edu.utexas.barad.agent.proxy;

import edu.utexas.barad.agent.exceptions.AgentRuntimeException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.Set;

/**
 * University of Texas at Austin
 * Barad Project, Jul 4, 2007
 */
public abstract class AbstractProxyFactory implements IProxyFactory {
    public Object newProxy(Class clazz) {
        try {
            InvocationHandler invocationHandler = new DefaultProxyInvocationHandler(clazz, this);
            Class[] interfaces = new Class[]{getProxyInterface(clazz)};
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
            Class[] interfaces = new Class[]{getProxyInterface(clazz)};
            return Proxy.newProxyInstance(getClass().getClassLoader(), interfaces, invocationHandler);
        } catch (Exception e) {
            throw new AgentRuntimeException("Couldn't create new proxy, class=" + clazz, e);
        }
    }

    public Object newProxy(Object actualInstance) {
        if (actualInstance == null) {
            throw new NullPointerException("actualInstance");
        }
        Class[] interfaces = new Class[]{getProxyInterface(actualInstance.getClass())};
        InvocationHandler invocationHandler = new DefaultProxyInvocationHandler(actualInstance, this);
        return Proxy.newProxyInstance(getClass().getClassLoader(), interfaces, invocationHandler);
    }

    public Class getProxyInterface(Class actualClass) {
        Map<Class, Class> actualClassToProxyClassMap = getActualClassToProxyClassMapInternal();

        // First check if the actual class is one of the SWT classes.
        Class proxyClass = actualClassToProxyClassMap.get(actualClass);

        if (proxyClass == null) {
            // Is the actual class a subclass of one of the SWT classes?
            Set<Map.Entry<Class, Class>> entrySet = actualClassToProxyClassMap.entrySet();
            for (Map.Entry<Class, Class> entry : entrySet) {
                proxyClass = entry.getValue();
                if (proxyClass != null && proxyClass.isAssignableFrom(actualClass)) {
                    break;
                }
            }

            if (proxyClass == null) {
                // We couldn't find a proxy class for the actual class.
                throw new AgentRuntimeException("No proxy class available, actualClass=" + actualClass);
            }
        }
        return proxyClass;
    }

    public boolean isProxyInterface(Class clazz) {
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