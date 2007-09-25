package barad.symboliclibrary.path.integers;

import java.io.Serializable;

import barad.symboliclibrary.integers.ICONST;
import barad.symboliclibrary.integers.IntegerInterface;

/**
 * Symbolic path condition for replacing the bytecode instruction IFGT
 * @author svetoslavganov
 */
public class IFGT extends IntegerPathConstraint implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public IFGT(IntegerInterface op1) {
		super(op1, new ICONST(0), ">", "IFGT");
	}
	
	@Override
	public IntegerPathConstraint inverse() {
		return new IFLE(super.getOp1());
	}
	
	@Override
	public Object clone() {
		IFGT ifgt = new IFGT((IntegerInterface)op1.clone());
		ifgt.setName(this.getName());
		return ifgt; 
	}
}
