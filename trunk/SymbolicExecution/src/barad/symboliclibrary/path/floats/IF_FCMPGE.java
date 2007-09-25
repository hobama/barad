package barad.symboliclibrary.path.floats;

import java.io.Serializable;

import barad.symboliclibrary.floats.FloatInterface;
import barad.symboliclibrary.path.FloatPathConstraint;

/**
 * Class that represents the symbolic float path constrint: greter than or equal
 * @author svetoslavganov
 */
public class IF_FCMPGE extends FloatPathConstraint implements Serializable {
	private static final long serialVersionUID = 1;

	public IF_FCMPGE(FloatInterface op1, FloatInterface op2) {
		super(op1, op2, ">=", "IF_FCMPGE");
	}
	
	/**
	 * Returns the complementary float path constrint: less than
	 * @return New instance of the complementary path constraint
	 */
	@Override
	public FloatPathConstraint inverse() {
		return new IF_FCMPLT(super.getOp1(), super.getOp2());
	}
	
	/**
	 * Clones this float path constraint
	 * @return New clone of the path constrint
	 */
	@Override
	public Object clone() {
		IF_FCMPGE if_fcmpge = new IF_FCMPGE((FloatInterface)op1.clone(), (FloatInterface)op2.clone());
		if_fcmpge.setName(this.getName());
		return if_fcmpge; 
	}
}
