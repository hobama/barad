package barad.symboliclibrary.integers;

import java.io.Serializable;

/**
 * Class that implements the symbolic operation "integer difference"
 * @author Svetoslav Ganov
 */
public class ISUB extends IntegerOperation implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public ISUB (IntegerInterface op1, IntegerInterface op2) {
		super(op1, op2, "-", "ISUB");
	}
	
	@Override
	public Object clone() {
		ISUB isub = new ISUB((IntegerInterface)op1.clone(), (IntegerInterface)op2.clone());
		isub.setName(this.getName());
		return isub;
	}
}