package barad.symboliclibrary.path.solver;

import org.apache.log4j.Logger;

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
	private String solverClassName;
	
	private ConstraintSolverFactory() {
		log = Logger.getLogger(this.getClass());
		solverClassName = Util.getProperties().getProperty("numeric.constraint.solver");
	}
	
	/**
	 * Gets the instance of ConstraintSolverFactory if one
	 * does not exist it is created
	 * @return The ConstraintSolverFactory
	 */
	public static ConstraintSolverFactory getInstance() {
		if (instance == null) {
			instance = new ConstraintSolverFactory(); 
		}
		return instance;
	}
	
	/**
	 * Creates a new constraint solver instance
	 * @return New constraint solver instance
	 */
	public NumericConstraintSolver getSolverInstance() {
		Class clazz = null;
		try {
			clazz = Class.forName(solverClassName);
		} catch(ClassNotFoundException cnfe) {
			log.error("Constraint solver class does not exist or is not specified in the properties file " + solverClassName, cnfe);
		}
		return (NumericConstraintSolver)getInstance(clazz);
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
			log.error("Exception while instantiating an object of the instrumented class" + ie, ie);
		} catch (IllegalAccessException iae) {
			log.error("Exception while trying to access the instrumented class " + iae, iae);
		}
		return instance;
	}
}
