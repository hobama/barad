package edu.utexas.barad.common;

import java.lang.reflect.Array;
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

    private ReflectionUtils() {
        // Private constructor.
    }
}