package barad.symboliclibrary.floats;

import barad.symboliclibrary.common.CommonInterface;

public interface FloatInterface extends CommonInterface {   
	/**
	 * @param Instance that implements FloatInterface
	 * @return New float multiplication operation
	 */
	FMUL FMUL(SymbolicFloatEntity op);

	/**
	 * @param Instance that implements FloatInterface
	 * @return New float division operation
	 */
	FDIV FDIV(SymbolicFloatEntity op);

	/**
	 * @param Instance that implements FloatInterface
	 * @return New float addition operation
	 */
	FADD IADD(SymbolicFloatEntity op);

	/**
	 * @param Instance that implements FloatInterface
	 * @return New float difference operation
	 */
	FSUB FSUB(SymbolicFloatEntity op);
}