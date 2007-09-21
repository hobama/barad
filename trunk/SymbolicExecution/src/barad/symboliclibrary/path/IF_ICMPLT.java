package barad.symboliclibrary.path;

import java.io.Serializable;

import barad.symboliclibrary.integers.IntegerInterface;

public class IF_ICMPLT extends IntegerPathConstraint implements Serializable {
	private static final long serialVersionUID = 1;

	public IF_ICMPLT(IntegerInterface op1, IntegerInterface op2) {
		super(op1, op2, "<", "IF_ICMPLT");
	}
	
	@Override
	public IntegerPathConstraint inverse() {
		return new IF_ICMPGE(super.getOp1(), super.getOp2());
	}
	
	@Override
	public Object clone() {
		IF_ICMPLT if_icmplt = new IF_ICMPLT((IntegerInterface)op1.clone(), (IntegerInterface)op2.clone());
		if_icmplt.setName(this.getName());
		return if_icmplt;
	}
}
