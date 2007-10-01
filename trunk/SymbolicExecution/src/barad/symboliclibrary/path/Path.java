package barad.symboliclibrary.path;

import static barad.util.Properties.DEBUG;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import barad.symboliclibrary.common.ConstraintType;
import barad.symboliclibrary.integers.UnsupportedOperationByChoco;
import barad.symboliclibrary.path.floats.FloatPathConstraint;
import barad.symboliclibrary.path.integers.IntegerPathConstraint;
import barad.symboliclibrary.path.strings.StringPathConstraint;
import barad.symboliclibrary.string.SymbolicString;
import choco.Problem;
import choco.integer.IntConstraint;
import choco.integer.IntDomainVar;
import choco.real.constraint.RealConstraint;

public class Path {
	private static Logger log = Logger.getLogger(Path.class);  
	private static LinkedList<State> stateStack = new LinkedList<State>();
	private static LinkedList<PathConstraintInterface> reversedConstraints = null;
	private static LinkedList<HashMap<String, String>> concreteValues = new LinkedList<HashMap<String,String>>();
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
			log.debug("Added constraint: " + pathConstraintInterface.toString());
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
		} else {
			State state = stateStack.peek();
			state.getVariableValues().put(id, deepCloneBySerialization(variable));
			log.info("Local variale added: " + (variable == null ? null : variable.toString()) + " id: " + id);
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
			log.info("Local variale added: " + field.toString() + " id: " + name);
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
		if (reversedConstraints != null && reversedConstraints.size() > 0 
				&& reversedConstraints.peek().getType() == ConstraintType.IF) {
			for (PathConstraintInterface pc: reversedConstraints) {
				addBranchConstraint(pc);
			}	
		}
		reversedConstraints = null;
		stateStack.push(state);
		if (DEBUG) {
			//state.printStoredValues();
		}
	}
	
	/**
	 * Removes the last state from the state stack
	 */
	public static void removeLastState() {
		if (stateStack.size() > 0) {
			State state = stateStack.pop();
			log.debug("State removed: " + state.getId());
			if (reversedConstraints != null) {
				createNewState();
			}
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
	
	public static void generateInputs() {
		HashMap<String, String> concretized = new HashMap<String, String>();
		Problem integerProblem = new Problem();
		Problem realProblem = new Problem();
		//Fix the strings
		for (State s:stateStack) {
			//Read constraints from each state
			for (PathConstraintInterface c: s.getConstraints()) {
				if (c instanceof IntegerPathConstraint) {
					IntegerPathConstraint constraint = (IntegerPathConstraint)c;
					try {
						IntConstraint intConstraint = constraint.getIntConstraint(integerProblem);
						integerProblem.post(intConstraint);
						if (DEBUG) {
							log.info("Integer constraint: " + intConstraint.pretty());
						}
					} catch (UnsupportedOperationByChoco uobc) {
						log.error("Unsupported operation by Choco" + uobc, uobc);
					}
				} 
			}
				/*
				else if (c instanceof FloatPathConstraint) {
					FloatPathConstraint constraint = (FloatPathConstraint)c;
					try {
						integerConstraints.add(constraint.getFloatConstraint(integerProblem));
					} catch (UnsupportedOperationByChoco uobc) {
						log.error(uobc.getStackTrace());
					}
				} else if (c instanceof StringPathConstraint) {
					StringPathConstraint constraint = (StringPathConstraint)c;
					//stringConstraints.add(constraint.getConstraintString());
				} else {
					log.error("Unsupported constraint type: " + c.getClass());
				}
			}
			*/
			//Solve and concretize
			//Integers
			
			if (integerProblem.solve()) {
				for (int i = 0; i < integerProblem.getNbIntVars(); i++) {
					IntDomainVar var = (IntDomainVar)integerProblem.getIntVar(i);
					concretized.put(var.toString(), String.valueOf(var.getVal()));
					if (DEBUG) {
						log.info("Concretized variable\\value pair: " +  var.toString());
					}
				}
			}
			/*
			if (realProblem.solve()) {
				
			}
			*/
		}
	}
	
	/**
	 * Prints all constraints
	 * FIXME: Throws exception
	 */
	public static void printAllConstraints() {
		log.info("STORED CONSTRAINTS");
		if (stateStack.size() > 3) {
			for (Iterator<State> iterator = stateStack.iterator(); iterator.hasNext(); iterator.next()) {
				for (PathConstraintInterface pc: iterator.next().getConstraints()) {
					log.info(/*"State: " + iterator.toString() + */" Constraint: " + pc.toString());
				}
			}
		}
	}
	
	/**
	 * Prints all constraints for the last state
	 */
	public static void printLastStateConstraints() {
		log.info("STORED CONSTRAINTS FOR THE LAST STATE");
		for (PathConstraintInterface pc: stateStack.peek().getConstraints()) {
			log.info("State: " + stateStack.peek().toString() + " Constraint: " + pc.toString());
		}
	}
}
