package barad.symboliclibrary.path.floats;

import java.io.Serializable;

import barad.symboliclibrary.floats.FCONST;
import barad.symboliclibrary.floats.FloatInterface;

/**
 * Class that represents the symbolic float path constraint: not equal to 0
 * @author svetoslavganov
 */
public class IFFNE extends FloatPathConstraint implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public IFFNE(FloatInterface op1) {
		super(op1, new FCONST(0), "!=", "IFFNE");
	}
	
	/**
	 * Returns the complementary float path constrint: equal to 0
	 * @return New instance of the complementary path constraint
	 */
	@Override
	public FloatPathConstraint inverse() {
		return new IFFEQ(super.getOp1());
	}
	
	/**
	 * Clones this float path constraint
	 * @return New clone of the path constrint
	 */
	@Override
	public Object clone() {
		IFFNE iffne = new IFFNE((FloatInterface)op1.clone());
		iffne.setName(this.getName());
		return iffne; 
	}
}
