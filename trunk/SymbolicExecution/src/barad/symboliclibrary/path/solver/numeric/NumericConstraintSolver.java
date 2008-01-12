package barad.symboliclibrary.path.solver.numeric;

import java.util.List;

import barad.symboliclibrary.path.floats.FloatPathConstraint;
import barad.symboliclibrary.path.integers.IntegerPathConstraint;
import barad.symboliclibrary.test.Descriptor;
import barad.symboliclibrary.test.TestInput;

/**
 * Interface that defines the conract between a numeric
 * constraint solver and the Barad symbolic execution
 * framework
 * @author svetoslavganov
 *
 */
public interface NumericConstraintSolver {

	/**
	 * Creates a new integer problem. This method
	 * is called first and once a problem is created
	 * expressions and variables could be added 
	 */
	public void createIntProblem();
	
	/**
	 * Creates a new real numbere problem. This method
	 * is called first and once a problem is created
	 * expressions and variables could be added 
	 */
	public void createRealProblem();
	
	/**
	 * Adds a new integer constraint to the integer problem
	 * @param constraint The integer constraint to be added
	 */
	public void addIntCosntraint(IntegerPathConstraint constraint);
	
	/**
	 * Adds a new real number constraint to the real problem
	 * @param constraint The real constraint to be added
	 */
	public void addRealCosntraint(FloatPathConstraint constraint);
	
	/**
	 * Add an input variable for which a concrete value should
	 * be generated 
	 */
	public void addInputVaribale(String name, Descriptor descriptor);
	
	/**
	 * Solve the integer problem
	 */
	public void solveIntProblem();
	
	/**
	 * Solve the real problem
	 */
	public void solveRealProblem();
	
	/**
	 * Generate concrete values for each integer variable
	 */
	public void concretizeInt();
	
	/**
	 * Generate concrete values for each real variable
	 */
	public void concretizeReal();
	
	/**
	 * Returns the generated concrete integer values (if any)
	 * @return Map with the generated concrete values if any, null otherwise
	 */
	public List<TestInput> getConcretizedIntValues(); 

	/**
	 * Returns the generated concrete real values (if any)
	 * @return Map with the generated concrete values if any, null otherwise
	 */
	public List<TestInput> getConcretizedRealValues();
	
	/**
	 * Prints the concretized solution
	 */
	public void printConcretizedSolution();
	
	/**
	 * Reset the solver i.e. clear all residual data if any
	 */
	public void reset();
}
