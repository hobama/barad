package barad.symboliclibrary.path;

import java.io.Serializable;

import barad.symboliclibrary.integers.ICONST;
import barad.symboliclibrary.integers.IntegerInterface;

/**
 * Symbolic path condition for replacing the bytecode instruction IFLT
 * @author svetoslavganov
 */
public class IFLT extends IntegerPathConstraint implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public IFLT(IntegerInterface op1) {
		super(op1, new ICONST(0), "<", "IFLT");
	}
	
	@Override
	public IntegerPathConstraint inverse() {
		return new IFGE(super.getOp1());
	}
	
	@Override
	public Object clone() {
		IFLT iflt = new IFLT((IntegerInterface)op1.clone());
		iflt.setName(this.getName());
		return iflt; 
	}
}
