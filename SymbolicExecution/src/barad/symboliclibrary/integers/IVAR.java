package barad.symboliclibrary.integers;

import java.io.Serializable;

import barad.util.Util;

import choco.Problem;
import choco.integer.IntExp;

public class IVAR extends SymbolicIntegerEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public IVAR() {
		super("IVAR");
	}
	
	@Override
	public Object clone() {
		IVAR ivar = new IVAR();
		ivar.name = name;
		return ivar;
	}
	
	@Override
	public String toString() {
		return getId();
	}
	
	public IADD IADD(SymbolicIntegerEntity op) {
		return null;
	}

	public ISUB ISUB(SymbolicIntegerEntity op) {
		return null;
	}

	public IDIV IDIV(SymbolicIntegerEntity op) {
		return null;
	}

	public IMUL IMUL(SymbolicIntegerEntity op) {
		return null;
	}

	public String getStrValue() {
		return getName();
	}
	
	public IntExp getIntExp(Problem problem) {
		int min = -100;
		int max = 100;
		try {
			min = Integer.parseInt(Util.getProperties().getProperty("integers.min", "-100"));
			max = Integer.parseInt(Util.getProperties().getProperty("integers.max", "100"));
		} catch (NumberFormatException nfe) {
			/*ignore*/
		}
		return problem.makeBoundIntVar(getId(), min, max);
	}

	public IntegerInterface getOp1() {
		// TODO Auto-generated method stub
		return null;
	}

	public IntegerInterface getOp2() {
		// TODO Auto-generated method stub
		return null;
	}
}
