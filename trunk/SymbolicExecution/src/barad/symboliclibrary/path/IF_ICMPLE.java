package barad.symboliclibrary.path;

import java.io.Serializable;

import barad.symboliclibrary.integers.IntegerInterface;

public class IF_ICMPLE extends IntegerPathConstraint implements Serializable {
	private static final long serialVersionUID = 1;

	public IF_ICMPLE(IntegerInterface op1, IntegerInterface op2) {
		super(op1, op2, "<=", "IF_ICMPLE");
	}
	
	@Override
	public IntegerPathConstraint inverse() {
		return new IF_ICMPGT(super.getOp1(), super.getOp2());
	}
	
	@Override
	public Object clone() {
		IF_ICMPLE if_icmple = new IF_ICMPLE((IntegerInterface)op1.clone(), (IntegerInterface)op2.clone());
		if_icmple.setName(this.getName());
		return if_icmple;
	}
}
