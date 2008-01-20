package edu.utexas.barad.agent.swt;

import edu.utexas.barad.agent.proxy.IProxyInvocationHandler;
import edu.utexas.barad.agent.swt.proxy.widgets.DisplayProxy;
import edu.utexas.barad.agent.swt.proxy.widgets.WidgetProxy;
import edu.utexas.barad.agent.swt.widgets.MessageBoxHelper;
import edu.utexas.barad.common.swt.WidgetInfo;
import edu.utexas.barad.common.swt.WidgetValues;
import org.apache.log4j.Logger;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * University of Texas at Austin
 * Barad Project, Jul 11, 2007
 */
public class WidgetValueBuilder {
    private static final Logger logger = Logger.getLogger(WidgetValueBuilder.class);

    public static Map<String, String> buildPropertyValues(final Object object) {
        return buildPropertyValues(object, (List<ReadMethodInfo>) null);
    }

    public static Map<String, String> buildPropertyValues(final Object object, List<ReadMethodInfo> includedReadMethods) {
        return buildPropertyValues(object, null, includedReadMethods);
    }

    public static Map<String, String> buildPropertyValues(final Object widget, DisplayProxy display) {
        return buildPropertyValues(widget, display, null);
    }

    public static Map<String, String> buildPropertyValues(final Object widget, DisplayProxy display, List<ReadMethodInfo> includedReadMethods) {
        Map<String, String> propertyValues = new HashMap<String, String>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(widget.getClass(), Introspector.IGNORE_ALL_BEANINFO);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                final Method readMethod = propertyDescriptor.getReadMethod();
                if (readMethod != null) {
                    String methodName = readMethod.getName();
                    if (includedReadMethods != null && findReadMethodInfo(readMethod, includedReadMethods) == null) {
                        continue;
                    }

                    final String[] result = new String[1];
                    Runnable runnable = new Runnable() {
                        public void run() {
                            try {
                                Object value = readMethod.invoke(widget);
                                result[0] = value != null ? value.toString() : null;
                            } catch (IllegalAccessException e) {
                                logger.error("Illegal access.", e);
                            } catch (InvocationTargetException e) {
                                logger.error("Exception during method invocation.", e);
                            }
                        }
                    };
                    if (display != null) {
                        display.syncExec(runnable);
                    } else {
                        runnable.run();
                    }

                    String valueString = result[0];
                    propertyValues.put(methodName, valueString);
                }
            }
        } catch (IntrospectionException e) {
            logger.error("Couldn't build property values, widget=" + widget, e);
        }

        return propertyValues;
    }

    public static Map<String, String> buildFieldValues(final Object object) {
        return buildFieldValues(object, null);
    }

    public static Map<String, String> buildFieldValues(final Object widget, DisplayProxy display) {
        Map<String, String> fieldValues = new HashMap<String, String>();
        Class clazz = widget.getClass();
        do {
            Field[] fields = clazz.getDeclaredFields();
            for (final Field field : fields) {
                String fieldName = field.getName();
                field.setAccessible(true);

                final String[] result = new String[1];
                Runnable runnable = new Runnable() {
                    public void run() {
                        try {
                            Object value = field.get(widget);
                            result[0] = value != null ? value.toString() : null;
                        } catch (IllegalAccessException e) {
                            logger.error("Illegal access.", e);
                        }
                    }
                };
                if (display != null) {
                    display.syncExec(runnable);
                } else {
                    runnable.run();
                }

                String valueString = result[0];
                fieldValues.put(fieldName, valueString);
            }
            clazz = clazz.getSuperclass();
        } while (clazz != null);
        return fieldValues;
    }

    public static WidgetValues getWidgetValues(WidgetInfo widgetInfo, WidgetHierarchy widgetHierarchy) {
        if (widgetInfo == null) {
            throw new NullPointerException("widgetInfo");
        }
        if (widgetHierarchy == null) {
            throw new NullPointerException("widgetHierarchy");
        }

        if (!WidgetHierarchy.isMessageBoxHelper(widgetInfo)) {
            Object widget = widgetHierarchy.getWidgetProxy(widgetInfo.getGuid());
            DisplayProxy display;
            if (widget instanceof DisplayProxy) {
                display = (DisplayProxy) widget;
            } else {
                WidgetProxy widgetProxy = (WidgetProxy) widget;
                display = widgetProxy.getDisplay();
            }
            IProxyInvocationHandler handler = (IProxyInvocationHandler) Proxy.getInvocationHandler(widget);
            Object actualInstance = handler.getActualInstance();

            WidgetValues widgetValues = new WidgetValues();
            widgetValues.setPropertyValues(WidgetValueBuilder.buildPropertyValues(actualInstance, display));
            widgetValues.setFieldValues(WidgetValueBuilder.buildFieldValues(actualInstance, display));
            return widgetValues;
        } else {
            MessageBoxHelper messageBoxHelper = widgetHierarchy.getMessageBoxHelper();

            WidgetValues widgetValues = new WidgetValues();
            widgetValues.setPropertyValues(WidgetValueBuilder.buildPropertyValues(messageBoxHelper));
            widgetValues.setFieldValues(WidgetValueBuilder.buildFieldValues(messageBoxHelper));
            return widgetValues;
        }
    }

    private WidgetValueBuilder() {
        // Private constructor.
    }

    private static ReadMethodInfo findReadMethodInfo(Method method, List<ReadMethodInfo> list) {
        for (ReadMethodInfo readMethodInfo : list) {
            if (readMethodInfo.getMethodName().equals(method.getName()) && readMethodInfo.getReturnType().equals(method.getReturnType())) {
                return readMethodInfo;
            }
        }
        return null;
    }

    public static class ReadMethodInfo {
        private String methodName;
        private Class returnType;

        public ReadMethodInfo(String methodName, Class returnType) {
            if (methodName == null) {
                throw new NullPointerException("methodName");
            }
            if (returnType == null) {
                throw new NullPointerException("read method must have a non-null return type");
            }
            this.methodName = methodName;
            this.returnType = returnType;
        }

        public String getMethodName() {
            return methodName;
        }

        public Class getReturnType() {
            return returnType;
        }

        @Override
        public int hashCode() {
            int hashCode = 0;
            hashCode += methodName.hashCode();
            if (returnType != null) {
                hashCode += returnType.hashCode();
            }
            return hashCode;
        }

        @Override
        public boolean equals(Object object) {
            if (object instanceof ReadMethodInfo) {
                ReadMethodInfo another = (ReadMethodInfo) object;
                if (!another.methodName.equals(this.methodName)) {
                    return false;
                }
                if (another.returnType == null && this.returnType != null) {
                    return false;
                }
                if (another.returnType != null && this.returnType == null) {
                    return false;
                }
                if (another.returnType != null && this.returnType != null && !another.returnType.equals(this.returnType)) {
                    return false;
                }
                return true;
            }
            return false;
        }

        @Override
        public String toString() {
            return "ReadMethodInfo{" +
                    "methodName='" + methodName + '\'' +
                    ", returnType=" + returnType +
                    '}';
        }
    }
}