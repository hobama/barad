package barad.symboliclibrary.path.integers;

import java.io.Serializable;

import choco.Constraint;
import choco.Problem;
import choco.integer.IntConstraint;

import barad.symboliclibrary.integers.ICONST;
import barad.symboliclibrary.integers.IntegerInterface;
import barad.symboliclibrary.integers.UnsupportedOperationByChoco;

/**
 * Class that represents the symbolic integer path constraint: less than or equal to 0
 * @author svetoslavganov
 */
public class IFLE extends IntegerPathConstraint implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public IFLE(IntegerInterface op1) {
		super(op1, new ICONST(0), "<=", "IFFLE");
	}
	
	/**
	 * Returns the complementary integer path constraint: greater than 0
	 * @return New instance of the complementary path constraint
	 */
	@Override
	public IntegerPathConstraint inverse() {
		IntegerPathConstraint inversed = this;
		if (inverseCounter < 2) {
			inverseCounter++;
		    inversed = new IFGT(super.getOp1());
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
		IFLE ifle = new IFLE((IntegerInterface)op1.clone());
		ifle.name = name;
		return ifle; 
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
			constraint = problem.leq(op1.getIntExp(problem), 0);
		} catch (UnsupportedOperationByChoco uobc) {
			throw uobc;
		}
		return (IntConstraint) constraint; 
	}
}
