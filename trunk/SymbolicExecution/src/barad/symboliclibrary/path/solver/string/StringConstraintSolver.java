package barad.symboliclibrary.path.solver.string;

import java.util.List;

import barad.symboliclibrary.path.strings.StringPathConstraint;
import barad.symboliclibrary.test.Descriptor;
import barad.symboliclibrary.test.TestInput;

/**
 * Interface that defines the conract between a string
 * constraint solver and the Barad symbolic execution
 * framework
 * @author svetoslavganov
 *
 */
public interface StringConstraintSolver {

	/**
	 * Add an input variable for which a concrete value should
	 * be generated 
	 * @param The name of the variable
	 */
	public void addInputVaribale(String name, Descriptor descriptor);
	
	/**
	 * Add a new string constraint to the problem
	 * @param The string path constraint to be added
	 * @param Descriptor that relates the value to a widget's 
	 * property
	 */
	public void addStringConstriant(StringPathConstraint constraint);
	
	/**
	 * Create a new string problem
	 */
	public void createStringProblem();
	
	/**
	 * Solve the problem composed by all path constraints
	 */
	public void solveStringProblem();
	
	/**
	 * Generates concrete values for the input variables
	 */
	public void concretizeString();
	
	/**
	 * Returns the generated concrete string values (if any)
	 * @return Map with the generated concrete values if any, null otherwise
	 */
	public List<TestInput> getConcretizedStringValues();
	
	/**
	 * Prints the concretized solution
	 */
	public void printConcretizedSolution();
	
	/**
	 * Reset the solver i.e. clear all residual data if any
	 */
	public void reset();
}
