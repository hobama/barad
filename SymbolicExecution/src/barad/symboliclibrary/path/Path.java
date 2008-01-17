package barad.symboliclibrary.path;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import barad.symboliclibrary.common.ConstraintType;
import barad.symboliclibrary.floats.SymbolicFloatEntity;
import barad.symboliclibrary.integers.SymbolicIntegerEntity;
import barad.symboliclibrary.path.floats.FloatPathConstraint;
import barad.symboliclibrary.path.integers.IntegerPathConstraint;
import barad.symboliclibrary.path.solver.ConstraintSolverFactory;
import barad.symboliclibrary.path.solver.numeric.NumericConstraintSolver;
import barad.symboliclibrary.path.solver.string.StringConstraintSolver;
import barad.symboliclibrary.path.strings.StringPathConstraint;
import barad.symboliclibrary.strings.SymbolicStringEntity;
import barad.symboliclibrary.test.Descriptor;
import barad.symboliclibrary.test.TestCase;
import barad.symboliclibrary.test.TestInput;
import barad.symboliclibrary.test.xml.XMLWriter;
import barad.symboliclibrary.test.xml.XMLWriterFactory;
import barad.symboliclibrary.ui.common.SymbolicMockObject;
import barad.symboliclibrary.ui.events.SymbolicEvent;
import barad.util.Util;

public class Path {
	private static Logger log = Logger.getLogger(Path.class);  
	private static LinkedList<State> stateStack = new LinkedList<State>();
	private static LinkedList<PathConstraintInterface> reversedConstraints = null;
	private static NumericConstraintSolver numericSolver =  ConstraintSolverFactory.getInstance().getNumericSolverInstance();
	private static StringConstraintSolver stringSolver = ConstraintSolverFactory.getInstance().getStringSolverInstance();
	private static String executedMethod;
	private static Set<TestCase> testCases = new HashSet<TestCase>();
	private static HashMap<String, Set<TestCase>> pendingTestcases = new HashMap<String, Set<TestCase>>();
	private static XMLWriter xmlWriter = XMLWriterFactory.getInstance().getXMLWriterInstance();
	private static boolean mergeTestCases;
	private static boolean outputToConsole;
	private static boolean outputToXML;
	private static boolean outputToJVM;
	private static Descriptor triggeringEventDescriptor;
	static {
		String propertyValue = Util.getProperties().getProperty("merge.test.cases");
		mergeTestCases = Boolean.parseBoolean(propertyValue);
		propertyValue = Util.getProperties().getProperty("output.to.console");
		outputToConsole = Boolean.parseBoolean(propertyValue);
		propertyValue = Util.getProperties().getProperty("output.to.xml");
		outputToXML = Boolean.parseBoolean(propertyValue);
		propertyValue = Util.getProperties().getProperty("output.to.jvm");
		outputToJVM =  Boolean.parseBoolean(propertyValue);
	}
	
	/**
	 * Adds an input parameter of the initial method from which the
	 * symbolic xecution begins. These are variables we should concretize
	 * @param value The variable name
	 */
	public static Object addInputVariable(Object obj) {
		if (obj instanceof SymbolicStringEntity) {
			SymbolicStringEntity entity = (SymbolicStringEntity)obj;
			stringSolver.addInputVaribale(entity.getName(), entity.getDescriptor());
			log.debug("Input variable added: " + entity.getId());
		} else if (obj instanceof SymbolicIntegerEntity) {
			SymbolicIntegerEntity entity = (SymbolicIntegerEntity)obj;
			numericSolver.addInputVaribale(entity.getId(), entity.getDescriptor());
			log.debug("Input variable added: " + entity.getId());
		} else if (obj instanceof SymbolicFloatEntity) {
			SymbolicFloatEntity entity = (SymbolicFloatEntity)obj;
			numericSolver.addInputVaribale(entity.getId(), entity.getDescriptor());
			log.debug("Input variable added: " + entity.getId());
		} else if (obj instanceof SymbolicEvent) {
			//TODO: Add the event fields in the symbolic execution
			//do nothing for now
		} else {
			log.warn("The initial symbolic execution method takes as a parameter non symbolic entity: " + obj.toString());
		}
		return obj;
	}
	
