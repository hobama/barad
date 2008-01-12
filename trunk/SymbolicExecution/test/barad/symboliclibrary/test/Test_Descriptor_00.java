package barad.symboliclibrary.test;

import java.util.HashSet;

import junit.framework.Assert;

import org.junit.Test;

import util.BaseTestSuite;

public class Test_Descriptor_00 extends BaseTestSuite {
	//same test cases should be eliminated i.e. same hash and equal
	@Test
	public void test_hashCodeAndEquals00() throws Exception {
		//desciptor1
		Descriptor descriptor1 = new Descriptor();
		descriptor1.setParentClass("parentClass");
		descriptor1.setParentId("parentId");
		descriptor1.setWidgetClass("widgetClass");
		descriptor1.setWidgetId("widgetId");
		descriptor1.setWidgetProperty("widgetProperty");
		//desciptor2
		Descriptor descriptor2 = new Descriptor();
		descriptor2.setParentClass("parentClass");
		descriptor2.setParentId("parentId");
		descriptor2.setWidgetClass("widgetClass");
		descriptor2.setWidgetId("widgetId");
		descriptor2.setWidgetProperty("widgetProperty");
		//check correctness
		HashSet<Descriptor> testCases = new HashSet<Descriptor>();
		testCases.add(descriptor1);
		testCases.add(descriptor2);
		Assert.assertEquals(testCases.size(), 1);
	}
}
