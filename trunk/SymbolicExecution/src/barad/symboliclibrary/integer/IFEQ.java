package barad.symboliclibrary.integer;

import java.io.Serializable;

import barad.symboliclibrary.path.PathConstraint;

public class IFEQ extends PathConstraint implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public IFEQ(IntegerInterface op1) {
		super(op1, new ICONST(0), "==", "IFEQ");
	}
	
	@Override
	public PathConstraint inverse() {
		return new IFNE(super.getOp1());
	}
	
	@Override
	public Object clone() {
		IF_ICMPEQ if_icmpeq = new IF_ICMPEQ((IntegerInterface)op1.clone(), (IntegerInterface)op2.clone());
		if_icmpeq.setName(this.getName());
		return if_icmpeq; 
	}
}
