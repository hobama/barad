package barad.symboliclibrary.integer;

import java.io.Serializable;

import barad.symboliclibrary.path.PathConstraint;

public class IF_ICMPEQ extends PathConstraint implements Serializable {
	private static final long serialVersionUID = 1;
	
	public IF_ICMPEQ(IntegerInterface op1, IntegerInterface op2) {
		super(op1, op2, "==", "IF_ICMPEQ");
	}
	
	@Override
	public PathConstraint inverse() {
		return new IF_ICMPNE(super.getOp1(), super.getOp2());
	}
	
	@Override
	public Object clone() {
		IF_ICMPEQ if_icmpeq = new IF_ICMPEQ((IntegerInterface)op1.clone(), (IntegerInterface)op2.clone());
		if_icmpeq.setName(this.getName());
		return if_icmpeq; 
	}
}
