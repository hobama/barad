package barad.symboliclibrary.path.floats;

import java.io.Serializable;

import barad.symboliclibrary.floats.FCONST;
import barad.symboliclibrary.floats.FloatInterface;

/**
 * Class that represents the symbolic float path constraint: equal to 0
 * @author svetoslavganov
 */
public class IFFEQ extends FloatPathConstraint implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public IFFEQ(FloatInterface op1) {
		super(op1, new FCONST(0), "==", "IFFEQ");
	}
	
	/**
	 * Returns the complementary float path constraint: not equal to 0
	 * @return New instance of the complementary path constraint
	 */
	@Override
	public FloatPathConstraint inverse() {
		return new IFFNE(super.getOp1());
	}
	
	/**
	 * Clones this float path constraint
	 * @return New clone of the path constrint
	 */
	@Override
	public Object clone() {
		IFFEQ iffeq = new IFFEQ((FloatInterface)op1.clone());
		iffeq.setName(this.getName());
		return iffeq; 
	}
}
