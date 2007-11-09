package org.barad.abstractuserinterface;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import static org.fitness.generator.WorkoutGeneratorProperties.DEBUG;

public class AbstractEventDispatch {
	private static Logger log = Logger.getLogger(AbstractEventDispatch.class);
	private static LinkedHashMap<Object, LinkedHashMap<Method, LinkedHashMap<Object, HashSet<Method>>>> callbackMap = new LinkedHashMap<Object, LinkedHashMap<Method,LinkedHashMap<Object,HashSet<Method>>>>(); 
	
	public static void addEventCallBack(Object sourceObject, Method sourceMethod, Object desinationObject, Method destinationMethod) {
		LinkedHashMap<Method, LinkedHashMap<Object,HashSet<Method>>> outerValueMap = null;
		LinkedHashMap<Object,HashSet<Method>> innerValueMap = null;
		HashSet<Method> valueSet = null;
		if ((outerValueMap = callbackMap.get(sourceObject)) == null) {
			outerValueMap = new LinkedHashMap<Method, LinkedHashMap<Object,HashSet<Method>>>();
		}
		if ((innerValueMap = outerValueMap.get(sourceMethod)) == null) { 
			innerValueMap =  new LinkedHashMap<Object,HashSet<Method>>();
		}
		if ((valueSet = innerValueMap.get(desinationObject)) == null) {
			valueSet = new HashSet<Method>();
		}
		valueSet.add(destinationMethod);
		innerValueMap.put(desinationObject, valueSet);
		outerValueMap.put(sourceMethod, innerValueMap);
		callbackMap.put(sourceObject, outerValueMap);
	}
	
	public static void removeEventCallback(Object sourceObject, Method sourceMethod, Object desinationObject, Method destinationMethod) {
		//TODO: Finish implementation
	}
	 
	public static void handleEvent(Object sourceObject, Method sourceMethod) {
		LinkedHashMap<Method, LinkedHashMap<Object,HashSet<Method>>> outerMap = null;
		LinkedHashMap<Object,HashSet<Method>> innerMap = null;
		if ((outerMap = callbackMap.get(sourceObject)) != null && (innerMap = outerMap.get(sourceMethod)) != null) {
			for (Map.Entry<Object, HashSet<Method>> listeners : innerMap.entrySet()) {
				for (Method method:listeners.getValue()) {
					invokeEventHandler(sourceObject, listeners.getKey(), method);
				}
			}
		}
	}
	 
	private static void invokeEventHandler(Object sourceObject, Object desinationObject, Method destinationMethod) {
		try {
			destinationMethod.invoke(desinationObject, sourceObject);
			if (DEBUG) {
				log.info("Object: " + desinationObject.toString() + " Method: " + destinationMethod.toString() + " Correctly invoked");
			}
		} catch (IllegalAccessException iae) {
			log.error("Exception while trying to invoke event handler", iae);
		} catch (InvocationTargetException ite) {
			log.error("Exception while invoking event handler method", ite);
		}
	 }

	 public static LinkedHashMap<Object, LinkedHashMap<Method, LinkedHashMap<Object, HashSet<Method>>>> getCallbackMap() {
		 return callbackMap;
	 }
}