package barad.symboliclibrary.integers;

import java.io.Serializable;

import choco.Problem;
import choco.integer.IntExp;

/**
 * Class that implements the symbolic operation "integer multiplication"
 * @author Svetoslav Ganov
 */
public class IMUL extends IntegerOperation implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public IMUL (IntegerInterface op1, IntegerInterface op2) {
		super(op1, op2, "*", "IMUL");
	}
	
	@Override
	public Object clone() {
		IMUL imul = new IMUL((IntegerInterface)op1.clone(), (IntegerInterface)op2.clone());
		imul.name = name;
		return imul;
	}
	
	/**
	 * Returns a new Choco integer expression that represents multiplication
	 * @param Instance of Choco Problem
	 * @return New Choco plus expression
	 * @throws UnsupportedOperationByChoco if the two operands are integer constants
	 * and rethrows it if such is caught
	 */
	public IntExp getIntExp(Problem problem) throws UnsupportedOperationByChoco {
		IntExp intExp = null;
		try {
			if (op1 instanceof ICONST && !(op2 instanceof ICONST)) {
				intExp = problem.mult(((ICONST)op1).getValue(), op2.getIntExp(problem));
			} else if (!(op1 instanceof ICONST) && op2 instanceof ICONST){ 
				intExp = problem.mult(((ICONST)op2).getValue(), op1.getIntExp(problem));
			} else {
				throw new UnsupportedOperationByChoco("Integer multiplication of two constants is not supported by Choco");
			}
		} catch (UnsupportedOperationByChoco uobc) {
			throw uobc;
		}
		return intExp;
	}
}