package barad.symboliclibrary.path;

import java.io.Serializable;

import barad.symboliclibrary.integers.IntegerInterface;

public class IF_ICMPGT extends IntegerPathConstraint implements Serializable {
	private static final long serialVersionUID = 1;
	
	public IF_ICMPGT(IntegerInterface op1, IntegerInterface op2) {
		super(op1, op2, ">", "IF_ICMPGT");
	}
	
	@Override
	public IntegerPathConstraint inverse() {
		return new IF_ICMPLE(super.getOp1(), super.getOp2());
	}
	
	@Override
	public Object clone() {
		IF_ICMPGT if_icmpgt = new IF_ICMPGT((IntegerInterface)op1.clone(), (IntegerInterface)op2.clone());
		if_icmpgt.setName(this.getName());
		return if_icmpgt; 
	}
}
