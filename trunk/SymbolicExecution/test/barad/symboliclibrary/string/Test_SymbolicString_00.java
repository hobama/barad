package barad.symboliclibrary.string;

import junit.framework.Assert;

import org.junit.Test;

import barad.symboliclibrary.path.strings.Equals;
import barad.symboliclibrary.strings.SymbolicString;

import util.BaseTestSuite;

public class Test_SymbolicString_00 extends BaseTestSuite {
	
	//Accept any string with length 10
	@Test
	public void test_SymbolicString_00 () throws Exception { 
		SymbolicString symbolicString = new SymbolicString(10);
		Assert.assertTrue(symbolicString.accept("TestTestTe"));
	}
	
	//Accept a const symbolic string i.e. the same as regular string
	@Test
	public void test_SymbolicString_01 () throws Exception { 
		SymbolicString symbolicString = new SymbolicString("Test");
		Assert.assertTrue(symbolicString.accept("Test"));
		Assert.assertFalse(symbolicString.accept("Most"));
	}
	
	//Accept a string which is const to some point and until the end accepts any value
	@Test
	public void test_SymbolicString_02 () throws Exception { 
		SymbolicString symbolicString = new SymbolicString("  Test", 10);
		Assert.assertTrue(symbolicString.accept("  Test    "));
		Assert.assertFalse(symbolicString.accept("  Most    "));
	}
	
	//Test the trimming
	@Test
	public void test_trim_00 () throws Exception { 
		SymbolicString symbolicString = new SymbolicString("  Test", 10);
		Assert.assertEquals(2, symbolicString.trim().getBegIndex());
		Assert.assertEquals(5, symbolicString.trim().getEndIndex());
	}
	
	//Test the trimming
	@Test
	public void test_trim_01 () throws Exception { 
		SymbolicString symbolicString = new SymbolicString(10);
		Assert.assertEquals(0, symbolicString.trim().getBegIndex());
		Assert.assertEquals(9, symbolicString.trim().getEndIndex());
	}
	
	//Test the operations for gender
	@Test
	public void test_WorkoutGeneratorOperations_00 () throws Exception { 
		SymbolicString symbolicString = new SymbolicString("Male", 10);
		SymbolicString trimmed = symbolicString.trim();
		Assert.assertEquals(0, trimmed.getBegIndex());
		Assert.assertEquals(3, trimmed.getEndIndex());
		Assert.assertEquals("Male", trimmed.concretize());
	}
	
	//Test the operations for gender (the same mechanism is used for all other input widgets)
	@Test
	public void test_WorkoutGeneratorOperations_01 () throws Exception { 
		SymbolicString symbolicString = new SymbolicString(10);
		Equals pathConstraint = new Equals(symbolicString, new SymbolicString("Male"));
		//evalute
		pathConstraint.inverse();
		Assert.assertEquals("Male", pathConstraint.concretize().trim());
		//evalute
		pathConstraint.inverse();
		pathConstraint = new Equals(pathConstraint.getConstraintString(), new SymbolicString("Female"));
		//evaluate
		pathConstraint.inverse();
		
		SymbolicString constraintResult = pathConstraint.getConstraintString();
	    //check result
		Assert.assertEquals("Female", constraintResult.concretize().trim());
		Assert.assertTrue(constraintResult.accept("Female    "));
		Assert.assertFalse(constraintResult.accept("Male      "));
		Assert.assertEquals(0, constraintResult.getBegIndex());
		Assert.assertEquals(9, constraintResult.getEndIndex());
	}
}
