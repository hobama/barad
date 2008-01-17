package barad.symboliclibrary.integers;

import barad.symboliclibrary.common.CommonInterface;
import choco.Problem;
import choco.integer.IntExp;

public interface IntegerInterface extends CommonInterface, IntExp {   
	/**
	 * @param Instance that implements IntegerInterface
	 * @return New integer multiplication operation
	 */
	IMUL IMUL(SymbolicIntegerEntity op);

	/**
	 * @param Instance that implements IntegerInterface
	 * @return New integer division operation
	 */
	IDIV IDIV(SymbolicIntegerEntity op);

	/**
	 * @param Instance that implements IntegerInterface
	 * @return New integer addition operation
	 */
	IADD IADD(SymbolicIntegerEntity op);

	/**
	 * @param Instance that implements IntegerInterface
	 * @return New integer difference operation
	 */
	ISUB ISUB(SymbolicIntegerEntity op);
	
	/**
	 * @param Choco problem instance
	 * @return New integer Choco constraint
	 */
	IntExp getIntExp(Problem problem) throws UnsupportedOperationByChoco;
	
	IntegerInterface getOp1();
	
	IntegerInterface getOp2();
}