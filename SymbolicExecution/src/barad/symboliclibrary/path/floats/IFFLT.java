package barad.symboliclibrary.path.floats;

import java.io.Serializable;

import barad.symboliclibrary.integers.ICONST;
import barad.symboliclibrary.integers.IntegerInterface;

/**
 * Class that represents the symbolic integer path constrint: less than 0
 * @author svetoslavganov
 */
public class IFFLT extends IntegerPathConstraint implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public IFFLT(IntegerInterface op1) {
		super(op1, new ICONST(0), "<", "IFFLT");
	}
	
	/**
	 * Returns the complementary integer path constrint: greater than or equal to 0
	 * @return New instance of the complementary path constraint
	 */
	@Override
	public IntegerPathConstraint inverse() {
		return new IFFGE(super.getOp1());
	}
	
	/**
	 * Clones this path integer constraint
	 * @return New clone of the path constrint
	 */
	@Override
	public Object clone() {
		IFFLT iflt = new IFFLT((IntegerInterface)op1.clone());
		iflt.setName(this.getName());
		return iflt; 
	}
}
