package barad.symboliclibrary.path.floats;

import java.io.Serializable;

import choco.Problem;
import choco.real.constraint.RealConstraint;

import barad.symboliclibrary.floats.FloatInterface;

/**
 * Class that represents the symbolic float path constraint: less than
 * @author svetoslavganov
 */
public class IF_FCMPLT extends FloatPathConstraint implements Serializable {
	private static final long serialVersionUID = 1;

	public IF_FCMPLT(FloatInterface op1, FloatInterface op2) {
		super(op1, op2, "<", "IF_FCMPLT");
	}
	
	/**
	 * Returns the complementary float path constraint: greater than or equal
	 * @return New instance of the complementary path constraint
	 */
	@Override
	public FloatPathConstraint inverse() {
		return new IF_FCMPGE(super.getOp1(), super.getOp2());
	}
	
	/**
	 * Clones this float path constraint
	 * @return New clone of the path constraint
	 */
	@Override
	public Object clone() {
		IF_FCMPLT if_fcmplt = new IF_FCMPLT((FloatInterface)op1.clone(), (FloatInterface)op2.clone());
		if_fcmplt.setName(this.getName());
		return if_fcmplt;
	}
	
	/**
	 * Returns Choco real constraint that represents 
	 * this real constriant
	 * @param problem Choco Problem instance
	 * @return New Choco real constraint instance
	 */
	public RealConstraint getRealConstraint(Problem problem) {
		return (RealConstraint)problem.not(problem.geq(op1.getRealExp(problem), op2.getRealExp(problem)));
	}
}
