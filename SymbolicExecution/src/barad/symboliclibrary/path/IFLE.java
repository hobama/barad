package barad.symboliclibrary.path;

import java.io.Serializable;

import barad.symboliclibrary.integers.ICONST;
import barad.symboliclibrary.integers.IntegerInterface;

/**
 * Symbolic path condition for replacing the bytecode instruction IFLE
 * @author svetoslavganov
 */
public class IFLE extends IntegerPathConstraint implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public IFLE(IntegerInterface op1) {
		super(op1, new ICONST(0), "<=", "IFLE");
	}
	
	@Override
	public IntegerPathConstraint inverse() {
		return new IFGT(super.getOp1());
	}
	
	@Override
	public Object clone() {
		IFLE ifle = new IFLE((IntegerInterface)op1.clone());
		ifle.setName(this.getName());
		return ifle; 
	}
}
