package org.barad.launcher.util;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.log4j.Logger;

public class Util {
	private static Logger log = Logger.getLogger(Util.class);
	/**
	  * Gets a class for a class name
	  * @param className The class name
	  * @return
	  */
	public static Class getClass(String className) {
		 Class clazz = null;
		 try {
			 clazz = Class.forName(className);
		 } catch(ClassNotFoundException cnfe) {
			 log.error("Could not load class '" + className + "'. Maybe, the class file is not on the class path", cnfe);
		 }
		 return clazz;
	 }
	 
	 /**
	  * Gets a method of a class gicen the class, the method name and the method's parameters
	  * @param clazz The class
	  * @param name The method name
	  * @param parameterTypes The classes of the method's parameters types
	  * @return The Method object
	  */
	 public static Method getMethod(Class<?> clazz, String name, Class[] parameterTypes) {
		 Method method = null;
		 try {
			 method = clazz.getMethod(name, parameterTypes);
		 } catch(NoSuchMethodException nsme) {
			 log.error("Could not find method '" + name + "'. Maybe, the method does not exist or its parameter list is incorrect", nsme);
		 }
		 return method;
	 }
	 
	 /**
	  * Gets the classes for a method parameters given list of their names
	  * @param parameterClassNames The list of class names for the method
	  * @return Array of class objects
	  */
	 public static Class[] getMethodParameters(List<String> parameterClassNames) {
		 Class[] parameterTypes = new Class[parameterClassNames.size()];
		 int i = 0;
		 for (String s: parameterClassNames) {
			 if(s.equals("int")) {
				 parameterTypes[i++] = Integer.TYPE;
			 } else if(s.equals("int")) {
				 parameterTypes[i++] = Byte.TYPE;
			 } else if(s.equals("byte")) {
				 parameterTypes[i++] = Short.TYPE;
			 } else if(s.equals("char")) {
				 parameterTypes[i++] = Character.TYPE;
			 } else if(s.equals("long")) {
				 parameterTypes[i++] = Long.TYPE;
			 } else if(s.equals("float")) {
				 parameterTypes[i++] = Float.TYPE;
			 } else if(s.equals("double")) {
				 parameterTypes[i++] = Double.TYPE;
			 } else if(s.equals("boolean")) {
				 parameterTypes[i++] = Boolean.TYPE;
			 } else {
				 parameterTypes[i++] = getClass(s);
			 }
		 }
		return parameterTypes;
	 }
}
