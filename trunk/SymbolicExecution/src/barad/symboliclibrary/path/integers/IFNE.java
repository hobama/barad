package barad.symboliclibrary.path.integers;

import java.io.Serializable;

import choco.Constraint;
import choco.Problem;
import choco.integer.IntConstraint;

import barad.symboliclibrary.integers.ICONST;
import barad.symboliclibrary.integers.IntegerInterface;
import barad.symboliclibrary.integers.UnsupportedOperationByChoco;

/**
 * Class that represents the symbolic integer path constraint: not equal to 0
 * @author svetoslavganov
 */
public class IFNE extends IntegerPathConstraint implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public IFNE(IntegerInterface op1) {
		super(op1, new ICONST(0), "!=", "IFFNE");
	}
	
	/**
	 * Returns the complementary integer path constraint: equal to 0
	 * @return New instance of the complementary path constraint
	 */
	@Override
	public IntegerPathConstraint inverse() {
		return new IFEQ(super.getOp1());
	}
	
	/**
	 * Clones this integer path constraint
	 * @return New clone of the path constraint
	 */
	@Override
	public Object clone() {
		IFNE ifne = new IFNE((IntegerInterface)op1.clone());
		ifne.name = name;
		return ifne; 
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
			constraint = problem.neq(op1.getIntExp(problem), 0);
		} catch (UnsupportedOperationByChoco uobc) {
			throw uobc;
		}
		return (IntConstraint) constraint; 
	}
}
