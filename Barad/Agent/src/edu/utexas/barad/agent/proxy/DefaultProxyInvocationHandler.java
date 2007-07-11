package edu.utexas.barad.agent.proxy;

import edu.utexas.barad.agent.exceptions.AgentRuntimeException;
import edu.utexas.barad.common.ReflectionUtils;
import org.apache.log4j.Logger;

import java.lang.ref.WeakReference;
import java.lang.reflect.*;

/**
 * University of Texas at Austin
 * Barad Project, Jul 4, 2007
 */
public class DefaultProxyInvocationHandler implements IProxyInvocationHandler {
    private static final Logger logger = Logger.getLogger(DefaultProxyInvocationHandler.class);

    private IProxyFactory proxyFactory;
    private Class actualClass;
    private WeakReference<Object> actualInstance;

    public DefaultProxyInvocationHandler(Class actualClass, IProxyFactory proxyFactory) {
        if (actualClass == null) {
            throw new NullPointerException("actualClass");
        }
        if (proxyFactory == null) {
            throw new NullPointerException("proxyFactory");
        }
        if (!ReflectionUtils.isComplexType(actualClass)) {
            throw new AgentRuntimeException("actualClass must be a complex type.");
        }

        this.actualClass = actualClass;
        this.proxyFactory = proxyFactory;
    }

    public DefaultProxyInvocationHandler(Object actualInstance, IProxyFactory proxyFactory) {
        if (actualInstance == null) {
            throw new NullPointerException("actualInstance");
        }
        if (proxyFactory == null) {
            throw new NullPointerException("proxyFactory");
        }
        if (!ReflectionUtils.isComplexType(actualInstance.getClass())) {
            throw new AgentRuntimeException("actualInstance must be a complex type.");
        }

        this.actualInstance = new WeakReference<Object>(actualInstance);
        this.actualClass = actualInstance.getClass();
        this.proxyFactory = proxyFactory;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // Note: method names that begin "__fieldGet" or "__fieldSet" represent a field, not a method.
        // For example, __fieldGetx() means that we want to read the field named "x".
        boolean isFieldAccess = false; // Otherwise we assume a method invocation.
        String fieldName = null; // Will be null if this is a method invocation.
        boolean isFieldRead = true; // Otherwise it's a field write.  Only considered if isFieldAccess == true.

        // Determine if this is a method invocation or a field access.
        String methodName = method.getName();
        if (methodName.startsWith("__fieldGet")) {
            isFieldAccess = true;
            isFieldRead = true;
        } else if (methodName.startsWith("__fieldSet")) {
            isFieldAccess = true;
            isFieldRead = false;
        }
        if (isFieldAccess) {
            fieldName = methodName.substring("__fieldXXX".length());
        }

        // If a field access, check that it's valid.
        Class returnType = method.getReturnType();
        Class[] parameterTypes = method.getParameterTypes();
        if (isFieldAccess && isFieldRead && parameterTypes.length != 0) {
            logger.warn("Attempting to read a field while passing parameters, field=" + fieldName);
        }
        if (isFieldAccess && isFieldRead && returnType.getName().equals(void.class.getName())) {
            throw new AgentRuntimeException("Can't read from a field without having a non-void return type, field=" + fieldName);
        }
        if (isFieldAccess && !isFieldRead && parameterTypes.length != 1) {
            throw new AgentRuntimeException("Can't write to field without exactly one parameter to write, field=" + fieldName);
        }
        if (isFieldAccess && !isFieldRead && !returnType.getName().equals(void.class.getName())) {
            throw new AgentRuntimeException("Can't write to field without having a void return type, field=" + fieldName);
        }

        // Convert parameter types, replacing proxy types with actual types.
        Class[] actualParameterTypes = new Class[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; ++i) {
            if (Proxy.isProxyClass(parameterTypes[i])) {
                InvocationHandler invocationHandler = Proxy.getInvocationHandler(parameterTypes[i]);
                if (invocationHandler instanceof DefaultProxyInvocationHandler) {
                    DefaultProxyInvocationHandler defaultProxyInvocationHandler = (DefaultProxyInvocationHandler) invocationHandler;
                    actualParameterTypes[i] = defaultProxyInvocationHandler.getActualClass();
                } else {
                    actualParameterTypes[i] = parameterTypes[i];
                }
            } else {
                actualParameterTypes[i] = parameterTypes[i];
            }
        }

        // Convert arguments, replacing proxy objects with actual objects.
        Object[] actualArgs = args != null ? new Object[args.length] : null;
        if (args != null) {
            for (int i = 0; i < args.length; ++i) {
                if (Proxy.isProxyClass(args[i].getClass())) {
                    InvocationHandler invocationHandler = Proxy.getInvocationHandler(args[i]);
                    if (invocationHandler instanceof DefaultProxyInvocationHandler) {
                        DefaultProxyInvocationHandler defaultProxyInvocationHandler = (DefaultProxyInvocationHandler) invocationHandler;
                        actualArgs[i] = defaultProxyInvocationHandler.getActualInstance();
                    } else {
                        actualArgs[i] = args[i];
                    }
                } else {
                    actualArgs[i] = args[i];
                }
            }
        }

        Object actualResult = null;
        if (!isFieldAccess) {
            // Invoke the actual method.
            Method actualMethod = actualClass.getMethod(methodName, actualParameterTypes);
            try {
                actualResult = actualMethod.invoke(getActualInstance(), actualArgs);
            } catch (Exception e) {
                logger.error("An unexpected exception occurred during method invocation.", e);
                throw new AgentRuntimeException("An unexpected exception occurred during method invocation.", e);
            }
        } else {
            if (isFieldRead) {
                // Read from the field.
                Field field = actualClass.getField(fieldName);
                actualResult = field.get(getActualInstance());
            } else {
                // Write to the field.
                Field field = actualClass.getField(fieldName);
                if (actualArgs == null || actualArgs.length != 1) {
                    throw new AgentRuntimeException("Can't write to field without exactly one argument to write, field=" + fieldName);
                }
                field.set(getActualInstance(), actualArgs[0]);
            }
        }

        // Do we need to create a proxy for the return result?
        if (actualResult != null) {
            // Is the actual result an array?
            // If so we just need the base component type.
            if (returnType.isArray()) {
                if (ReflectionUtils.getDimensionCount(actualResult) > 1) {
                    throw new AgentRuntimeException("Only one-dimensional arrays are supported currently.");
                }
                returnType = ReflectionUtils.getBaseType(returnType);
                if (proxyFactory.isProxyInterface(returnType)) {
                    int length = Array.getLength(actualResult);
                    Object array = Array.newInstance(returnType, length);
                    for (int i = 0; i < length; ++i) {
                        try {
                            Array.set(array, i, proxyFactory.newProxy(Array.get(actualResult, i)));
                        } catch (IllegalArgumentException e) {
                            logger.error("Couldn't set proxy instance in return array.", e);
                            throw e;
                        }
                    }
                    return array;
                }
            } else if (proxyFactory.isProxyInterface(returnType)) {
                // Not an array type.
                return proxyFactory.newProxy(actualResult);
            }
        }

        return actualResult;
    }

    public final Class getActualClass() {
        return actualClass;
    }

    public Object getActualInstance() {
        if (actualInstance != null) {
            return actualInstance.get();
        }
        return null;
    }

    public IProxyFactory getProxyFactory() {
        return proxyFactory;
    }
}