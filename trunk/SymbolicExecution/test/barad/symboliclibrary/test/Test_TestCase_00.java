package barad.symboliclibrary.test;

import java.util.HashSet;
import java.util.SortedSet;
import java.util.TreeSet;

import junit.framework.Assert;

import org.junit.Test;

import util.BaseTestSuite;

public class Test_TestCase_00 extends BaseTestSuite {
	
	//same test cases should be eliminated i.e. same hash and equal
	@Test
	public void testHashCodeAndEquals00() throws Exception {
		//desciptor1
		Descriptor descriptor1 = new Descriptor();
		descriptor1.setParentClass("parentClass");
		descriptor1.setParentId("parentId");
		descriptor1.setWidgetClass("widgetClass");
		descriptor1.setWidgetId("widgetId");
		descriptor1.setWidgetProperty("widgetProperty");
		descriptor1.setWidgetIndex("index");
		//testinput 1
		TestInput testInput1 = new TestInput();
		testInput1.setDescriptor(descriptor1);
		testInput1.setValue("value");
		//test case 1
		TestCase testCase1 = new TestCase();
		HashSet<TestInput> testInputs1 = new HashSet<TestInput>();
		testInputs1.add(testInput1);
		testCase1.setTestInputs(testInputs1);
		//desciptor2
		Descriptor descriptor2 = new Descriptor();
		descriptor2.setParentClass("parentClass");
		descriptor2.setParentId("parentId");
		descriptor2.setWidgetClass("widgetClass");
		descriptor2.setWidgetId("widgetId");
		descriptor2.setWidgetProperty("widgetProperty");
		descriptor2.setWidgetIndex("index");
		//testinput 2
		TestInput testInput2 = new TestInput();
		testInput2.setDescriptor(descriptor2);
		testInput2.setValue("value");
		//test case 2
		TestCase testCase2 = new TestCase();
		HashSet<TestInput> testInputs2 = new HashSet<TestInput>();
		testInputs2.add(testInput2);
		testCase2.setTestInputs(testInputs2);
		//check correctness
		HashSet<TestCase> testCases = new HashSet<TestCase>();
		testCases.add(testCase1);
		testCases.add(testCase2);
		Assert.assertEquals(testCases.size(), 1);
	}
	
	//test compare to i.e. the correct sorting 
	@Test
	public void testCompareTo00() throws Exception {
		//desciptor1
		Descriptor descriptor1 = new Descriptor();
		descriptor1.setParentClass("parentClass");
		descriptor1.setParentId("parentId");
		descriptor1.setWidgetClass("widgetClass");
		descriptor1.setWidgetId("widgetId");
		descriptor1.setWidgetProperty("widgetProperty");
		descriptor1.setWidgetIndex("index");
		//testinput 1
		TestInput testInput1 = new TestInput();
		testInput1.setDescriptor(descriptor1);
		testInput1.setValue("value");
		//test case 1
		TestCase testCase1 = new TestCase();
		HashSet<TestInput> testInputs1 = new HashSet<TestInput>();
		testInputs1.add(testInput1);
		testCase1.setTestInputs(testInputs1);
		//desciptor2
		Descriptor descriptor2 = new Descriptor();
		descriptor2.setParentClass("parentClass");
		descriptor2.setParentId("parentId");
		descriptor2.setWidgetClass("widgetClass");
		descriptor2.setWidgetId("widgetId");
		descriptor2.setWidgetProperty("widgetProperty");
		//testinput 2
		TestInput testInput2 = new TestInput();
		testInput2.setDescriptor(descriptor2);
		testInput2.setValue("value");
		//testinput 3
		TestInput testInput3 = new TestInput();
		testInput3.setDescriptor(descriptor2);
		testInput3.setValue("value");
		//test case 2
		TestCase testCase2 = new TestCase();
		HashSet<TestInput> testInputs2 = new HashSet<TestInput>();
		testInputs2.add(testInput2);
		testInputs2.add(testInput3);
		testCase2.setTestInputs(testInputs2);
		//check correctness
		HashSet<TestCase> testCases = new HashSet<TestCase>();
		testCases.add(testCase1);
		testCases.add(testCase2);
		SortedSet<TestCase> sortedTestCases = new TreeSet<TestCase>(testCases);
		Assert.assertEquals(sortedTestCases.size(), 2);
		Assert.assertEquals(sortedTestCases.last(), testCase2);
		Assert.assertEquals(sortedTestCases.first(), testCase1);
	}
}
