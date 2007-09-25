package barad.symboliclibrary.path.floats;

import java.io.Serializable;

import barad.symboliclibrary.floats.FCONST;
import barad.symboliclibrary.floats.FloatInterface;

/**
 * Class that represents the symbolic float path constraint: less than or equal to 0
 * @author svetoslavganov
 */
public class IFFLE extends FloatPathConstraint implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public IFFLE(FloatInterface op1) {
		super(op1, new FCONST(0), "<=", "IFFLE");
	}
	
	/**
	 * Returns the complementary float path constraint: greater than 0
	 * @return New instance of the complementary path constraint
	 */
	@Override
	public FloatPathConstraint inverse() {
		return new IFFGT(super.getOp1());
	}
	
	/**
	 * Clones this integer float constraint
	 * @return New clone of the path constraint
	 */
	@Override
	public Object clone() {
		IFFLE iffle = new IFFLE((FloatInterface)op1.clone());
		iffle.setName(this.getName());
		return iffle; 
	}
}
