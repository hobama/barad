package barad.symboliclibrary.path;

import java.io.Serializable;

import barad.symboliclibrary.integers.IntegerInterface;

public class IF_ICMPGE extends IntegerPathConstraint implements Serializable {
	private static final long serialVersionUID = 1;

	public IF_ICMPGE(IntegerInterface op1, IntegerInterface op2) {
		super(op1, op2, ">=", "IF_ICMPGE");
	}
	
	@Override
	public IntegerPathConstraint inverse() {
		return new IF_ICMPLT(super.getOp1(), super.getOp2());
	}
	
	@Override
	public Object clone() {
		IF_ICMPGE if_icmpge = new IF_ICMPGE((IntegerInterface)op1.clone(), (IntegerInterface)op2.clone());
		if_icmpge.setName(this.getName());
		return if_icmpge; 
	}
}
