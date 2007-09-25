package barad.symboliclibrary.path.floats;

import java.io.Serializable;

import barad.symboliclibrary.floats.FloatInterface;

/**
 * Class that represents the symbolic float path constrint: equal
 * @author svetoslavganov
 */
public class IF_FCMPEQ extends FloatPathConstraint implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public IF_FCMPEQ(FloatInterface op1, FloatInterface op2) {
		super(op1, op2, "==", "IF_FCMPEQ");
	}
	
	/**
	 * Returns the complementary float path constrint: not equal
	 * @return New instance of the complementary path constraint
	 */
	@Override
	public FloatPathConstraint inverse() {
		return new IF_FCMPNE(super.getOp1(), super.getOp2());
	}
	
	/**
	 * Clones this float path constraint
	 * @return New clone of the path constrint
	 */
	@Override
	public Object clone() {
		IF_FCMPEQ if_fcmpeq = new IF_FCMPEQ((FloatInterface)op1.clone(), (FloatInterface)op2.clone());
		if_fcmpeq.setName(this.getName());
		return if_fcmpeq; 
	}
}
