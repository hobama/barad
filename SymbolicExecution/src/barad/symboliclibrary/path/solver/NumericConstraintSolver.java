package barad.symboliclibrary.path.solver;

import java.util.LinkedHashMap;

import barad.symboliclibrary.path.floats.FloatPathConstraint;
import barad.symboliclibrary.path.integers.IntegerPathConstraint;

public interface NumericConstraintSolver {

	public void createIntProblem();
	
	public void createRealProblem();
	
	public void addIntCosntraint(IntegerPathConstraint constraint);
	
	public void addRealCosntraint(FloatPathConstraint constraint);
	
	public void addInputVaribale(String name);
	
	public void solveIntProblem();
	
	public void solveRealProblem();
	
	public void concretizeInt();
	
	public void concretizeReal();
	
	public LinkedHashMap<String, String> getConcretizedValues(); 
	
	public void printConcretizedSolution();
}
