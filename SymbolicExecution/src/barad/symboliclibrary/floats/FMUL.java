package barad.symboliclibrary.floats;

import java.io.Serializable;

import barad.symboliclibrary.integers.UnsupportedOperationByChoco;
import choco.Problem;
import choco.real.RealExp;

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
	
	/**
	 * Returns a new Choco real expression that represents multiplication
	 * @param Instance of Choco Problem
	 * @return New Choco multiplication expression
	 * @throws rethrows UnsupportedOperationByChoco if such is caught
	 */
	public RealExp getRealExp(Problem problem) {
		return problem.mult(op1.getRealExp(problem), op2.getRealExp(problem));
	}
}