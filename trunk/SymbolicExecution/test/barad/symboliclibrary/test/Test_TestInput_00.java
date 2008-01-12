package barad.symboliclibrary.test;

import java.util.HashSet;

import junit.framework.Assert;

import org.junit.Test;

import util.BaseTestSuite;

public class Test_TestInput_00 extends BaseTestSuite {
	//same test inputs should be eliminated i.e. same hash and equal	
	@Test
	public void testHashCodeAndEquals00() throws Exception {
		//desciptor1
		Descriptor descriptor1 = new Descriptor();
		descriptor1.setParentClass("parentClass");
		descriptor1.setParentId("parentId");
		descriptor1.setWidgetClass("widgetClass");
		descriptor1.setWidgetId("widgetId");
		descriptor1.setWidgetProperty("widgetProperty");
		descriptor1.setIndex("index");
		//testinput 1
		TestInput testInput1 = new TestInput();
		testInput1.setDescriptor(descriptor1);
		testInput1.setValue("value");
		//desciptor2
		Descriptor descriptor2 = new Descriptor();
		descriptor2.setParentClass("parentClass");
		descriptor2.setParentId("parentId");
		descriptor2.setWidgetClass("widgetClass");
		descriptor2.setWidgetId("widgetId");
		descriptor2.setWidgetProperty("widgetProperty");
		descriptor2.setIndex("index");
		//testinput 2
		TestInput testInput2 = new TestInput();
		testInput2.setDescriptor(descriptor2);
		testInput2.setValue("value");
		//check correctness
		HashSet<TestInput> testInput = new HashSet<TestInput>();
		testInput.add(testInput1);
		testInput.add(testInput2);
		Assert.assertEquals(testInput.size(), 1);
	}
}
