package barad.symboliclibrary.floats;

import java.io.Serializable;

import barad.symboliclibrary.integers.UnsupportedOperationByChoco;
import choco.Problem;
import choco.real.RealExp;

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
	
	/**
	 * Returns a new Choco real expression that represents division
	 * @param Instance of Choco Problem
	 * @return New Choco division expression (X / Y <+> X * Y^-1)
	 * @throws rethrows UnsupportedOperationByChoco if such is caught
	 */
	public RealExp getRealExp(Problem problem) {
		return problem.mult(op1.getRealExp(problem), problem.power(op2.getRealExp(problem), -1));
	}
}