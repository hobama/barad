package barad.symboliclibrary.path.floats;

import java.io.Serializable;

import barad.symboliclibrary.integers.ICONST;
import barad.symboliclibrary.integers.IntegerInterface;

/**
 * Class that represents the symbolic integer path constrint: equal to 0
 * @author svetoslavganov
 */
public class IFFEQ extends IntegerPathConstraint implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public IFFEQ(IntegerInterface op1) {
		super(op1, new ICONST(0), "==", "IFFEQ");
	}
	
	/**
	 * Returns the complementary integer path constrint: not equal to 0
	 * @return New instance of the complementary path constraint
	 */
	@Override
	public IntegerPathConstraint inverse() {
		return new IFFNE(super.getOp1());
	}
	
	/**
	 * Clones this integer path constraint
	 * @return New clone of the path constrint
	 */
	@Override
	public Object clone() {
		IFFEQ ifeq = new IFFEQ((IntegerInterface)op1.clone());
		ifeq.setName(this.getName());
		return ifeq; 
	}
}
