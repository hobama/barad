package barad.symboliclibrary.path.integers;

import java.io.Serializable;

import barad.symboliclibrary.integers.ICONST;
import barad.symboliclibrary.integers.IntegerInterface;

/**
 * Symbolic path condition for replacing the bytecode instruction IFEQ
 * @author svetoslavganov
 */
public class IFEQ extends IntegerPathConstraint implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public IFEQ(IntegerInterface op1) {
		super(op1, new ICONST(0), "==", "IFEQ");
	}
	
	@Override
	public IntegerPathConstraint inverse() {
		return new IFNE(super.getOp1());
	}
	
	@Override
	public Object clone() {
		IFEQ ifeq = new IFEQ((IntegerInterface)op1.clone());
		ifeq.setName(this.getName());
		return ifeq; 
	}
}
