package barad.symboliclibrary.integers;

import java.io.Serializable;

import barad.util.Util;

import choco.Problem;
import choco.integer.IntExp;

public class IVAR extends SymbolicIntegerEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private static int MIN;
	private static int MAX;
	private int min;
	private int max;
	static {
		MIN = Integer.parseInt(Util.getProperties().getProperty("integers.min", "-100"));
		MAX = Integer.parseInt(Util.getProperties().getProperty("integers.max", "100"));
	}
	
	public IVAR() {
		super("IVAR");
		this.min = MIN;
		this.max = MAX;
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
	
	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}
}
