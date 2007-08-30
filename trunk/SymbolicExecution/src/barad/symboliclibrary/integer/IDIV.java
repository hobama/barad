package barad.symboliclibrary.integer;

import java.io.Serializable;

/**
 * Class that implements the symbolic operation
 * "integer division"
 * 
 * @author Svetoslav Ganov
 */
public class IDIV extends IntegerOperation implements Serializable {
	private static final long serialVersionUID = 1;
	
	public IDIV (IntegerInterface op1, IntegerInterface op2) {
		super(op1, op2, "/", "IDIV");
	}
	
	@Override
	public Object clone() {
		IDIV iadd = new IDIV((IntegerInterface)op1.clone(), (IntegerInterface)op2.clone());
		iadd.setName(this.getName());
		return iadd;
	}
}