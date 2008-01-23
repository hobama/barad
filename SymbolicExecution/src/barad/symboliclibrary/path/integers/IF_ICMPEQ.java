package barad.symboliclibrary.path.integers;

import java.io.Serializable;

import barad.symboliclibrary.integers.IntegerInterface;
import barad.symboliclibrary.integers.UnsupportedOperationByChoco;
import choco.Constraint;
import choco.Problem;
import choco.integer.IntConstraint;

/**
 * Class that represents the symbolic integer path constraint: equal
 * @author svetoslavganov
 */
public class IF_ICMPEQ extends IntegerPathConstraint implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public IF_ICMPEQ(IntegerInterface op1, IntegerInterface op2) {
		super(op1, op2, "==", "IF_ICMPEQ");
	}
	
	/**
	 * Returns the complementary integer path constraint: not equal
	 * @return New instance of the complementary path constraint
	 */
	@Override
	public IntegerPathConstraint inverse() {
		IntegerPathConstraint inversed = this;
		if (inverseCounter < 2) {
			inverseCounter++;
		    inversed = new IF_ICMPNE(super.getOp1(), super.getOp2());
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
		IF_ICMPEQ if_icmpeq = new IF_ICMPEQ((IntegerInterface)op1.clone(), (IntegerInterface)op2.clone());
		if_icmpeq.name = name;
		return if_icmpeq; 
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
			constraint = problem.eq(op1.getIntExp(problem), op2.getIntExp(problem));
		} catch (UnsupportedOperationByChoco uobc) {
			throw uobc;
		}
		return (IntConstraint) constraint; 
	}
}
