package barad.symboliclibrary.path.floats;

import java.io.Serializable;

import barad.symboliclibrary.integers.ICONST;
import barad.symboliclibrary.integers.IntegerInterface;

/**
 * Class that represents the symbolic integer path constrint: less than or equal to 0
 * @author svetoslavganov
 */
public class IFFLE extends IntegerPathConstraint implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public IFFLE(IntegerInterface op1) {
		super(op1, new ICONST(0), "<=", "IFFLE");
	}
	
	/**
	 * Returns the complementary integer path constrint: greater than 0
	 * @return New instance of the complementary path constraint
	 */
	@Override
	public IntegerPathConstraint inverse() {
		return new IFFGT(super.getOp1());
	}
	
	/**
	 * Clones this integer path constraint
	 * @return New clone of the path constrint
	 */
	@Override
	public Object clone() {
		IFFLE ifle = new IFFLE((IntegerInterface)op1.clone());
		ifle.setName(this.getName());
		return ifle; 
	}
}
