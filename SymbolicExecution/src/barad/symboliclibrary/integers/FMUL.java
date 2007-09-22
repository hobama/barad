package barad.symboliclibrary.floats;

import java.io.Serializable;

/**
 * Class that implements the symbolic operation "float multiplication"
 * @author Svetoslav Ganov
 */
public class FMUL extends FloatOperation implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public FMUL (FloatInterface op1, FloatInterface op2) {
		super(op1, op2, "*", "FMUL");
	}
	
	@Override
	public Object clone() {
		FMUL fmul = new FMUL((FloatInterface)op1.clone(), (FloatInterface)op2.clone());
		fmul.setName(this.getName());
		return fmul;
	}
}