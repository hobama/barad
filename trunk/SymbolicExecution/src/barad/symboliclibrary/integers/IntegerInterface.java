package barad.symboliclibrary.integers;

import barad.symboliclibrary.common.CommonInterface;

public interface IntegerInterface extends CommonInterface {   
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
}