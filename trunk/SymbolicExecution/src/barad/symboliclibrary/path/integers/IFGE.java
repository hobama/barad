package barad.symboliclibrary.path.integers;

import java.io.Serializable;

import barad.symboliclibrary.integers.ICONST;
import barad.symboliclibrary.integers.IntegerInterface;

/**
 * Symbolic path condition for replacing the bytecode instruction IFGE
 * @author svetoslavganov
 */
public class IFGE extends IntegerPathConstraint implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public IFGE(IntegerInterface op1) {
		super(op1, new ICONST(0), ">=", "IFGE");
	}
	
	@Override
	public IntegerPathConstraint inverse() {
		return new IFLT(super.getOp1());
	}
	
	@Override
	public Object clone() {
		IFGE ifge = new IFGE((IntegerInterface)op1.clone());
		ifge.setName(this.getName());
		return ifge; 
	}
}
