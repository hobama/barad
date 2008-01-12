package barad.symboliclibrary.integers;

import java.io.Serializable;

import choco.Problem;
import choco.integer.IntExp;

/**
 * Class that implements the symbolic operation "integer addition"
 * @author Svetoslav Ganov
 */
public class IADD extends IntegerOperation implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public IADD (IntegerInterface op1, IntegerInterface op2) {
		super(op1, op2, "+", "IADD");
	}
	
	@Override
	public Object clone() {
		IADD iadd = new IADD((IntegerInterface)op1.clone(), (IntegerInterface)op2.clone());
		iadd.name = name;
		return iadd;
	}

	/**
	 * Returns a new Choco integer expression that represents addition
	 * @param Instance of Choco Problem
	 * @return New Choco plus expression
	 * @throws rethrows UnsupportedOperationByChoco if such is caught
	 */
	public IntExp getIntExp(Problem problem) throws UnsupportedOperationByChoco {
		IntExp intExp = null;
		try {
			intExp = problem.plus(op1.getIntExp(problem), op2.getIntExp(problem));
		} catch (UnsupportedOperationByChoco uobc) {
			throw uobc;
		}
		return intExp;
	}
}