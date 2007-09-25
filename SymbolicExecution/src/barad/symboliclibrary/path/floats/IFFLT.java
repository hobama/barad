package barad.symboliclibrary.path.floats;

import java.io.Serializable;

import barad.symboliclibrary.floats.FCONST;
import barad.symboliclibrary.floats.FloatInterface;

/**
 * Class that represents the symbolic float path constraint: less than 0
 * @author svetoslavganov
 */
public class IFFLT extends FloatPathConstraint implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public IFFLT(FloatInterface op1) {
		super(op1, new FCONST(0), "<", "IFFLT");
	}
	
	/**
	 * Returns the complementary float path constraint: greater than or equal to 0
	 * @return New instance of the complementary path constraint
	 */
	@Override
	public FloatPathConstraint inverse() {
		return new IFFGE(super.getOp1());
	}
	
	/**
	 * Clones this float path constraint
	 * @return New clone of the path constraint
	 */
	@Override
	public Object clone() {
		IFFLT ifflt = new IFFLT((FloatInterface)op1.clone());
		ifflt.setName(this.getName());
		return ifflt; 
	}
}