	/**
	 * Add a new branch contraint to the last program state
	 * @param constraint The contsraint
	 * @return The contsraint
	 */
	public static Object addBranchConstraint(Object constraint) {
		if (constraint instanceof PathConstraintInterface) {
			PathConstraintInterface pathConstraintInterface = ((PathConstraintInterface)constraint).inverse(); 
			State lastState = stateStack.peek();
			lastState.setType(pathConstraintInterface.getType());
			lastState.getConstraints().add(pathConstraintInterface);
			log.debug("Added constraint: " + pathConstraintInterface.toString() + " to state: " + lastState.getId());
		} else {
			log.warn("Unrecognized constraint class");
		}
		return constraint;
	}
	
	/**
	 * Add a deep clone of a method variable that is to be modified.
	 * This method should be called before the bytecode instruction 
	 * that stores a modified value to the local variable 
	 * @param variable Local variable value that is to be modified
	 * @param id Index of the stored local variable in local variables table.
	 * @return The same vriable passed as parameter
	 */
	public static Object addLocalVariable(Object variable, int id) {
		if (stateStack.size() == 0) {
			log.error("Adding variable to empty state stack");
			//we do not store the symbolic widgets--only their symbolic fields
		} else if (!(variable instanceof SymbolicMockObject)){
			State state = stateStack.peek();
			state.getVariableValues().put(id, deepCloneBySerialization(variable));
			log.info("Local variable added: " + (variable == null ? null : variable.toString()) + " id: " + id);
		}
		return variable;
	}
	
	/**
	 * Add a deep clone of a class field that is to be modified.
	 * This method should be called before the bytecode instruction 
	 * that stores a modified value to the local class field 
	 * @param variable Class field value that is to be modified
	 * @param id Name of the class field
	 * @return The same field value passes as parameter
	 */
	public static Object addLocalField(Object field, String name) {
		if (stateStack.size() == 0) {
			log.error("Adding variable to empty state stack");
		} else {
			State state = stateStack.peek();
			state.getFieldValues().put(name, deepCloneBySerialization(field));
			log.info("Local variable added: " + field.toString() + " id: " + name);
		}
		return null;
	}
	
	/**
	 * Create a new state and add it to the state stack.
	 * Propagates the stored modified values (vars & fields) from the 
	 * previous state because thay are needed for backtracking
	 * back to the state in which they were stored
	 */
	public static void createNewState() {
		State state = new State();
		log.info("State created: " + state.getId());
		if (stateStack.size() > 0) {
			state.getVariableValues().putAll(stateStack.peek().getVariableValues());
			state.getFieldValues().putAll(stateStack.peek().getFieldValues());
		}
		stateStack.push(state);
		if (reversedConstraints != null && reversedConstraints.size() > 0 
				&& reversedConstraints.peek().getType() == ConstraintType.IF) {
			for (PathConstraintInterface pc: reversedConstraints) {
				addBranchConstraint(pc);
			}	
			//FIXME: This should be fixed if more time is available - This is a HACK
			//create state which is the same as the one before - simplifies the implementation
			State hackState = new State();
			log.info("State created: " + hackState.getId());
			if (stateStack.size() > 0) {
				hackState.getVariableValues().putAll(state.getVariableValues());
				hackState.getFieldValues().putAll(state.getFieldValues());
			}
			hackState.setConstraints(state.getConstraints());
			stateStack.push(state);
		}
		reversedConstraints = null;
	}
	
	/**
	 * Removes the last state from the state stack
	 */
	public static void removeLastState() {
		log.debug("GENERATING INPUTS FOR STATE: " + stateStack.peek().getId());
		generateTestCase();
		State state = stateStack.pop();
		log.debug("State removed: " + state.getId());
		if (reversedConstraints != null) {
			createNewState();
		}
	}
	
