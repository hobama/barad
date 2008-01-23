package barad.symboliclibrary.floats;

import java.io.Serializable;

import barad.util.Util;
import choco.Problem;
import choco.real.RealExp;

/**
 * Class that represents smbolic float constant
 * @author svetoslavganov
 *
 */
public class FVAR extends SymbolicFloatEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private static double MIN;
	private static double MAX;
	private double min;
	private double max;
	static {
		MIN = Integer.parseInt(Util.getProperties().getProperty("doubles.min", "-100"));
		MAX = Integer.parseInt(Util.getProperties().getProperty("doubles.max", "100"));
	}
	
	public FVAR() {
		super("FVAR");
		this.min = MIN;
		this.max = MAX;
	}
	
	@Override
	public Object clone() {
		FVAR fvar = new FVAR();
		fvar.name = name;
		return fvar;
	}
	
	@Override
	public String toString() {
		return getId();
	}
	
	public FADD IADD(SymbolicFloatEntity op) {
		return null;
	}

	public FSUB FSUB(SymbolicFloatEntity op) {
		return null;
	}

	public FDIV FDIV(SymbolicFloatEntity op) {
		return null;
	}
	
	public FMUL FMUL(SymbolicFloatEntity op) {
		return null;
	}
   
	public String getStrValue() {
		return getName();
	}
	
	public RealExp getRealExp(Problem problem) {
		double min = -100;
		double max = 100;
		try {
			min = Float.parseFloat(Util.getProperties().getProperty("doubles.min", "-100"));
			max = Float.parseFloat(Util.getProperties().getProperty("doubles.max", "100"));
		} catch (NumberFormatException nfe) {
			/*ignore*/
		}
		return problem.makeRealVar(getId(), min, max);
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}
}
