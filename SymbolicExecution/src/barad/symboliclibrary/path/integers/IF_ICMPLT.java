package barad.symboliclibrary.path.integers;

import java.io.Serializable;

import barad.symboliclibrary.integers.IntegerInterface;

/**
 * Class that represents the symbolic integer path constraint: less than
 * @author svetoslavganov
 */
public class IF_ICMPLT extends IntegerPathConstraint implements Serializable {
	private static final long serialVersionUID = 1;

	public IF_ICMPLT(IntegerInterface op1, IntegerInterface op2) {
		super(op1, op2, "<", "IF_ICMPLT");
	}
	
	/**
	 * Returns the complementary integer path constraint: greater than or equal
	 * @return New instance of the complementary path constraint
	 */
	@Override
	public IntegerPathConstraint inverse() {
		return new IF_ICMPGE(super.getOp1(), super.getOp2());
	}
	
	/**
	 * Clones this path integer constraint
	 * @return New clone of the path constraint
	 */
	@Override
	public Object clone() {
		IF_ICMPLT if_icmplt = new IF_ICMPLT((IntegerInterface)op1.clone(), (IntegerInterface)op2.clone());
		if_icmplt.setName(this.getName());
		return if_icmplt;
	}
}