	/**
	 * Gets the value of a stored variable from the previous state.
	 * Before a state is removed all variables that were modified in
	 * that state should be reverted to their values in the previous one
	 * @param id Index of the variable in the method's local variable table
	 * @return The value of the variable in the previous state
 	 */
	public static Object getVariableValue(int id) {
		Object value = null;
		if (stateStack.size() > 1 && stateStack.peek().getVariableValues().size() > 0) {
			State state = stateStack.pop();
			value = stateStack.peek().getVariableValues().get(id);
			stateStack.push(state);
			log.debug("Local variable restored: " + value + " id: " + id);
		}
		return value;
	}
	
	/**
	 * Gets the value of a stored field from the previous state.
	 * Before a state is removed all fields that were modified in
	 * that state should be reverted to their values in the previous one
	 * @param name Field name
	 * @return The value of the field in the previous state
 	 */
	public static Object getFieldValue(String name) {
		Object value = null;
		if (stateStack.size() > 1 && stateStack.peek().getVariableValues().size() > 0) {
			State state = stateStack.pop();
			value = stateStack.peek().getFieldValues().get(name);
			stateStack.push(state);
			log.debug("Local field restored: " + value.toString() + " id: " + name);
		}
		return value;
	}
	
	/**
	 * Store the constraints from the current state. They will be
	 * reversed and used in the next state. This is needed for the
	 * else part of each if statement. Called right before visiting
	 * the bytecode responsible for the else 
	 */
	public static void reverseBranchConstraints() {
		reversedConstraints = stateStack.peek().getConstraints();
	}
	
	public static void generateTestSuite() {
		//alway remove subsumed tests
		removeSumbsumedTestCases();
		//merge if specified so
		if (mergeTestCases) {
			mergeNonIntersectingTestCases();
		}
		//output to XML if specified so
		if (outputToXML) {
			writeTestSuiteToXML();
		} 
		//output to JVM if specified so
		if (outputToJVM) {
			writeTestSuiteToJVM();
		}
		//output to Console if specified so
		if (outputToConsole) {
			writeTestSuiteToConsole();
		} 
		//reset the path
		reset();
	}
	
	/**
	 * Gives access to the test cases stored in a static field of this 
	 * class. This could be used by other tools running in the same JVM 
	 * (or using RMI while this JVM is still running) to access the test
	 * suite generated for a specific method  
	 * @return A map from method name to test suite
	 */
	public static HashMap<String, Set<TestCase>> getPendingTestcases() {
		return pendingTestcases;
	}


	/**
	 * Accessor to the last/currently symbolically executed method
	 * i.e. event handler
	 * @return The name of the method 
	 */
	public static String getExecutedMethod() {
		return executedMethod;
	}

	/**
	 * Setter to the last/currently symbolically executed method
	 * i.e. event handler
	 * @param The name of the method 
	 */
	public static void setExecutedMethod(String executedMethod) {
		Path.executedMethod = executedMethod;
	}
	
	private static void writeTestSuiteToJVM() {
		pendingTestcases.put(executedMethod, testCases);
	}
	
	/**
	 * Writes the test suite for a symbolically executed method to the
	 * console 
	 */
	private static void writeTestSuiteToConsole() {
		log.info("Symbolically executed method:" + executedMethod + '\n');
		if (testCases.size() > 0) {
			for (TestCase tc: testCases) {
				log.info(tc.toString());
			}
		} else {
			log.info("There are no test cases for this method");
		}
	}
	
	private static void writeTestSuiteToXML() {
		xmlWriter.writeToXML(testCases, executedMethod, triggeringEventDescriptor);
	}
	
	/**
	 * Merge test cases with no common test inputs
	 */
	private static void mergeNonIntersectingTestCases() {
		Object[] testCaseArray = testCases.toArray();
		for (int i = 0; i < testCaseArray.length; i++) {
			for (int j = i + 1; j < testCaseArray.length; j++) {
				TestCase first = (TestCase)testCaseArray[i];
				TestCase second = (TestCase)testCaseArray[j];
				if (second.merge(first)) {
					break;
				}
			}
		}
		System.out.println();
	}
	
