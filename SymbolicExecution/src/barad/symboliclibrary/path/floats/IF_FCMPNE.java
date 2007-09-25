package barad.symboliclibrary.path.floats;

import java.io.Serializable;

import barad.symboliclibrary.floats.FloatInterface;

/**
 * Class that represents the symbolic path constrint: not equal
 * @author svetoslavganov
 */
public class IF_FCMPNE extends FloatPathConstraint implements Serializable {
	private static final long serialVersionUID = 1;

	public IF_FCMPNE(FloatInterface op1, FloatInterface op2) {
		super(op1, op2, "!=", "IF_FCMPNE");
	}
	
	/**
	 * Returns the complementary path constrint: equal
	 * @return New instance of the complementary path constraint
	 */
	@Override
	public FloatPathConstraint inverse() {
		return new IF_FCMPEQ(super.getOp1(), super.getOp2());
	}
	
	/**
	 * Clones this path constraint
	 * @return New clone of the path constrint
	 */
	@Override
	public Object clone() {
		IF_FCMPNE if_fcmpne = new IF_FCMPNE((FloatInterface)op1.clone(), (FloatInterface)op2.clone());
		if_fcmpne.setName(this.getName());
		return if_fcmpne;
	}
}
