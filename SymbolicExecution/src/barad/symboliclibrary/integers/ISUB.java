package barad.symboliclibrary.integers;

import java.io.Serializable;

import choco.Problem;
import choco.integer.IntExp;

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
	
   /**
	* Returns a new Choco integer expression that represents substraction
	* @param Instance of Choco Problem
	* @return New Choco plus expression
	*/
	public IntExp getIntExp(Problem problem) throws UnsupportedOperationByChoco {
		IntExp intExp = null;
		try {
			problem.minus(op1.getIntExp(problem), op2.getIntExp(problem));
		} catch (UnsupportedOperationByChoco uobc) {
			throw uobc;
		}
		return intExp;
	}
}