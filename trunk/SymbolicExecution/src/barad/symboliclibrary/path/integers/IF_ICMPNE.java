package barad.symboliclibrary.path.integers;

import java.io.Serializable;

import barad.symboliclibrary.integers.IntegerInterface;

public class IF_ICMPNE extends IntegerPathConstraint implements Serializable {
	private static final long serialVersionUID = 1;

	public IF_ICMPNE(IntegerInterface op1, IntegerInterface op2) {
		super(op1, op2, "!=", "IF_ICMPNE");
	}
	
	@Override
	public IntegerPathConstraint inverse() {
		return new IF_ICMPEQ(super.getOp1(), super.getOp2());
	}
	
	@Override
	public Object clone() {
		IF_ICMPNE if_icmpne = new IF_ICMPNE((IntegerInterface)op1.clone(), (IntegerInterface)op2.clone());
		if_icmpne.setName(this.getName());
		return if_icmpne;
	}
}