	/**
	 * Remove test cases that are subsumed in other test cases. 
	 */
	private static void removeSumbsumedTestCases() {
		//remove tests that are subsumed in other tests
		//sort by length
		TreeSet<TestCase> sortedTestCases = new TreeSet<TestCase>(testCases);
		//mark the subsumed tests without modifying the collection
		Iterator<TestCase> ascIterator = sortedTestCases.iterator();
		while (ascIterator.hasNext()) {
			TestCase currentAsc = ascIterator.next();
			Iterator<TestCase> descIterator = sortedTestCases.descendingIterator();
			while (descIterator.hasNext()) {
				TestCase currentDesc = descIterator.next();
				if (currentAsc == currentDesc) {
					break;
				}
				if (currentDesc.subsumes(currentAsc)) {
					break;
				}
			}
		}
		//remove subsumed tests
		HashSet<TestCase> relevantCases = new HashSet<TestCase>();
		for (TestCase tc: sortedTestCases) {
			if (!tc.isIrrelevant()) {
				relevantCases.add(tc);
			}
		}
		testCases = relevantCases;
	}
	
	/**
	 * Reset the path i.e. remove all residual data
	 * from the execution of the prevoius method
	 */
	private static void reset() {
		stateStack = new LinkedList<State>();
		testCases = new HashSet<TestCase>();
		reversedConstraints = null;
		executedMethod = null;
		stringSolver.reset();
		numericSolver.reset();
	}
	
	/**
	 * Returns a deep clode of an object obtained via serialization
	 * @param obj object to be cloned
	 * @return Clone of the parameter object
	 */
	private static Object deepCloneBySerialization(Object obj) {
		Object result = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream objectOutput = new ObjectOutputStream(baos);
			objectOutput.writeObject(obj);
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream objectInput = new ObjectInputStream(bais);
			result = objectInput.readObject();
		} catch (IOException ioe) {
			log.error("Error during object serialization " + ioe, ioe);
		} catch (ClassNotFoundException cnfe) {
			/*never thrown we work on the same machine*/
		}
		return result;
	}
	
	/**
	 * Generates the next test case 
	 */
	private static void generateTestCase() {
		//Create problems
		numericSolver.createIntProblem();
		numericSolver.createRealProblem();
		stringSolver.createStringProblem();
		//Add consraints
		for (State s:stateStack) {
			//Read constraints from each state
			for (PathConstraintInterface c: s.getConstraints()) {
				if (c instanceof IntegerPathConstraint) {
					numericSolver.addIntCosntraint((IntegerPathConstraint)c);
				} else if (c instanceof FloatPathConstraint) {
					numericSolver.addRealCosntraint((FloatPathConstraint) c);
				} else if (c instanceof StringPathConstraint) {
					stringSolver.addStringConstriant((StringPathConstraint)c);	
				} else {
					log.error("Unsupported cosnstraint type: " + c.toString());
				}
			}
		}	
		//Solve the problem
		numericSolver.solveIntProblem();
		numericSolver.solveRealProblem();
		stringSolver.solveStringProblem();
		//Concretize
		numericSolver.concretizeInt();
		numericSolver.concretizeReal();
		stringSolver.concretizeString();
		//Print values
		numericSolver.printConcretizedSolution();
		stringSolver.printConcretizedSolution();
		//add the test case 
		HashSet<TestInput> testInputs = new HashSet<TestInput>();
		testInputs.addAll(numericSolver.getConcretizedIntValues());
		testInputs.addAll(numericSolver.getConcretizedRealValues());
		testInputs.addAll(stringSolver.getConcretizedStringValues());
		TestCase testCase = new TestCase();
		testCase.setTestInputs(testInputs);
		testCases.add(testCase);
	}

	/**
	 * Gets the descriptor of the event source i.e. which 
	 * widget and which event (the event class)
	 * @return The event descriptor
	 */
	public static Descriptor getTriggeringEventDescriptor() {
		return triggeringEventDescriptor;
	}

	/**
	 * Sets the descriptor of the event source i.e. which 
	 * widget and which event (the event class)
	 * @return The event descriptor
	 */
	public static void setTriggeringEventDescriptor(Descriptor triggeringEventDescriptor) {
		Path.triggeringEventDescriptor = triggeringEventDescriptor;
	}
}
