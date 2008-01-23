package barad.symboliclibrary.path.integers;

import java.io.Serializable;

import choco.Constraint;
import choco.Problem;
import choco.integer.IntConstraint;

import barad.symboliclibrary.integers.ICONST;
import barad.symboliclibrary.integers.IntegerInterface;
import barad.symboliclibrary.integers.UnsupportedOperationByChoco;

/**
 * Class that represents the symbolic integer path constraint: less than 0
 * @author svetoslavganov
 */
public class IFLT extends IntegerPathConstraint implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public IFLT(IntegerInterface op1) {
		super(op1, new ICONST(0), "<", "IFFLT");
	}
	
	/**
	 * Returns the complementary integer path constraint: greater than or equal to 0
	 * @return New instance of the complementary path constraint
	 */
	@Override
	public IntegerPathConstraint inverse() {
		IntegerPathConstraint inversed = this;
		if (inverseCounter < 2) {
			inverseCounter++;
		    inversed = new IFGE(super.getOp1());
		    inversed.inverseCounter = inverseCounter;
		}
		return inversed; 
	}
	
	/**
	 * Clones this path integer constraint
	 * @return New clone of the path constraint
	 */
	@Override
	public Object clone() {
		IFLT iflt = new IFLT((IntegerInterface)op1.clone());
		iflt.name = name;
		return iflt; 
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
			constraint = problem.lt(op1.getIntExp(problem), 0);
		} catch (UnsupportedOperationByChoco uobc) {
			throw uobc;
		}
		return (IntConstraint) constraint; 
	}
}
