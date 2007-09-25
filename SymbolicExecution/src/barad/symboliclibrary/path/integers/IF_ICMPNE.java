package barad.symboliclibrary.path.integers;

import java.io.Serializable;

import barad.symboliclibrary.integers.IntegerInterface;

/**
 * Class that represents the symbolic integer path constraint: not equal
 * @author svetoslavganov
 */
public class IF_ICMPNE extends IntegerPathConstraint implements Serializable {
	private static final long serialVersionUID = 1;

	public IF_ICMPNE(IntegerInterface op1, IntegerInterface op2) {
		super(op1, op2, "!=", "IF_ICMPNE");
	}
	
	/**
	 * Returns the complementary integer path constraint: equal
	 * @return New instance of the complementary path constraint
	 */
	@Override
	public IntegerPathConstraint inverse() {
		return new IF_ICMPEQ(super.getOp1(), super.getOp2());
	}
	
	/**
	 * Clones this integer path constraint
	 * @return New clone of the path constraint
	 */
	@Override
	public Object clone() {
		IF_ICMPNE if_icmpne = new IF_ICMPNE((IntegerInterface)op1.clone(), (IntegerInterface)op2.clone());
		if_icmpne.setName(this.getName());
		return if_icmpne;
	}
}
