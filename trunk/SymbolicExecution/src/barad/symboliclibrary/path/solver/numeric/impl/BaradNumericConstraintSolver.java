package barad.symboliclibrary.path.solver.numeric.impl;

import static barad.util.Properties.DEBUG;
import static barad.util.Properties.VERBOSE;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import barad.symboliclibrary.floats.FADD;
import barad.symboliclibrary.floats.FCONST;
import barad.symboliclibrary.floats.FDIV;
import barad.symboliclibrary.floats.FMUL;
import barad.symboliclibrary.floats.FSUB;
import barad.symboliclibrary.floats.FVAR;
import barad.symboliclibrary.floats.FloatInterface;
import barad.symboliclibrary.integers.IADD;
import barad.symboliclibrary.integers.ICONST;
import barad.symboliclibrary.integers.IDIV;
import barad.symboliclibrary.integers.IMUL;
import barad.symboliclibrary.integers.ISUB;
import barad.symboliclibrary.integers.IVAR;
import barad.symboliclibrary.integers.IntegerInterface;
import barad.symboliclibrary.integers.UnsupportedOperationByChoco;
import barad.symboliclibrary.path.floats.FloatPathConstraint;
import barad.symboliclibrary.path.floats.IF_FCMPEQ;
import barad.symboliclibrary.path.floats.IF_FCMPGE;
import barad.symboliclibrary.path.floats.IF_FCMPGT;
import barad.symboliclibrary.path.floats.IF_FCMPLE;
import barad.symboliclibrary.path.floats.IF_FCMPLT;
import barad.symboliclibrary.path.floats.IF_FCMPNE;
import barad.symboliclibrary.path.integers.IFEQ;
import barad.symboliclibrary.path.integers.IFGE;
import barad.symboliclibrary.path.integers.IFGT;
import barad.symboliclibrary.path.integers.IFLE;
import barad.symboliclibrary.path.integers.IFLT;
import barad.symboliclibrary.path.integers.IFNE;
import barad.symboliclibrary.path.integers.IF_ICMPEQ;
import barad.symboliclibrary.path.integers.IF_ICMPGE;
import barad.symboliclibrary.path.integers.IF_ICMPGT;
import barad.symboliclibrary.path.integers.IF_ICMPLE;
import barad.symboliclibrary.path.integers.IF_ICMPLT;
import barad.symboliclibrary.path.integers.IF_ICMPNE;
import barad.symboliclibrary.path.integers.IntegerPathConstraint;
import barad.symboliclibrary.path.solver.numeric.NumericConstraintSolver;
import barad.symboliclibrary.test.Descriptor;
import barad.symboliclibrary.test.TestInput;
import barad.util.Util;
import choco.Constraint;
import choco.Problem;
import choco.Var;
import choco.integer.IntConstraint;
import choco.integer.IntDomainVar;
import choco.integer.IntExp;
import choco.integer.IntVar;
import choco.real.RealExp;
import choco.real.RealVar;
import choco.real.constraint.RealConstraint;

/**
 * Numeric constraint solver. Solves real and integer path
 * constraints. Generates concrete values for each input
 * variable if the path condition has at least one solution
 * @author svetoslavganov
 */
public class BaradNumericConstraintSolver implements NumericConstraintSolver {
	private double min_double;
	private double max_double;
	private double delta;
	public int min_int;
	public int max_int;
	private Logger log;
	private HashMap<String, Descriptor> inputVariables;
	private List<TestInput> concretizedInt;
	private List<TestInput> concretizedReal;
	private HashMap<String, Var> realProblemVariables;
	private HashMap<String, Var> intProblemVariables;
	private Problem integerProblem;
	private boolean integerProblemSolved;
	public Problem realProblem;
	private boolean realProblemSolved;
	private int doublesDecimalPlacesRoundValue;
	
