package barad.symboliclibrary.path;

import java.io.Serializable;

import barad.symboliclibrary.integers.IntegerInterface;

public class FCMPL extends IntegerPathConstraint implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public FCMPL(IntegerInterface op1, IntegerInterface op2) {
		super(op1, op2, "==", "FCMPL");
	}
	
	@Override
	public IntegerPathConstraint inverse() {
		return new IF_ICMPNE(super.getOp1(), super.getOp2());
	}
	
	@Override
	public Object clone() {
		FCMPL fcmpl = new FCMPL((IntegerInterface)op1.clone(), (IntegerInterface)op2.clone());
		fcmpl.setName(this.getName());
		return fcmpl; 
	}
}
