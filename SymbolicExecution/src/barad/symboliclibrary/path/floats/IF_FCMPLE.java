package barad.symboliclibrary.path.floats;

import java.io.Serializable;

import barad.symboliclibrary.floats.FloatInterface;

/**
 * Class that represents the symbolic float path constrint: less than or equal
 * @author svetoslavganov
 */
public class IF_FCMPLE extends FloatPathConstraint implements Serializable {
	private static final long serialVersionUID = 1;

	public IF_FCMPLE(FloatInterface op1, FloatInterface op2) {
		super(op1, op2, "<=", "IF_FCMPLE");
	}
	
	/**
	 * Returns the complementary float path constrint: greater than
	 * @return New instance of the complementary path constraint
	 */
	@Override
	public FloatPathConstraint inverse() {
		return new IF_FCMPGT(super.getOp1(), super.getOp2());
	}
	
	/**
	 * Clones this float path constraint
	 * @return New clone of the path constrint
	 */
	@Override
	public Object clone() {
		IF_FCMPLE if_fcmple = new IF_FCMPLE((FloatInterface)op1.clone(), (FloatInterface)op2.clone());
		if_fcmple.setName(this.getName());
		return if_fcmple;
	}
}
