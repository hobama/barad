package barad.symboliclibrary.integer;

import java.io.Serializable;

/**
 * Class that implements the symbolic operation
 * "integer multiplication"
 * 
 * @author Svetoslav Ganov
 */
public class IMUL extends IntegerOperation implements Serializable {
	private static final long serialVersionUID = 1;
	
	public IMUL (IntegerInterface op1, IntegerInterface op2) {
		super(op1, op2, "*", "IMUL");
	}
	
	@Override
	public Object clone() {
		IMUL imul = new IMUL((IntegerInterface)op1.clone(), (IntegerInterface)op2.clone());
		imul.setName(this.getName());
		return imul;
	}
}