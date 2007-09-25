package barad.symboliclibrary.path.integers;

import java.io.Serializable;

import barad.symboliclibrary.integers.ICONST;
import barad.symboliclibrary.integers.IntegerInterface;

/**
 * Class that represents the symbolic integer path constrint: not equal to 0
 * @author svetoslavganov
 */
public class IFNE extends IntegerPathConstraint implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public IFNE(IntegerInterface op1) {
		super(op1, new ICONST(0), "!=", "IFFNE");
	}
	
	/**
	 * Returns the complementary integer path constrint: equal to 0
	 * @return New instance of the complementary path constraint
	 */
	@Override
	public IntegerPathConstraint inverse() {
		return new IFEQ(super.getOp1());
	}
	
	/**
	 * Clones this integer path constraint
	 * @return New clone of the path constrint
	 */
	@Override
	public Object clone() {
		IFNE ifne = new IFNE((IntegerInterface)op1.clone());
		ifne.setName(this.getName());
		return ifne; 
	}
}
