package barad.symboliclibrary.integers;

import java.io.Serializable;

import choco.Problem;
import choco.integer.IntExp;

/**
 * Class that implements the symbolic operation "integer division"
 * @author Svetoslav Ganov
 */
public class IDIV extends IntegerOperation implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public IDIV (IntegerInterface op1, IntegerInterface op2) {
		super(op1, op2, "/", "IDIV");
	}
	
	@Override
	public Object clone() {
		IDIV idiv = new IDIV((IntegerInterface)op1.clone(), (IntegerInterface)op2.clone());
		idiv.setName(this.getName());
		return idiv;
	}
	
	/**
	* Returns a new Choco integer expression that represents division
	* @param Instance of Choco Problem
	* @return Throws new UnsupportedOperationByChoco since this operation is not
	* supported by Choco neither power opertion in order to model division
	* @throws UnsupportedOperationByChoco always
	*/
	public IntExp getIntExp(Problem problem) throws UnsupportedOperationByChoco {
		throw new UnsupportedOperationByChoco("Neither integer division nor oprations to model it are supported by Choco");
	}
}