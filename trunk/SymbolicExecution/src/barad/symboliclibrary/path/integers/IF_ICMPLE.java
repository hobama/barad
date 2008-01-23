package barad.symboliclibrary.path.integers;

import java.io.Serializable;

import choco.Constraint;
import choco.Problem;
import choco.integer.IntConstraint;

import barad.symboliclibrary.integers.IntegerInterface;
import barad.symboliclibrary.integers.UnsupportedOperationByChoco;

/**
 * Class that represents the symbolic integer path constraint: less than or equal
 * @author svetoslavganov
 */
public class IF_ICMPLE extends IntegerPathConstraint implements Serializable {
	private static final long serialVersionUID = 1;

	public IF_ICMPLE(IntegerInterface op1, IntegerInterface op2) {
		super(op1, op2, "<=", "IF_ICMPLE");
	}
	
	/**
	 * Returns the complementary integer path constraint: greater than
	 * @return New instance of the complementary path constraint
	 */
	@Override
	public IntegerPathConstraint inverse() {
		IntegerPathConstraint inversed = this;
		if (inverseCounter < 2) {
			inverseCounter++;
		    inversed = new IF_ICMPGT(super.getOp1(), super.getOp2());
		    inversed.inverseCounter = inverseCounter;
		}
		return inversed; 
	}
	
	/**
	 * Clones this integer path constraint
	 * @return New clone of the path constraint
	 */
	@Override
	public Object clone() {
		IF_ICMPLE if_icmple = new IF_ICMPLE((IntegerInterface)op1.clone(), (IntegerInterface)op2.clone());
		if_icmple.name = name;
		return if_icmple;
	}
	
	/**
	 * Returns Choco integer constraint that represents 
	 * this integer constriant
	 * @param problem Choco Problem instance
	 * @return New Choco integer constraint instance
	 */
	public IntConstraint getIntConstraint(Problem problem) throws UnsupportedOperationByChoco {
		Constraint constraint = null;
		try {
			constraint = problem.leq(op1.getIntExp(problem), op2.getIntExp(problem));
		} catch (UnsupportedOperationByChoco uobc) {
			throw uobc;
		}
		return (IntConstraint) constraint; 
	}
}
