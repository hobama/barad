package barad.symboliclibrary.path.floats;

import java.io.Serializable;

import barad.symboliclibrary.integers.ICONST;
import barad.symboliclibrary.integers.IntegerInterface;

/**
 * Class that represents the symbolic integer path constrint: greter than 0
 * @author svetoslavganov
 */
public class IFFGT extends IntegerPathConstraint implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public IFFGT(IntegerInterface op1) {
		super(op1, new ICONST(0), ">", "IFFGT");
	}
	
	/**
	 * Returns the complementary integer path constrint: less than or equal to 0
	 * @return New instance of the complementary path constraint
	 */
	@Override
	public IntegerPathConstraint inverse() {
		return new IFFLE(super.getOp1());
	}
	
	/**
	 * Clones this integer path constraint
	 * @return New clone of the path constrint
	 */
	@Override
	public Object clone() {
		IFFGT ifgt = new IFFGT((IntegerInterface)op1.clone());
		ifgt.setName(this.getName());
		return ifgt; 
	}
}
