package barad.symboliclibrary.path.integers;

import java.io.Serializable;

import barad.symboliclibrary.integers.ICONST;
import barad.symboliclibrary.integers.IntegerInterface;

/**
 * Class that represents the symbolic integer path constraint: greter than or equal to 0
 * @author svetoslavganov
 */
public class IFGE extends IntegerPathConstraint implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public IFGE(IntegerInterface op1) {
		super(op1, new ICONST(0), ">=", "IFFGE");
	}
	
	/**
	 * Returns the complementary integer path constraint: less than 0
	 * @return New instance of the complementary path constraint
	 */
	@Override
	public IntegerPathConstraint inverse() {
		return new IFLT(super.getOp1());
	}
	
	/**
	 * Clones this integer path constraint
	 * @return New clone of the path constraint
	 */
	@Override
	public Object clone() {
		IFGE ifge = new IFGE((IntegerInterface)op1.clone());
		ifge.setName(this.getName());
		return ifge; 
	}
}
