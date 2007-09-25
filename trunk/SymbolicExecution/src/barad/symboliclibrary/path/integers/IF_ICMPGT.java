package barad.symboliclibrary.path.integers;

import java.io.Serializable;

import barad.symboliclibrary.integers.IntegerInterface;

/**
 * Class that represents the symbolic integer path constrint: greter than
 * @author svetoslavganov
 */
public class IF_ICMPGT extends IntegerPathConstraint implements Serializable {
	private static final long serialVersionUID = 1;
	
	public IF_ICMPGT(IntegerInterface op1, IntegerInterface op2) {
		super(op1, op2, ">", "IF_ICMPGT");
	}
	
	/**
	 * Returns the complementary integer path constrint: less than or equal
	 * @return New instance of the complementary path constraint
	 */
	@Override
	public IntegerPathConstraint inverse() {
		return new IF_ICMPLE(super.getOp1(), super.getOp2());
	}
	
	/**
	 * Clones this integer path constraint
	 * @return New clone of the path constrint
	 */
	@Override
	public Object clone() {
		IF_ICMPGT if_icmpgt = new IF_ICMPGT((IntegerInterface)op1.clone(), (IntegerInterface)op2.clone());
		if_icmpgt.setName(this.getName());
		return if_icmpgt; 
	}
}
