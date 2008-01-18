package barad.symboliclibrary.path.solver;

import org.apache.log4j.Logger;

import barad.symboliclibrary.path.solver.numeric.NumericConstraintSolver;
import barad.symboliclibrary.path.solver.string.StringConstraintSolver;
import barad.util.Util;

/**
 * Class that is a factory for constraint solvers. This
 * implementation allows modular architecture. The user
 * should judt replace the name of the constraint solver
 * implementation in the properties file
 * @author svetoslavganov
 */
public class ConstraintSolverFactory {
	private Logger log;
	private static ConstraintSolverFactory instance;
	private String numericSolverClassName;
	private String stringSolverClassName;
	private NumericConstraintSolver numericConstraintSolver;
	private StringConstraintSolver stringConstraintSolver;
	
	private ConstraintSolverFactory() {
		log = Logger.getLogger(this.getClass());
		numericSolverClassName = Util.getProperties().getProperty("numeric.constraint.solver");
		stringSolverClassName = Util.getProperties().getProperty("string.constraint.solver");
	}
	
	/**
	 * Gets the instance of XMLWriterFactory if one
	 * does not exist it is created
	 * @return The XMLWriterFactory
	 */
	public static ConstraintSolverFactory getInstance() {
		if (instance == null) {
			instance = new ConstraintSolverFactory(); 
		}
		return instance;
	}
	
	/**
	 * Returns the numeric constraint solver instance. If such one
	 * does not exist it is created. The solver is a singleton 
	 * @return A numeric constraint solver
	 */
	public NumericConstraintSolver getNumericSolverInstance() {
		if (numericConstraintSolver == null) {
			numericConstraintSolver = (NumericConstraintSolver)getSolverInstance(numericSolverClassName);
		}
		return numericConstraintSolver;
	}
	
	/**
	 * Returns the string constraint solver instance. If such one
	 * does not exist it is created. The solver is a singleton 
	 * @return A string constraint solver
	 */
	public StringConstraintSolver getStringSolverInstance() {
		if (stringConstraintSolver == null) {
			stringConstraintSolver = (StringConstraintSolver)getSolverInstance(stringSolverClassName);
		}
		return stringConstraintSolver;
	}
	
	/**
	 * Creates a new constraint solver instance
	 * @return New constraint solver instance
	 */
	private Object getSolverInstance(String solverClassName) {
		Class clazz = null;
		try {
			clazz = Class.forName(solverClassName);
		} catch(ClassNotFoundException cnfe) {
			log.error("Constraint solver class does not exist or is not specified in the properties file " + numericSolverClassName, cnfe);
		}
		return getInstance(clazz);
	}
	
	/**
	 * Instantiates on object of a given class
	 * @param clazz The class
	 * @return Instance of the class, null if any exception 
	 * is thrown during the instantiation
	 */
	private Object getInstance(Class clazz) {
		Object instance = null;
		try {
			instance = clazz.newInstance();
		} catch(InstantiationException ie) {
			log.error(ie);
		} catch (IllegalAccessException iae) {
			log.error(iae);
		}
		return instance;
	}
}
