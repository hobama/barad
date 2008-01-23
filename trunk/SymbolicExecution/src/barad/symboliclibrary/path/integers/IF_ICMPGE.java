package barad.symboliclibrary.path.integers;

import java.io.Serializable;

import barad.symboliclibrary.integers.IntegerInterface;
import barad.symboliclibrary.integers.UnsupportedOperationByChoco;
import choco.Constraint;
import choco.Problem;
import choco.integer.IntConstraint;

/**
 * Class that represents the symbolic integer path constraint: greter than or equal
 * @author svetoslavganov
 */
public class IF_ICMPGE extends IntegerPathConstraint implements Serializable {
	private static final long serialVersionUID = 1;

	public IF_ICMPGE(IntegerInterface op1, IntegerInterface op2) {
		super(op1, op2, ">=", "IF_ICMPGE");
	}
	
	/**
	 * Returns the complementary integer path constraint: less than
	 * @return New instance of the complementary path constraint
	 */
	@Override
	public IntegerPathConstraint inverse() {
		IntegerPathConstraint inversed = this;
		if (inverseCounter < 2) {
			inverseCounter++;
		    inversed = new IF_ICMPLT(super.getOp1(), super.getOp2());
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
		IF_ICMPGE if_icmpge = new IF_ICMPGE((IntegerInterface)op1.clone(), (IntegerInterface)op2.clone());
		if_icmpge.name = name;
		return if_icmpge; 
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
			constraint = problem.geq(op1.getIntExp(problem), op2.getIntExp(problem));
		} catch (UnsupportedOperationByChoco uobc) {
			throw uobc;
		}
		return (IntConstraint) constraint; 
	}
}
