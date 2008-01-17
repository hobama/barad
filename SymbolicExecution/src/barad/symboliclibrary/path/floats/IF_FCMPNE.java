package barad.symboliclibrary.path.floats;

import java.io.Serializable;

import choco.Problem;
import choco.real.RealExp;
import choco.real.RealVar;
import choco.real.constraint.RealConstraint;

import barad.symboliclibrary.floats.FloatInterface;
import barad.util.Util;

/**
 * Class that represents the symbolic float path constraint: not equal
 * @author svetoslavganov
 */
public class IF_FCMPNE extends FloatPathConstraint implements Serializable {
	private static final long serialVersionUID = 1;

	public IF_FCMPNE(FloatInterface op1, FloatInterface op2) {
		super(op1, op2, "!=", "IF_FCMPNE");
	}
	
	/**
	 * Returns the complementary float path constraint: equal
	 * @return New instance of the complementary path constraint
	 */
	@Override
	public FloatPathConstraint inverse() {
		return new IF_FCMPEQ(super.getOp1(), super.getOp2());
	}
	
	/**
	 * Clones this float path constraint
	 * @return New clone of the path constraint
	 */
	@Override
	public Object clone() {
		IF_FCMPNE if_fcmpne = new IF_FCMPNE((FloatInterface)op1.clone(), (FloatInterface)op2.clone());
		if_fcmpne.name = name;
		return if_fcmpne;
	}
	
	/**
	 * Returns Choco real constraint that represents 
	 * this real constriant
	 * @param problem Choco Problem instance
	 * @return New Choco real constraint instance
	 * FIXME: THIS IS A TERRIBLE ASSUMPTION
	 * Choco does not support x != y real operation and
	 * if I attempt to represent the consraint as
	 * x + delta <= y || x >= y + delta a get Choco message
	 * that there is a bug in Choco. Just to keep going I 
	 * used the very bad representation as x + delta <= y 
	 */
	public RealConstraint getRealConstraint(Problem problem) {
		double value = Double.parseDouble(Util.getProperties().getProperty("doubles.delta", "0.1"));
		RealVar delta = problem.makeRealVar(value, value);
		//RealExp op1PlusDelta = problem.plus(op1.getRealExp(problem), delta);
		RealExp op2PlusDelta = problem.plus(op2.getRealExp(problem), delta);
		RealConstraint c1 = (RealConstraint)problem.geq(op1.getRealExp(problem), op2PlusDelta);
		//RealConstraint c2 = (RealConstraint)problem.leq(op1PlusDelta, op2.getRealExp(problem));
		return c1;// (RealConstraint)problem.or(c1, c2);
	}
}
