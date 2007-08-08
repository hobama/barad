package edu.utexas.barad.common;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * University of Texas at Austin
 * Barad Project, Jul 6, 2007
 */
public class ReflectionUtils {
    private static final List PRIMITIVE_WRAPPER_CLASSES = Arrays.asList(Void.class,
            Boolean.class,
            Byte.class,
            Character.class,
            Double.class,
            Float.class,
            Integer.class,
            Number.class,
            Short.class,
            String.class);

    public static Class getBaseType(Class realValueClass) {
        if (realValueClass == null) {
            return null;
        }
        Class baseType = realValueClass;
        while (baseType.isArray()) {
            baseType = baseType.getComponentType();
        }
        return baseType;
    }

    public static boolean isComplexType(Class clazz) {
        if (clazz == null) {
            return false;
        }
        clazz = getBaseType(clazz);
        return !clazz.isPrimitive() && !PRIMITIVE_WRAPPER_CLASSES.contains(clazz);
    }

    public static boolean isPrimitiveType(Class clazz) {
        return clazz != null && !isComplexType(clazz);
    }

    public static int[] getArrayDimensions(Object array) {
        int[] dimensions = null;
        int dimensionCount = getDimensionCount(array);
        if (dimensionCount > -1) {
            dimensions = new int[dimensionCount];
            int index = 0;
            while (array != null && array.getClass().isArray()) {
                dimensions[index] = Array.getLength(array);
                if (dimensions[index] > 0) {
                    array = Array.get(array, 0);
                } else {
                    array = null;
                }
                index++;
            }
        }
        return dimensions;
    }

    public static int getDimensionCount(Object array) {
        if (array == null) {
            return -1;
        }
        if (!array.getClass().isArray()) {
            return -1;
        }
        String className = array.getClass().getName();
        char[] classNameChars = className.toCharArray();
        int dimensionCount = 0;
        for (char classNameChar : classNameChars) {
            if (classNameChar == '[') {
                dimensionCount++;
            }
        }
        return dimensionCount;
    }

    public static Object getField(Object object, String fieldName) {
        if (object == null) {
            throw new NullPointerException("object");
        }
        if (fieldName == null) {
            throw new NullPointerException("fieldName");
        }
        try {
            Field field = getFieldAux(object.getClass(), fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred during field get, fieldName=" + fieldName, e);
        }
    }

    public static Object getField(Class clazz, String fieldName) {
        if (clazz == null) {
            throw new NullPointerException("clazz");
        }
        if (fieldName == null) {
            throw new NullPointerException("fieldName");
        }
        try {
            Field field = getFieldAux(clazz, fieldName);
            field.setAccessible(true);
            return field.get(null);
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred during field get, fieldName=" + fieldName, e);
        }
    }

    private static Field getFieldAux(Class clazz, String fieldName) throws NoSuchFieldException {
        NoSuchFieldException exception = null;
        Class temp = clazz;
        Field field = null;
        do {
            try {
                field = temp.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                exception = e;
            }
            if (field != null) {
                return field;
            }
            temp = temp.getSuperclass();
        } while (temp != null);

        throw exception;
    }

    public static void setField(Object object, String fieldName, Object value) {
        if (object == null) {
            throw new NullPointerException("object");
        }
        if (fieldName == null) {
            throw new NullPointerException("fieldName");
        }
        try {
            Field field = getFieldAux(object.getClass(), fieldName);
            field.setAccessible(true);
            field.set(object, value);
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred during field set, fieldName=" + fieldName, e);
        }
    }

    public static void setField(Class clazz, String fieldName, Object value) {
        if (clazz == null) {
            throw new NullPointerException("clazz");
        }
        if (fieldName == null) {
            throw new NullPointerException("fieldName");
        }
        try {
            Field field = getFieldAux(clazz, fieldName);
            field.setAccessible(true);
            field.set(null, value);
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred during field set, fieldName=" + fieldName, e);
        }
    }

    public static Object invokeMethod(Object object, String methodName, Class[] parameterTypes, Object[] arguments) {
        if (object == null) {
            throw new NullPointerException("object");
        }
        if (methodName == null) {
            throw new NullPointerException("methodName");
        }
        try {
            Method method = getMethod(object.getClass(), methodName, parameterTypes);
            method.setAccessible(true);
            return method.invoke(object, arguments);
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred during method invocation, methodName=" + methodName, e);
        }
    }

    public static Object invokeMethod(Class clazz, String methodName, Class[] parameterTypes, Object[] arguments) {
        if (clazz == null) {
            throw new NullPointerException("clazz");
        }
        if (methodName == null) {
            throw new NullPointerException("methodName");
        }
        try {
            Method method = getMethod(clazz, methodName, parameterTypes);
            method.setAccessible(true);
            return method.invoke(null, arguments);
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred during method invocation, methodName=" + methodName, e);
        }
    }

    public static Method getMethod(Class clazz, String methodName, Class[] parameterTypes) throws NoSuchMethodException {
        if (clazz == null) {
            throw new NullPointerException("clazz");
        }
        if (methodName == null) {
            throw new NullPointerException("methodName");
        }

        NoSuchMethodException exception = null;
        Class temp = clazz;
        Method method = null;
        do {
            try {
                method = temp.getDeclaredMethod(methodName, parameterTypes);
            } catch (NoSuchMethodException e) {
                exception = e;
            }
            if (method != null) {
                return method;
            }
            temp = temp.getSuperclass();
        } while (temp != null);

        throw exception;
    }

    private ReflectionUtils() {
        // Private constructor.
    }
}