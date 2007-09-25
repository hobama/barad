package barad.symboliclibrary.path.integers;

import java.io.Serializable;

import barad.symboliclibrary.integers.IntegerInterface;

/**
 * Class that represents the symbolic integer path constrint: less than or equal
 * @author svetoslavganov
 */
public class IF_ICMPLE extends IntegerPathConstraint implements Serializable {
	private static final long serialVersionUID = 1;

	public IF_ICMPLE(IntegerInterface op1, IntegerInterface op2) {
		super(op1, op2, "<=", "IF_ICMPLE");
	}
	
	/**
	 * Returns the complementary integer path constrint: greater than
	 * @return New instance of the complementary path constraint
	 */
	@Override
	public IntegerPathConstraint inverse() {
		return new IF_ICMPGT(super.getOp1(), super.getOp2());
	}
	
	/**
	 * Clones this integer path constraint
	 * @return New clone of the path constrint
	 */
	@Override
	public Object clone() {
		IF_ICMPLE if_icmple = new IF_ICMPLE((IntegerInterface)op1.clone(), (IntegerInterface)op2.clone());
		if_icmple.setName(this.getName());
		return if_icmple;
	}
}