	public BaradNumericConstraintSolver() {
		log = Logger.getLogger(this.getClass());
		inputVariables = new HashMap<String, Descriptor>();
		concretizedInt = new LinkedList<TestInput>();
		concretizedReal = new LinkedList<TestInput>();
		realProblemVariables = new HashMap<String, Var>();
		intProblemVariables = new HashMap<String, Var>();
		min_double = -100;
		max_double = 100;
		max_int = 100;
		min_int = -100;
		doublesDecimalPlacesRoundValue = 5;
		try {
			min_double = Float.parseFloat(Util.getProperties().getProperty("doubles.min", "-100"));
			max_double = Float.parseFloat(Util.getProperties().getProperty("doubles.max", "100"));
			delta = Double.parseDouble(Util.getProperties().getProperty("doubles.delta", "0.1"));
			max_int  = Integer.parseInt(Util.getProperties().getProperty("integers.max", "100"));
			min_int  = Integer.parseInt(Util.getProperties().getProperty("integers.min", "-100"));
			doublesDecimalPlacesRoundValue = Integer.parseInt(Util.getProperties().getProperty("doubles.decimal.places.round.value", "5"));
		} catch (NumberFormatException nfe) {
			/*ignore*/
		}
	}

	/**
	 * Add an input variable for which a concrete value should
	 * be generated 
	 */
	public void addInputVaribale(String name, Descriptor descriptor) {
		inputVariables.put(name, descriptor);
		if (DEBUG && VERBOSE) {
			log.info("Input variable added: " + name);
		}
	}

	/**
	 * Add an integer constraint to the integer problem
	 */
	public void addIntCosntraint(IntegerPathConstraint constraint) {
		try {
			integerProblem.post(getIntConstraint(constraint));
		} catch (UnsupportedOperationByChoco uobc) {
			log.error("Unsupported operation by Choco" + uobc, uobc);
		}
	}

	/**
	 * Add a real constraint to the real problem
	 */
	public void addRealCosntraint(FloatPathConstraint constraint) {
		try {
			realProblem.post(getRealConstraint(constraint));
		} catch (UnsupportedOperationByChoco uobc) {
			log.error("Unsupported operation by Choco" + uobc, uobc);
		}
	}

	/**
	 * Generate concrete values for each integer variable
	 */
	public void concretizeInt() {
		if (integerProblemSolved) {
			for (int i = 0; i < integerProblem.getNbIntVars(); i++) {
				IntDomainVar var = (IntDomainVar)integerProblem.getIntVar(i);
				String chocoVariableName = var.toString();
				int endIndex = chocoVariableName.lastIndexOf(':');
				if (endIndex > -1) {
					String variableName = chocoVariableName.substring(0, endIndex);
					Descriptor descriptor = null;
					if ((descriptor = inputVariables.get(variableName)) != null) {
						TestInput testInput = new TestInput();
						testInput.setValue(String.valueOf(var.getVal()));
						testInput.setDescriptor(descriptor);
						concretizedInt.add(testInput);
					}
				}
			}
		}
	} 
	
	/**
	 * Generate concrete values for each real variable
	 */
	public void concretizeReal() {
		if (realProblemSolved) {
			for (int i = 0; i < realProblem.getNbRealVars(); i++) {
				RealVar var = (RealVar)realProblem.getRealVar(i);
				String chocoVariableName = var.toString();
				int endIndex = chocoVariableName.indexOf('[');
				if (endIndex > -1) {
					String variableName = chocoVariableName.substring(0, endIndex);
					Descriptor descriptor = null;
					if ((descriptor = inputVariables.get(variableName)) != null) {
						TestInput testInput = new TestInput();
						//we get the supremum since we need any valid value
						double concreteValue = round(var.getValue().getSup());
						testInput.setValue(String.valueOf(String.valueOf(concreteValue)));
						testInput.setDescriptor(descriptor);
						concretizedReal.add(testInput);
					}
				}
			}
		}
	}
	
	/**
	 * Rounds a double input value generated by the solver. This
	 * is required to ensure that in case the genereted value is
	 * compared to a concrete one (For example, x == 5f) the 
	 * constraint would be satisfied 
	 * @param d The value to be rounded
	 * @return The rounded value
	 */
	private double round(double d){
		BigDecimal bd = new BigDecimal(Double.toString(d));
		bd = bd.setScale(doublesDecimalPlacesRoundValue, BigDecimal.ROUND_HALF_UP);
		return bd.doubleValue();
	}
	 
