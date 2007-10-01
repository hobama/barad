package barad.symboliclibrary.floats;

import java.io.Serializable;

import barad.symboliclibrary.integers.UnsupportedOperationByChoco;
import choco.Problem;
import choco.real.RealExp;

/**
 * Class that implements the symbolic operation "float difference"
 * @author Svetoslav Ganov
 */
public class FSUB extends FloatOperation implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public FSUB (FloatInterface op1, FloatInterface op2) {
		super(op1, op2, "-", "FSUB");
	}
	
	@Override
	public Object clone() {
		FSUB fdif = new FSUB((FloatInterface)op1.clone(), (FloatInterface)op2.clone());
		fdif.setName(this.getName());
		return fdif;
	}
	
	/**
	 * Returns a new Choco real expression that represents substraction
	 * @param Instance of Choco Problem
	 * @return New Choco substraction expression
	 * @throws rethrows UnsupportedOperationByChoco if such is caught
	 */
	public RealExp getRealExp(Problem problem) {
		return problem.minus(op1.getRealExp(problem), op2.getRealExp(problem));
	}
}