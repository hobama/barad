package barad.symboliclibrary.path.integers;

import java.io.Serializable;

import choco.Constraint;
import choco.Problem;
import choco.integer.IntConstraint;
import choco.integer.IntExp;

import barad.symboliclibrary.integers.ICONST;
import barad.symboliclibrary.integers.IntegerInterface;
import barad.symboliclibrary.integers.UnsupportedOperationByChoco;
import barad.util.Util;

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
		return new IF_ICMPNE(super.getOp1(), super.getOp2());
	}
	
	/**
	 * Clones this integer path constraint
	 * @return New clone of the path constraint
	 */
	@Override
	public Object clone() {
		IF_ICMPEQ if_icmpeq = new IF_ICMPEQ((IntegerInterface)op1.clone(), (IntegerInterface)op2.clone());
		if_icmpeq.setName(this.getName());
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
