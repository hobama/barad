package barad.symboliclibrary.path.integers;

import java.io.Serializable;

import barad.symboliclibrary.integers.ICONST;
import barad.symboliclibrary.integers.IntegerInterface;

/**
 * Class that represents the symbolic integer path constrint: equal to 0
 * @author svetoslavganov
 */
public class IFEQ extends IntegerPathConstraint implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public IFEQ(IntegerInterface op1) {
		super(op1, new ICONST(0), "==", "IFFEQ");
	}
	
	/**
	 * Returns the complementary integer path constrint: not equal to 0
	 * @return New instance of the complementary path constraint
	 */
	@Override
	public IntegerPathConstraint inverse() {
		return new IFNE(super.getOp1());
	}
	
	/**
	 * Clones this integer path constraint
	 * @return New clone of the path constrint
	 */
	@Override
	public Object clone() {
		IFEQ ifeq = new IFEQ((IntegerInterface)op1.clone());
		ifeq.setName(this.getName());
		return ifeq; 
	}
}
