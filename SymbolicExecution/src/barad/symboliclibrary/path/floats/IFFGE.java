package barad.symboliclibrary.path.floats;

import java.io.Serializable;

import barad.symboliclibrary.floats.FCONST;
import barad.symboliclibrary.floats.FloatInterface;

/**
 * Class that represents the symbolic float path constrint: greter than or equal to 0
 * @author svetoslavganov
 */
public class IFFGE extends FloatPathConstraint implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public IFFGE(FloatInterface op1) {
		super(op1, new FCONST(0), ">=", "IFFGE");
	}
	
	/**
	 * Returns the complementary float path constraint: less than 0
	 * @return New instance of the complementary path constraint
	 */
	@Override
	public FloatPathConstraint inverse() {
		return new IFFLT(super.getOp1());
	}
	
	/**
	 * Clones this float path constraint
	 * @return New clone of the path constraint
	 */
	@Override
	public Object clone() {
		IFFGE iffge = new IFFGE((FloatInterface)op1.clone());
		iffge.setName(this.getName());
		return iffge; 
	}
}
