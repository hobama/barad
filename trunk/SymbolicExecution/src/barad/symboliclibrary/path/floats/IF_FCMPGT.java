package barad.symboliclibrary.path.floats;

import java.io.Serializable;

import choco.Problem;
import choco.real.RealExp;
import choco.real.RealVar;
import choco.real.constraint.RealConstraint;

import barad.symboliclibrary.floats.FloatInterface;
import barad.util.Util;

/**
 * Class that represents the symbolic float path constraint: greter than
 * @author svetoslavganov
 */
public class IF_FCMPGT extends FloatPathConstraint implements Serializable {
	private static final long serialVersionUID = 1;
	
	public IF_FCMPGT(FloatInterface op1, FloatInterface op2) {
		super(op1, op2, ">", "IF_FCMPGT");
	}
	
	/**
	 * Returns the complementary float path constraint: less than or equal
	 * @return New instance of the complementary path constraint
	 */
	@Override
	public FloatPathConstraint inverse() {
		return new IF_FCMPLE(super.getOp1(), super.getOp2());
	}
	
	/**
	 * Clones this float path constraint
	 * @return New clone of the path constraint
	 */
	@Override
	public Object clone() {
		IF_FCMPGT if_fcmpgt = new IF_FCMPGT((FloatInterface)op1.clone(), (FloatInterface)op2.clone());
		if_fcmpgt.name = name;
		return if_fcmpgt; 
	}
	
	/**
	 * Returns Choco real constraint that represents 
	 * this real constriant
	 * @param problem Choco Problem instance
	 * @return New Choco real constraint instance
	 * NOTE: Choco does not support x > y real operation and
	 * it is modeled as x + delta < = y
	 */
	public RealConstraint getRealConstraint(Problem problem) {
		double value = Double.parseDouble(Util.getProperties().getProperty("doubles.delta", "0.1"));
		RealVar delta = problem.makeRealVar(value, value);
		RealExp op2PlusDelta = problem.plus(op2.getRealExp(problem), delta);
		return (RealConstraint)problem.geq(op1.getRealExp(problem), op2PlusDelta);
	}
}
