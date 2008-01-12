package barad.symboliclibrary.path.solver.string.impl;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import util.BaseTestSuite;
import barad.symboliclibrary.path.solver.string.StringConstraintSolver;
import barad.symboliclibrary.path.strings.Equals;
import barad.symboliclibrary.path.strings.StringPathConstraint;
import barad.symboliclibrary.strings.SymbolicString;
import barad.symboliclibrary.test.Descriptor;
import barad.symboliclibrary.test.TestInput;


/**
 * Test the string constraint solver
 * @author svetoslavganov
 *
 */
public class Test_BaradStringConstraintSolver_00 extends BaseTestSuite {
	private Descriptor descriptor;
	
	public Test_BaradStringConstraintSolver_00() {
		Descriptor descriptor = new Descriptor();
		descriptor.setParentClass("parentClass");
		descriptor.setParentId("parentId");
		descriptor.setWidgetClass("widgetClass");
		descriptor.setWidgetId("widgetId");
		descriptor.setWidgetProperty("widgetProperty");
		this.descriptor = descriptor;
	}
	
	//two equals constraints
	@Test
	public void test_BaradStringConstraintSolver00() throws Exception {
		//create the solver
		StringConstraintSolver solver = new BaradStringConstraintSolver();
		//create a problem
		solver.createStringProblem();
		//add input variables
		SymbolicString symbolicVariable = new SymbolicString(10);
		symbolicVariable.setDescriptor(descriptor);
		solver.addInputVaribale(symbolicVariable.getName(), descriptor);
		//add constraints
		StringPathConstraint constraint1 = new Equals(symbolicVariable, new SymbolicString("Test")).inverse();
		solver.addStringConstriant(constraint1);
		StringPathConstraint constraint2 = new Equals(symbolicVariable, new SymbolicString("Te")).inverse();
		solver.addStringConstriant(constraint2);
		//solve and concretize
		solver.solveStringProblem();
		solver.concretizeString();
		List<TestInput> concrete = solver.getConcretizedStringValues();
		String value = concrete.get(0).getValue();
		Assert.assertEquals(value.length(), 4);
		Assert.assertEquals("Test", value.trim());
	}
	
	//one equals and one not equals constraint
	@Test
	public void test_BaradStringConstraintSolver01() throws Exception {
		//create the solver
		StringConstraintSolver solver = new BaradStringConstraintSolver();
		//create a problem
		solver.createStringProblem();
		//add input variables
		SymbolicString symbolicVariable = new SymbolicString(10);
		symbolicVariable.setDescriptor(descriptor);
		solver.addInputVaribale(symbolicVariable.getName(), descriptor);
		//add constraints
		StringPathConstraint constraint1 = new Equals(symbolicVariable, new SymbolicString("Test")).inverse().inverse();
		solver.addStringConstriant(constraint1);
		StringPathConstraint constraint2 = new Equals(symbolicVariable, new SymbolicString("Svetlio")).inverse();
		solver.addStringConstriant(constraint2);
		//solve and concretize
		solver.solveStringProblem();
		solver.concretizeString();
		List<TestInput> concrete = solver.getConcretizedStringValues();
		String value = concrete.get(0).getValue();
		Assert.assertEquals(7, value.length());
		Assert.assertEquals("Svetlio", value.trim());
	}
}
