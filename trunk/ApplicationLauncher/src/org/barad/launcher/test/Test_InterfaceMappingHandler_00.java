package org.barad.launcher.test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import junit.framework.Assert;

import org.barad.launcher.xml.handlers.InterfaceMappingHandler;
import org.junit.Test;

public class Test_InterfaceMappingHandler_00 extends ApplicationLauncherTestCase {
	/**
	 * Tests loading of a valid xml
	 */ 
	@Test
	public void test_parseLoadInterfaceMapping_01() throws Exception {	
		final String result = "class java.lang.StringBufferpublic synchronized java.lang.StringBuffer java.lang.StringBuffer.append(java.lang.CharSequence,int,int)class java.lang.Stringpublic int java.lang.String.indexOf(java.lang.String,int)class java.lang.Stringpublic java.lang.String java.lang.String.substring(int,int)class java.lang.Integerpublic final native void java.lang.Object.wait(long) throws java.lang.InterruptedExceptionclass java.lang.StringBuilderpublic java.lang.StringBuilder java.lang.StringBuilder.append(java.lang.CharSequence,int,int)class java.lang.Stringpublic int java.lang.String.indexOf(int,int)class java.lang.Stringpublic java.lang.String java.lang.String.substring(int,int)class java.lang.Integerpublic final native void java.lang.Object.wait(long) throws java.lang.InterruptedException";
		StringBuilder stringBuilder = new StringBuilder();
		InterfaceMappingHandler handler = new InterfaceMappingHandler();
		HashMap<Class, HashMap<Method, HashMap<Class, Set<Method>>>> interfaceMap = handler.parseLoadInterfaceMapping("TestAbstractWidgetsToSWTWidgets.xml");
		Assert.assertNotNull(interfaceMap);
		for (Map.Entry<Class, HashMap<Method, HashMap<Class, Set<Method>>>> e1: interfaceMap.entrySet()) {
			for (Map.Entry<Method, HashMap<Class, Set<Method>>> e2: e1.getValue().entrySet()) {
				stringBuilder.append(e1.getKey().toString() + e2.getKey().toString());
				for (Map.Entry<Class, Set<Method>> e3: e2.getValue().entrySet()) {
					for (Method m : e3.getValue()) {
						stringBuilder.append(e3.getKey().toString() + m.toString());
					} 
				}
			}
		}
		Assert.assertEquals(stringBuilder.toString().trim(), result);
	}
}