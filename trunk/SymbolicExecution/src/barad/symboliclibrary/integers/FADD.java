package barad.symboliclibrary.floats;

import java.io.Serializable;

import barad.symboliclibrary.integers.UnsupportedOperationByChoco;
import choco.Problem;
import choco.real.RealExp;

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
	
	/**
	 * Returns a new Choco real expression that represents addition
	 * @param Instance of Choco Problem
	 * @return New Choco plus expression
	 * @throws rethrows UnsupportedOperationByChoco if such is caught
	 */
	public RealExp getRealExp(Problem problem) {
		return problem.plus(op1.getRealExp(problem), op2.getRealExp(problem));
	}
}