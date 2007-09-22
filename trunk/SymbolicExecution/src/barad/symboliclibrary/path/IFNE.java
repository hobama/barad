package barad.symboliclibrary.path;

import java.io.Serializable;

import barad.symboliclibrary.integers.ICONST;
import barad.symboliclibrary.integers.IntegerInterface;

/**
 * Symbolic path condition for replacing the bytecode instruction IFNE
 * @author svetoslavganov
 */
public class IFNE extends IntegerPathConstraint implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public IFNE(IntegerInterface op1) {
		super(op1, new ICONST(0), "!=", "IFNE");
	}
	
	@Override
	public IntegerPathConstraint inverse() {
		return new IFEQ(super.getOp1());
	}
	
	@Override
	public Object clone() {
		IFNE ifne = new IFNE((IntegerInterface)op1.clone());
		ifne.setName(this.getName());
		return ifne; 
	}
}
