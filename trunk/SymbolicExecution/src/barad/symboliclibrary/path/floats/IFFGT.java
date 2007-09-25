package barad.symboliclibrary.path.floats;

import java.io.Serializable;

import barad.symboliclibrary.floats.FCONST;
import barad.symboliclibrary.floats.FloatInterface;

/**
 * Class that represents the symbolic float path constraint: greter than 0
 * @author svetoslavganov
 */
public class IFFGT extends FloatPathConstraint implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public IFFGT(FloatInterface op1) {
		super(op1, new FCONST(0), ">", "IFFGT");
	}
	
	/**
	 * Returns the complementary float path constraint: less than or equal to 0
	 * @return New instance of the complementary path constraint
	 */
	@Override
	public FloatPathConstraint inverse() {
		return new IFFLE(super.getOp1());
	}
	
	/**
	 * Clones this float path constraint
	 * @return New clone of the path constraint
	 */
	@Override
	public Object clone() {
		IFFGT iffgt = new IFFGT((FloatInterface)op1.clone());
		iffgt.setName(this.getName());
		return iffgt; 
	}
}
