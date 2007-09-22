package barad.symboliclibrary.floats;

import java.io.Serializable;

/**
 * Class that implements the symbolic operation "float division"
 * @author Svetoslav Ganov
 */
public class FDIV extends FloatOperation implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public FDIV (FloatInterface op1, FloatInterface op2) {
		super(op1, op2, "/", "FDIV");
	}
	
	@Override
	public Object clone() {
		FDIV fdiv = new FDIV((FloatInterface)op1.clone(), (FloatInterface)op2.clone());
		fdiv.setName(this.getName());
		return fdiv;
	}
}