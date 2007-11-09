package org.barad.launcher.test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.barad.launcher.instrument.InterfaceMapping;
import org.barad.launcher.instrument.InterfaceMapping.ClassMethodPair;
import org.barad.launcher.xml.handlers.InterfaceMappingHandler;
import org.junit.Test;

public class Test_InterfaceMapping_00 extends ApplicationLauncherTestCase {

	/**
	 * Test existing mapping 
	 * NOTE: This test case depends on success of Test_InterfaceMappingHandler_00
	 */
	@Test
	public void test_parseLoadInterfaceMapping_01() throws Exception {	
		final String expectedResult = "Class: class java.lang.Integer Method: public final native void java.lang.Object.wait(long) throws java.lang.InterruptedExceptionClass: class java.lang.String Method: public int java.lang.String.indexOf(int,int)Class: class java.lang.String Method: public java.lang.String java.lang.String.substring(int,int)";
		InterfaceMappingHandler handler = new InterfaceMappingHandler();
		HashMap<Class, HashMap<Method, HashMap<Class, Set<Method>>>> interfaceMap = handler.parseLoadInterfaceMapping("TestAbstractWidgetsToSWTWidgets.xml");
		InterfaceMapping.setInterfaceMap(interfaceMap);
		String className = "java.lang.StringBuilder";
		String methodName = "append";
		List<String> parameterClassNames = new LinkedList<String>();
		parameterClassNames.add("java.lang.CharSequence");
		parameterClassNames.add("int");
		parameterClassNames.add("int");
		ClassMethodPair[] classMethodPair = InterfaceMapping.getClassMethodToClassMethodMapping(className, methodName, parameterClassNames);
		StringBuilder resultStringBuilder = new StringBuilder();
		for (ClassMethodPair cmp: classMethodPair) {
			resultStringBuilder.append(cmp.toString());
		}
		Assert.assertEquals("There should be three mappings", expectedResult, resultStringBuilder.toString().trim());
	}
}