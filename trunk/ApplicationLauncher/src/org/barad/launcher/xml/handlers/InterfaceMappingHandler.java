package org.barad.launcher.xml.handlers;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;
import org.barad.interfacemapping.Element;
import org.barad.interfacemapping.Map;
import org.barad.interfacemapping.Mapping;
import org.barad.interfacemapping.To;
import org.barad.launcher.LauncherProperties;
import org.barad.launcher.util.Util;

public class InterfaceMappingHandler {
	 private Logger log = Logger.getLogger(getClass());
	 /**
	  * Builds a map that is used during the instrumentation phase to replace abstract interface classes
	  * with the concrete ones used for the specific platform on which we are running the application
	  * @param fileName The name of the xml file that conains the interface mappings
	  */
	 @SuppressWarnings("unchecked")
	 public HashMap<Class, HashMap<Method, HashMap<Class, Set<Method>>>> parseLoadInterfaceMapping(String fileName) {	
		 HashMap<Class, HashMap<Method, HashMap<Class, Set<Method>>>> interfaceMap = new HashMap<Class, HashMap<Method, HashMap<Class, Set<Method>>>>();
		 try {
			 JAXBContext context = JAXBContext.newInstance("org.barad.interfacemapping");
			 Unmarshaller unmarshaler = context.createUnmarshaller();
			 File file = new File(LauncherProperties.INTERFACE_MAPPING_PATH + fileName);
			 if (!file.exists()) {
				 log.error("File " + LauncherProperties.INTERFACE_MAPPING_PATH + fileName + " not found");
				 return null;
			 }
			 Map map = (Map)unmarshaler.unmarshal(file);
			 Mapping mapping = map.getMapping();
			 for (Element element: (List<Element>)mapping.getElement()) {
				 HashMap<Class, Set<Method>> toMap = new HashMap<Class, Set<Method>>();
				 //add each "to" mapping
				 for (To to: (List<To>)element.getTo()) {
					 Class toClass = Util.getClass(to.getClazz());
					 Set<Method> toMethods = new HashSet<Method>();
					 for (org.barad.interfacemapping.Method m: (List<org.barad.interfacemapping.Method>)to.getMethod()) {
						 Class[] fromParameterTypes = Util.getMethodParameters(m.getParameter());
						 toMethods.add(Util.getMethod(toClass, m.getName(), fromParameterTypes));
					 }
					 toMap.put(toClass, toMethods);
				 }
				 //add the "from" mapping
				 HashMap<Method, HashMap<Class, Set<Method>>> fromMap = new HashMap<Method, HashMap<Class, Set<Method>>>();
				 Class fromClass = Util.getClass(element.getFrom().getClazz());
				 Class[] toParameterTypes = Util.getMethodParameters((List<String>)element.getFrom().getMethod().getParameter());
				 Method fromMethod = Util.getMethod(fromClass, element.getFrom().getMethod().getName(), toParameterTypes);
				 fromMap.put(fromMethod, toMap);
				 //add final mapping
				 interfaceMap.put(fromClass, fromMap);
			 }
		 } catch (JAXBException je) {
			 log.error("Error during parsing of interface mapping definition from file " + fileName, je);
		 }
		 return interfaceMap;
	 }
}
