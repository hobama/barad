package barad.symboliclibrary.integer;

import java.io.Serializable;

/**
 * Class that implements the symbolic operation
 * "integer addition"
 * 
 * @author Svetoslav Ganov
 */
public class IADD extends IntegerOperation implements Serializable {
	private static final long serialVersionUID = 1;
	
	public IADD (IntegerInterface op1, IntegerInterface op2) {
		super(op1, op2, "+", "IADD");
	}
	
	@Override
	public Object clone() {
		IADD iadd = new IADD((IntegerInterface)op1.clone(), (IntegerInterface)op2.clone());
		iadd.setName(this.getName());
		return iadd;
	}
}