	/**
	 * Create a new integer problem
	 */
	public void createIntProblem() {
		integerProblem = new Problem();
		concretizedInt.clear();
		intProblemVariables.clear();
	}
	
	/**
	 * Create a new real problem
	 */
	public void createRealProblem() {
		realProblem = new Problem();
		concretizedReal.clear();
		realProblemVariables.clear();
	}
	
	/**
	 * Returns the generated concrete values (if any)
	 * @return Map with the generated concrete values if any, null otherwise
	 */
	public List<TestInput> getConcretizedIntValues() {
		return concretizedInt;
	}
	
	/**
	 * Returns the generated concrete values (if any)
	 * @return Map with the generated concrete values if any, null otherwise
	 */
	public List<TestInput> getConcretizedRealValues() {
		return concretizedReal;
	}
	
	/**
	 * Solve the integer problem
	 */
	public void solveIntProblem() {
		try {
			integerProblem.propagate();
		} catch (Exception e) {
			
		}
		integerProblemSolved = integerProblem.solve();
	}
	
	/**
	 * Solve the real problem
	 */
	public void solveRealProblem() {
		try {
			realProblem.propagate();
		} catch (Exception e) {
			
		}
		realProblemSolved = realProblem.solve();
	}
	

	/**
	 * Prints the concretized solution
	 */
	public void printConcretizedSolution() {
		if (concretizedInt.size() > 0) {
			log.info("Concrete integer values:");
			for (TestInput ti: concretizedInt) {
				log.info(ti.toString());
			}
		}
		if (concretizedReal.size() > 0) {
			log.info("Concrete real values:");
			for (TestInput ti: concretizedReal) {
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
	
	/**
	 * Generates the appropriate real path constraint from its symbolic
	 * representation 
	 * @param floatPathConstraint The symbolic path constraint
	 * @return Real Choco path constraint 
	 * @throws UnsupportedOperationByChoco If any of the constraints 
	 * contains unsupported by Choco operation 
	 */
	private RealConstraint getRealConstraint(FloatPathConstraint floatPathConstraint) throws UnsupportedOperationByChoco {
		if (floatPathConstraint instanceof IF_FCMPEQ) {
			return getRealConstraint((IF_FCMPEQ)floatPathConstraint);
		} else if (floatPathConstraint instanceof IF_FCMPGE) {
			return getRealConstraint((IF_FCMPGE)floatPathConstraint);
		} else if (floatPathConstraint instanceof IF_FCMPGT) {
			return getRealConstraint((IF_FCMPGT)floatPathConstraint);
		} else if (floatPathConstraint instanceof IF_FCMPLE) {
			return getRealConstraint((IF_FCMPLE)floatPathConstraint);
		} else if (floatPathConstraint instanceof IF_FCMPLT) {
			return getRealConstraint((IF_FCMPLT)floatPathConstraint);
		} else if (floatPathConstraint instanceof IF_FCMPNE) {
			return getRealConstraint((IF_FCMPNE)floatPathConstraint);
		} else {
			throw new UnsupportedOperationByChoco("Unrecognized symbolic expression. Most probably this is a bug " + floatPathConstraint.toString());
		}
	}
	
	/**
	 * Returns Choco real "==" constraint
	 * @param if_icmpeq Symbolic IF_FCMPEQ
	 * @return New Choco real constraint instance
	 */
	private RealConstraint getRealConstraint(IF_FCMPEQ if_fcmpeq) {
		return (RealConstraint)realProblem.eq(getRealExp(if_fcmpeq.getOp1()), getRealExp(if_fcmpeq.getOp2()));
	}
	
	/**
	 * Returns Choco real ">=" constraint
	 * @param if_icmpge Symbolic IF_FCMPGE
	 * @return New Choco real constraint instance
	 */
	private RealConstraint getRealConstraint(IF_FCMPGE if_fcmpge) {
		return (RealConstraint)realProblem.geq(getRealExp(if_fcmpge.getOp1()), getRealExp(if_fcmpge.getOp2()));
	}
	
	/**
	 * Returns Choco real ">" constraint
	 * @param if_icmpgt Symbolic IF_FCMPGT
	 * @return New Choco real constraint instance
	 * NOTE: Choco does not support x > y real operation and
	 * it is modeled as x + delta < = y
	 */
	private RealConstraint getRealConstraint(IF_FCMPGT if_fcmpgt) {
		RealVar deltaVar = realProblem.makeRealVar(delta, delta);
		RealExp op2PlusDelta = realProblem.plus(getRealExp(if_fcmpgt.getOp2()), deltaVar);
		return (RealConstraint)realProblem.geq(getRealExp(if_fcmpgt.getOp1()), op2PlusDelta);
	}
	
	/**
	 * Returns Choco real "<=" constraint
	 * @param if_icmple Symbolic IF_FCMPLE
	 * @return New Choco real constraint instance
	 */
	private RealConstraint getRealConstraint(IF_FCMPLE if_fcmple) {
		return (RealConstraint)realProblem.leq(getRealExp(if_fcmple.getOp1()), getRealExp(if_fcmple.getOp2()));
	}
	
	/**
	 * Returns Choco real "<" constraint
	 * @param if_icmplt Symbolic IF_FCMPLT
	 * @return New Choco real constraint instance
	 * NOTE: Choco does not support x < y real operation and
	 * it is modeled as x + delta < = y
	 */
	private RealConstraint getRealConstraint(IF_FCMPLT if_fcmplt) {
		RealVar deltaVar = realProblem.makeRealVar(delta, delta);
		RealExp op1PlusDelta = realProblem.plus(getRealExp(if_fcmplt.getOp1()), deltaVar);
		return (RealConstraint)realProblem.leq(op1PlusDelta, getRealExp(if_fcmplt.getOp2()));
	}
	
	/**
	 * Returns Choco real "!=" constraint
	 * @param if_icmplt Symbolic IF_FCMPLT
	 * @return New Choco real constraint instance
	 * FIXME: THIS IS A TERRIBLE ASSUMPTION
	 * Choco does not support x != y real operation and
	 * if I attempt to represent the consraint as
	 * x + delta <= y || x >= y + delta a get Choco message
	 * that there is a bug in Choco. Just to keep going I 
	 * used the very bad representation as x + delta <= y 
	 */
	private RealConstraint getRealConstraint(IF_FCMPNE if_fcmpne) {
		RealVar deltaVar = realProblem.makeRealVar(delta, delta);
		//RealExp op1PlusDelta = problem.plus(op1.getRealExp(problem), delta);
		RealExp op2PlusDelta = realProblem.plus(getRealExp(if_fcmpne.getOp2()), deltaVar);
		RealConstraint c1 = (RealConstraint)realProblem.geq(getRealExp(if_fcmpne.getOp1()), op2PlusDelta);
		//RealConstraint c2 = (RealConstraint)problem.leq(op1PlusDelta, op2.getRealExp(problem));
		return c1;// (RealConstraint)problem.or(c1, c2);
	}
	
	/**
	 * Get Choco representation of a real expression
	 * @param symbolicFloat The symbolic represenation of the
	 * real expression
	 * @return Choco real expression
	 */
	private RealExp getRealExp(FloatInterface symbolicFloat) {
		if (symbolicFloat instanceof FCONST) {
			return getRealExp((FCONST)symbolicFloat);
		} else if (symbolicFloat instanceof FVAR) {
			return getRealExp((FVAR)symbolicFloat);
		} else if (symbolicFloat instanceof FADD) {
			return getRealExp((FADD)symbolicFloat);
		} else if (symbolicFloat instanceof FSUB) {
			return getRealExp((FSUB)symbolicFloat);
		} else if (symbolicFloat instanceof FMUL) {
			return getRealExp((FMUL)symbolicFloat);
		} else/*(symbolicFloat instanceof FDIV)*/ {
			return getRealExp((FDIV)symbolicFloat);
		}
	}
	
	/**
	 * Returns a new Choco real expression that represents addition
	 * @param fadd Symbolic float addition
	 * @return New Choco plus expression
	 */
	private RealExp getRealExp(FADD fadd) {
		return realProblem.plus(getRealExp(fadd.getOp1()), getRealExp(fadd.getOp2()));
	}
	
	/**
	 * Returns a new Choco real expression that represents substraction
	 * @param fsub Symbolic float substraction
	 * @return New Choco substraction expression
	 */
	private RealExp getRealExp(FSUB fsub) {
		return realProblem.minus(getRealExp(fsub.getOp1()), getRealExp(fsub.getOp2()));
	}
	
	/**
	 * Returns a new Choco real expression that represents multiplication
	 * @param fmul Symbolic float multiplication
	 * @return New Choco multiplication expression
	 */
	private RealExp getRealExp(FMUL fmul) {
		return realProblem.mult(getRealExp(fmul.getOp1()), getRealExp(fmul.getOp2()));
	}
	
	/**
	 * Returns a new Choco real expression that represents division
	 * @param fdiv Symbolic float division
	 * @return New Choco division expression (X / Y <=> X * Y^-1)
	 */
	private RealExp getRealExp(FDIV fdiv) {
		return realProblem.mult(getRealExp(fdiv.getOp1()), realProblem.power(getRealExp(fdiv.getOp2()), -1));
	}
	
	/**
	 * Returns new Choco real constant
	 * @param iconst Symbolic float constant
	 * @return New Choco integer constant
	 */
	private RealExp getRealExp(FCONST fconst) {
		return realProblem.makeRealVar(fconst.getId(), fconst.getValue(), fconst.getValue());
	}
	
	/**
	 * Returns a new Choco integer expression that represents a variable
	 * @param fvar Symbolic real variable
	 * @return New Choco real variable
	 */
	private RealExp getRealExp(FVAR fvar) {
		RealVar realVar = (RealVar)realProblemVariables.get(fvar.getId());
		if (realVar == null) {
			realVar = realProblem.makeRealVar(fvar.getId(), min_double, max_double);
			realProblemVariables.put(fvar.getId(), realVar);
		}
		return realVar;
	}
	
	/**
	 * Generates the appropriate integer path constraint from its symbolic
	 * representation 
	 * @param integerPathConstraint The symbolic path constraint
	 * @return Real Choco path constraint 
	 * @throws UnsupportedOperationByChoco If any of the constraints 
	 * contains unsupported by Choco operation 
	 */
	private IntConstraint getIntConstraint(IntegerPathConstraint integerPathConstraint) throws UnsupportedOperationByChoco {
		if (integerPathConstraint instanceof IF_ICMPEQ) {
			return getIntConstraint((IF_ICMPEQ)integerPathConstraint);
		} else if (integerPathConstraint instanceof IF_ICMPGE) {
			return getIntConstraint((IF_ICMPGE)integerPathConstraint);
		} else if (integerPathConstraint instanceof IF_ICMPGT) {
			return getIntConstraint((IF_ICMPGT)integerPathConstraint);
		} else if (integerPathConstraint instanceof IF_ICMPLE) {
			return getIntConstraint((IF_ICMPLE)integerPathConstraint);
		} else if (integerPathConstraint instanceof IF_ICMPLT) {
			return getIntConstraint((IF_ICMPLT)integerPathConstraint);
		} else if (integerPathConstraint instanceof IF_ICMPNE) {
			return getIntConstraint((IF_ICMPNE)integerPathConstraint);
		} else if (integerPathConstraint instanceof IFEQ) {
			return getIntConstraint((IFEQ)integerPathConstraint);
		} else if (integerPathConstraint instanceof IFGE) {
			return getIntConstraint((IFGE)integerPathConstraint);
		} else if (integerPathConstraint instanceof IFGT) {
			return getIntConstraint((IFGT)integerPathConstraint);
		} else if (integerPathConstraint instanceof IFLE) {
			return getIntConstraint((IFLE)integerPathConstraint);
		} else if (integerPathConstraint instanceof IFLT) {
			return getIntConstraint((IFLT)integerPathConstraint);
		} else if (integerPathConstraint instanceof IFNE) {
			return getIntConstraint((IFNE)integerPathConstraint);
		} else {
			throw new UnsupportedOperationByChoco("Unrecognized symbolic expression. Most probably this is a bug " + integerPathConstraint.toString());
		}
	}
	
	/**
	 * Returns Choco integer "==" constraint
	 * @param if_icmpeq Symbolic IF_ICMPEQ
	 * @return New Choco integer constraint instance
	 */
	private IntConstraint getIntConstraint(IF_ICMPEQ if_icmpeq) throws UnsupportedOperationByChoco {
		Constraint constraint = null;
		try {
			constraint = integerProblem.eq(getIntExp(if_icmpeq.getOp1()), getIntExp(if_icmpeq.getOp2()));
		} catch (UnsupportedOperationByChoco uobc) {
			throw uobc;
		}
		return (IntConstraint) constraint; 
	}
	
    /**
	 * Returns Choco integer ">=" constraint
	 * @param if_icmpge Symbolic IF_ICMPGE
	 * @return New Choco integer constraint instance
	 */
	private IntConstraint getIntConstraint(IF_ICMPGE if_icmpge) throws UnsupportedOperationByChoco {
		Constraint constraint = null;
		try {
			constraint = integerProblem.geq(getIntExp(if_icmpge.getOp1()), getIntExp(if_icmpge.getOp2()));
		} catch (UnsupportedOperationByChoco uobc) {
			throw uobc;
		}
		return (IntConstraint) constraint; 
	}
	
	/**
	 * Returns Choco integer ">" constraint
	 * @param if_icmpgt Symbolic IF_ICMPGT
	 * @return New Choco integer constraint instance
	 */
	private IntConstraint getIntConstraint(IF_ICMPGT if_icmpgt) throws UnsupportedOperationByChoco {
		Constraint constraint = null;
		try {
			constraint = integerProblem.gt(getIntExp(if_icmpgt.getOp1()), getIntExp(if_icmpgt.getOp2()));
		} catch (UnsupportedOperationByChoco uobc) {
			throw uobc;
		}
		return (IntConstraint) constraint; 
	}
	
    /**
	 * Returns Choco integer "<=" constraint
	 * @param if_icmple Symbolic IF_ICMPLE
	 * @return New Choco integer constraint instance
	 */
	private IntConstraint getIntConstraint(IF_ICMPLE if_icmple) throws UnsupportedOperationByChoco {
		Constraint constraint = null;
		try {
			constraint = integerProblem.leq(getIntExp(if_icmple.getOp1()), getIntExp(if_icmple.getOp2()));
		} catch (UnsupportedOperationByChoco uobc) {
			throw uobc;
		}
		return (IntConstraint) constraint; 
	}
	
	/**
	 * Returns Choco integer "<" constraint
	 * @param if_icmplt Symbolic IF_ICMPLT
	 * @return New Choco integer constraint instance
	 */
	private IntConstraint getIntConstraint(IF_ICMPLT if_icmplt) throws UnsupportedOperationByChoco {
		Constraint constraint = null;
		try {
			constraint = integerProblem.lt(getIntExp(if_icmplt.getOp1()), getIntExp(if_icmplt.getOp2()));
		} catch (UnsupportedOperationByChoco uobc) {
			throw uobc;
		}
		return (IntConstraint) constraint; 
	}
	
	/**
	 * Returns Choco integer "!=" constraint
	 * @param if_icmpne Symbolic IF_ICMPNE
	 * @return New Choco integer constraint instance
	 */
	private IntConstraint getIntConstraint(IF_ICMPNE if_icmpne) throws UnsupportedOperationByChoco {
		Constraint constraint = null;
		try {
			constraint = integerProblem.neq(getIntExp(if_icmpne.getOp1()), getIntExp(if_icmpne.getOp2()));
		} catch (UnsupportedOperationByChoco uobc) {
			throw uobc;
		}
		return (IntConstraint) constraint; 
	}
	
	/**
	 * Returns Choco integer "= 0" constraint
	 * @param ifeq Symbolic IFEQ
	 * @return New Choco integer constraint instance
	 */
	private IntConstraint getIntConstraint(IFEQ ifeq) throws UnsupportedOperationByChoco {
		Constraint constraint = null;
		try {
			constraint = integerProblem.eq(getIntExp(ifeq.getOp1()), 0);
		} catch (UnsupportedOperationByChoco uobc) {
			throw uobc;
		}
		return (IntConstraint) constraint; 
	}

	/**
	 * Returns Choco integer ">= 0" constraint
	 * @param ifge Symbolic IFGE
	 * @return New Choco integer constraint instance
	 */
	private IntConstraint getIntConstraint(IFGE ifge) throws UnsupportedOperationByChoco {
		Constraint constraint = null;
		try {
			constraint = integerProblem.geq(getIntExp(ifge.getOp1()), 0);
		} catch (UnsupportedOperationByChoco uobc) {
			throw uobc;
		}
		return (IntConstraint) constraint; 
	}
	
	/**
	 * Returns Choco integer "> 0" constraint
	 * @param ifgt Symbolic IFGT
	 * @return New Choco integer constraint instance
	 */
	private IntConstraint getIntConstraint(IFGT ifgt) throws UnsupportedOperationByChoco {
		Constraint constraint = null;
		try {
			constraint = integerProblem.gt(getIntExp(ifgt.getOp1()), 0);
		} catch (UnsupportedOperationByChoco uobc) {
			throw uobc;
		}
		return (IntConstraint) constraint; 
	}
	
	/**
	 * Returns Choco integer "<= 0" constraint
	 * @param ifle Symbolic IFLE
	 * @return New Choco integer constraint instance
	 */
	private IntConstraint getIntConstraint(IFLE ifle) throws UnsupportedOperationByChoco {
		Constraint constraint = null;
		try {
			constraint = integerProblem.leq(getIntExp(ifle.getOp1()), 0);
		} catch (UnsupportedOperationByChoco uobc) {
			throw uobc;
		}
		return (IntConstraint) constraint; 
	}
	
	/**
	 * Returns Choco integer "< 0" constraint
	 * @param iflt Symbolic IFLT
	 * @return New Choco integer constraint instance
	 */
	private IntConstraint getIntConstraint(IFLT iflt) throws UnsupportedOperationByChoco {
		Constraint constraint = null;
		try {
			constraint = integerProblem.lt(getIntExp(iflt.getOp1()), 0);
		} catch (UnsupportedOperationByChoco uobc) {
			throw uobc;
		}
		return (IntConstraint) constraint; 
	}
	
	/**
	 * Returns Choco integer "!= 0" constraint
	 * @param ifne Symbolic IFNE
	 * @return New Choco integer constraint instance
	 */
	private IntConstraint getIntConstraint(IFNE ifne) throws UnsupportedOperationByChoco {
		Constraint constraint = null;
		try {
			constraint = integerProblem.neq(getIntExp(ifne.getOp1()), 0);
		} catch (UnsupportedOperationByChoco uobc) {
			throw uobc;
		}
		return (IntConstraint) constraint; 
	}
	
	/**
	 * Get Choco representation of an integer expression
	 * @param symbolicInteger The symbolic represenation of the
	 * integer expression
	 * @return Choco integer expression
	 */
	private IntExp getIntExp(IntegerInterface symbolicInteger) throws UnsupportedOperationByChoco {
		if (symbolicInteger instanceof ICONST) {
			return getIntExp((ICONST)symbolicInteger);
		} else if (symbolicInteger instanceof IVAR) {
			return getIntExp((IVAR)symbolicInteger);
		} else if (symbolicInteger instanceof IADD) {
			IntExp intExp = null;
			try {
				intExp = getIntExp((IADD)symbolicInteger);
			} catch (UnsupportedOperationByChoco uobc) {
				throw uobc;
			}
			return intExp;
		} else if (symbolicInteger instanceof ISUB) {
			return getIntExp((ISUB)symbolicInteger);
		} else if (symbolicInteger instanceof IMUL) {
			return getIntExp((IMUL)symbolicInteger);
		} else if (symbolicInteger instanceof IDIV) {
			return getIntExp((IDIV)symbolicInteger);
		} else {
			throw new UnsupportedOperationByChoco("Unrecognized symbolic expression. Most probably this is a bug " + symbolicInteger.toString());
		}
	}
	
	/**
	 * Returns a new Choco integer expression that represents addition
	 * @param iadd Symbolic integer addition
	 * @return New Choco plus expression
	 * @throws rethrows UnsupportedOperationByChoco if such is caught
	 */
	private IntExp getIntExp(IADD iadd) throws UnsupportedOperationByChoco {
		IntExp intExp = null;
		try {
			intExp = integerProblem.plus(getIntExp(iadd.getOp1()), getIntExp(iadd.getOp2()));
		} catch (UnsupportedOperationByChoco uobc) {
			throw uobc;
		}
		return intExp;
	}
	
   /**
	* Returns a new Choco integer expression that represents substraction
	* @param isub Symbolic integer substraction
	* @return New Choco plus expression
	*/
	private IntExp getIntExp(ISUB isub) throws UnsupportedOperationByChoco {
		IntExp intExp = null;
		try {
			intExp = integerProblem.minus(getIntExp(isub.getOp1()), getIntExp(isub.getOp2()));
		} catch (UnsupportedOperationByChoco uobc) {
			throw uobc;
		}
		return intExp;
	}
	
	/**
	* Returns a new Choco integer expression that represents division
	* @param idiv Symbolic integer division 
	* @return Throws new UnsupportedOperationByChoco since this operation is not
	* supported by Choco neither power opertion in order to model division
	* @throws UnsupportedOperationByChoco always
	*/
	private IntExp getIntExp(IDIV idiv) throws UnsupportedOperationByChoco {
		throw new UnsupportedOperationByChoco("Neither integer division nor oprations to model it are supported by Choco");
	}
	
	/**
	 * Returns a new Choco integer expression that represents multiplication
	 * @param imul Symbolic integer multiplication
	 * @return New Choco multiplication expression
	 * @throws UnsupportedOperationByChoco if the two operands are integer constants
	 * and rethrows it if such is caught
	 */
	private IntExp getIntExp(IMUL imul) throws UnsupportedOperationByChoco {
		IntExp intExp = null;
		try {
			if (imul.getOp1() instanceof ICONST && !(imul.getOp2() instanceof ICONST)) {
				int value = ((ICONST)imul.getOp1()).getValue();
				intExp =  getIntExp(imul.getOp2());
				intExp = integerProblem.mult(value,intExp);
			} else if (!(imul.getOp1() instanceof ICONST) && imul.getOp2() instanceof ICONST){ 
				intExp = integerProblem.mult(((ICONST)imul.getOp2()).getValue(), getIntExp(imul.getOp1()));
			} else {
				throw new UnsupportedOperationByChoco("Integer multiplication of two constants is not supported by Choco");
			}
		} catch (UnsupportedOperationByChoco uobc) {
			throw uobc;
		}
		return intExp;
	}
	
	/**
	 * Returns new Choco integer constant
	 * @param iconst Symbolic integer constant
	 * @return New Choco integer constant
	 */
	private IntExp getIntExp(ICONST iconst) {
		return integerProblem.makeConstantIntVar(iconst.getId(), iconst.getValue());
	}
	
	/**
	 * Returns a new Choco integer expression that represents variable
	 * @param ivar Symbolic integer vsriable
	 * @return New Choco integer variable
	 */
	private IntExp getIntExp(IVAR ivar) {
		IntVar intVar = (IntVar)realProblemVariables.get(ivar.getId());
		if (intVar == null) {
			intVar = integerProblem.makeBoundIntVar(ivar.getId(), min_int, max_int);
			realProblemVariables.put(ivar.getId(), intVar);
		}
		return intVar;
	}
}
