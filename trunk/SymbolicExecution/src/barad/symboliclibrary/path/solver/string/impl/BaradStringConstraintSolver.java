package barad.symboliclibrary.path.solver.string.impl;

import static barad.util.Properties.DEBUG;
import static barad.util.Properties.VERBOSE;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import barad.symboliclibrary.path.solver.string.StringConstraintSolver;
import barad.symboliclibrary.path.strings.StringPathConstraint;
import barad.symboliclibrary.strings.SymbolicString;
import barad.symboliclibrary.test.Descriptor;
import barad.symboliclibrary.test.TestInput;

/**
 * Class inplements a string constraint solver for the Barad 
 * symbolic execution framework
 * @author svetoslavganov
 *
 */
public class BaradStringConstraintSolver implements StringConstraintSolver {
private Logger log;
	private List<StringPathConstraint> stringProblem;
	private LinkedHashMap<String, SymbolicString> constraintSolutions;
	private HashMap<String, Descriptor> inputVariables;
	private List<TestInput> concretizedString;
	
	public BaradStringConstraintSolver() {
		this.log = Logger.getLogger(getClass());
		this.inputVariables = new HashMap<String, Descriptor>();
		this.stringProblem = new LinkedList<StringPathConstraint>();
	}
	
	/**
	 * Add an input variable for which a concrete value should
	 * be generated 
	 * @param The name of the variable
	 */
	public void addInputVaribale(String name, Descriptor descriptor) {
		inputVariables.put(name, descriptor);
		if (DEBUG && VERBOSE) {
			log.info("Input variable added: " + name);
		}
	}

	/**
	 * Add a new string constraint to the problem
	 * @param The string path constraint to be added
	 */
	public void addStringConstriant(StringPathConstraint constraint) {
		stringProblem.add(constraint);
		if (DEBUG && VERBOSE) {
			log.info("String path constraint added: " + constraint.toString());
		}
	}

	/**
	 * Create a new string problem
	 */
	public void createStringProblem() {
		stringProblem.clear();
		constraintSolutions = new LinkedHashMap<String, SymbolicString>();
		if (DEBUG && VERBOSE) {
			log.info("String problem created");
		}
	}

	/**
	 * Solve the problem composed by all path constraints
	 */
	public void solveStringProblem() {
		for (StringPathConstraint spc: stringProblem) {
			//propagate lhs
			String lhsId = spc.getOp1().getName();
			SymbolicString symbolicString = null;
			if ((symbolicString = constraintSolutions.get(lhsId)) != null) {
				spc.setOp1(symbolicString);
			}
			//propagate rhs
			String rhsId = spc.getOp2().getName();
			symbolicString = null;
			if ((symbolicString = constraintSolutions.get(rhsId)) != null) {
				spc.setOp2(symbolicString);
			}
			//evaluate
			spc.evaluate();
			//add to solutions
			symbolicString = spc.getConstraintString();
			constraintSolutions.put(symbolicString.getName(), symbolicString);
			if (DEBUG && VERBOSE) {
				log.info("String problem solved");
			}
		}
	}
	
	/**
	 * Generates concrete values for the input variables
	 */
	public void concretizeString() {
		concretizedString = new LinkedList<TestInput>();
		for (Map.Entry<String, SymbolicString> e: constraintSolutions.entrySet()) {
			if (inputVariables.get(e.getKey()) != null) {
				String key = e.getKey();
				SymbolicString value = e.getValue();
				String concreteValue = value.concretize();
				if (concreteValue != null) {
					concreteValue = concreteValue.trim();
					TestInput testInput = new TestInput();
					testInput.setValue(concreteValue);
					testInput.setDescriptor(value.getDescriptor());
					concretizedString.add(testInput);
				}
				if (DEBUG && VERBOSE) {
					log.info("Concretized variable: " + key + " Value: " + value);
				}
			}
		}
	}

	/**
	 * Returns the generated concrete string values (if any)
	 * @return Map with the generated concrete values if any, null otherwise
	 */
	public List<TestInput> getConcretizedStringValues() {
		return concretizedString;
	}
	
	/**
	 * Prints the concretized solution
	 */
	public void printConcretizedSolution() {
		if (concretizedString.size() > 0) {
			log.info("Concrete string values:");
			for (TestInput ti: concretizedString) {
				log.info(ti.toString());
			}
		}
	}
	
	/**
	 * Reset the solver i.e. clear all residual data if any
	 */
	public void reset() {
		inputVariables.clear();
	}
}
