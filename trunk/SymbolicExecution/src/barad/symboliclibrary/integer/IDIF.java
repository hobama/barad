package barad.symboliclibrary.integer;

import java.io.Serializable;

/**
 * Class that implements the symbolic operation
 * "integer difference"
 * 
 * @author Svetoslav Ganov
 */
public class IDIF extends IntegerOperation implements Serializable {
	private static final long serialVersionUID = 1;
	
	public IDIF (IntegerInterface op1, IntegerInterface op2) {
		super(op1, op2, "-", "IDIF");
	}
	
	@Override
	public Object clone() {
		IDIF idif = new IDIF((IntegerInterface)op1.clone(), (IntegerInterface)op2.clone());
		idif.setName(this.getName());
		return idif;
	}
}