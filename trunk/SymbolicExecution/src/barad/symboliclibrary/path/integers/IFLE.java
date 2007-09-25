package barad.symboliclibrary.path.integers;

import java.io.Serializable;

import barad.symboliclibrary.integers.ICONST;
import barad.symboliclibrary.integers.IntegerInterface;

/**
 * Class that represents the symbolic integer path constraint: less than or equal to 0
 * @author svetoslavganov
 */
public class IFLE extends IntegerPathConstraint implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public IFLE(IntegerInterface op1) {
		super(op1, new ICONST(0), "<=", "IFFLE");
	}
	
	/**
	 * Returns the complementary integer path constraint: greater than 0
	 * @return New instance of the complementary path constraint
	 */
	@Override
	public IntegerPathConstraint inverse() {
		return new IFGT(super.getOp1());
	}
	
	/**
	 * Clones this integer path constraint
	 * @return New clone of the path constraint
	 */
	@Override
	public Object clone() {
		IFLE ifle = new IFLE((IntegerInterface)op1.clone());
		ifle.setName(this.getName());
		return ifle; 
	}
}
