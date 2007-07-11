package edu.utexas.barad.agent.swt;

import edu.utexas.barad.agent.swt.proxy.widgets.DisplayProxy;
import org.apache.log4j.Logger;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * University of Texas at Austin
 * Barad Project, Jul 11, 2007
 */
public class WidgetValueBuilder {
    private static final Logger logger = Logger.getLogger(WidgetValueBuilder.class);

    public static Map<String, String> buildPropertyValues(final Object widget, DisplayProxy display) {
        Map<String, String> propertyValues = new HashMap<String, String>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(widget.getClass(), Introspector.IGNORE_ALL_BEANINFO);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                final Method readMethod = propertyDescriptor.getReadMethod();
                if (readMethod != null) {
                    String methodName = readMethod.getName();

                    final String[] result = new String[1];
                    display.syncExec(new Runnable() {
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
                    });

                    String valueString = result[0];
                    propertyValues.put(methodName, valueString);
                }
            }
        } catch (IntrospectionException e) {
            logger.error("Couldn't build property values, widget=" + widget, e);
        }

        return propertyValues;
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
                display.syncExec(new Runnable() {
                    public void run() {
                        try {
                            Object value = field.get(widget);
                            result[0] = value != null ? value.toString() : null;
                        } catch (IllegalAccessException e) {
                            logger.error("Illegal access.", e);
                        }
                    }
                });

                String valueString = result[0];
                fieldValues.put(fieldName, valueString);
            }
            clazz = clazz.getSuperclass();
        } while (clazz != null);
        return fieldValues;
    }

    private WidgetValueBuilder() {
        // Private constructor.
    }
}