package barad.symboliclibrary.floats;

import java.io.Serializable;

/**
 * Class that implements the symbolic operation "float addition"
 * @author Svetoslav Ganov
 */
public class FADD extends FloatOperation implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public FADD (FloatInterface op1, FloatInterface op2) {
		super(op1, op2, "+", "FADD");
	}
	
	@Override
	public Object clone() {
		FADD fadd = new FADD((FloatInterface)op1.clone(), (FloatInterface)op2.clone());
		fadd.setName(this.getName());
		return fadd;
	}
}