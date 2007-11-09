package org.barad.launcher.instrument;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.barad.launcher.util.Util;

/**
 * Class that helps in determinig the mapping (if it exists)
 * between abstract user interface class methods and the concrete 
 * class methods with which the former are to be replaced. It
 * also gives the same mapping for the classes.
 * @author svetoslavganov
 */
public class InterfaceMapping {
	 private static Logger log = Logger.getLogger(InterfaceMapping.class);
	 private static HashMap<Class, HashMap<Method, HashMap<Class, Set<Method>>>> interfaceMap = null;

	 /**
	  * Gives the mapping from abstract to concrete class methods
	  * @param className The abstract UI class name
	  * @param methodName The abstract UI method name
	  * @param parameterClassNames The names of the classes of method's parameters
	  * @return Array with the methods to which the abstract UI method is mapped
	  */
	 public static ClassMethodPair[] getClassMethodToClassMethodMapping(String className, String methodName, List<String> parameterClassNames) {
		 if (interfaceMap == null) {
			 log.error("Interface map is null. You must initialize it explicitly.");
			 throw new IllegalArgumentException();
		 }
		 ClassMethodPair[] resultToMapping = new ClassMethodPair[parameterClassNames.size()];		
		 Class fromClass = Util.getClass(className);
		 if (fromClass == null) {
			 return null;
		 }
		 HashMap<Method, HashMap<Class, Set<Method>>> fromMap = interfaceMap.get(fromClass);
		 if (fromMap == null) {
			 return null;
		 } 
		 Class[] methodParameters = Util.getMethodParameters(parameterClassNames);
		 Method fromMethod = Util.getMethod(fromClass, methodName, methodParameters);
		 if (fromMethod == null) {
			 return null;
		 }
		 HashMap<Class, Set<Method>> toMap = fromMap.get(fromMethod);
		 if (toMap == null) {
			 return null;
		 }
		 int count = 0;
		 for (Map.Entry<Class, Set<Method>> c: toMap.entrySet()) {
			 for (Method m: c.getValue()) {
				 resultToMapping[count++] = new ClassMethodPair(c.getKey(), m);
			 }
		 }
		 return resultToMapping;
	 }
	 
	 //TODO: Finish this method and add class to class mappings to the xsd schema
 	 public Class getClassToClassMapping(String className) {
		 return null;
	 }
	 
	 public static HashMap<Class, HashMap<Method, HashMap<Class, Set<Method>>>> getInterfaceMap() {
		 return interfaceMap;
	 }

	 public static void setInterfaceMap(HashMap<Class, HashMap<Method, HashMap<Class, Set<Method>>>> interfaceMap) {
		 InterfaceMapping.interfaceMap = interfaceMap;
	 }
	 
	 /**
	  * Class that represents class/method pair
	  * @author svetoslavganov
	  *
	  */
	 public static class ClassMethodPair {
		 private Class clazz;
		 private Method method;
		 
		 public ClassMethodPair(Class clazz, Method method) {
			 this.clazz = clazz;
			 this.method = method;
		 }
		 
		 @Override
		 public String toString() {
			 return "Class: " + clazz.toString() + " Method: " + method.toString(); 
		 }
		 @Override
		 public boolean equals(Object obj) {
			 if (obj == null) {
				 throw new NullPointerException(); 
			 } else if (obj instanceof ClassMethodPair) {
				 ClassMethodPair classMethodPair = (ClassMethodPair)obj;
				 return clazz.equals(classMethodPair.getClass()) && method.equals(classMethodPair.getMethod());
			 } else {
				 throw new IllegalArgumentException();
			 }
		 }
		
		 public Class getClazz() {
			 return clazz;
		 }
		 
		 public void setClazz(Class clazz) {
			 this.clazz = clazz;
		 }
	
		 public Method getMethod() {
			 return method;
		 }
		
		 public void setMethod(Method method) {
			 this.method = method;
		 }
	 }
}