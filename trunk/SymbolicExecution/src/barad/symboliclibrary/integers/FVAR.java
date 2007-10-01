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

	public FVAR() {
		super("FVAR");
	}
	
	@Override
	public Object clone() {
		FVAR fvar = new FVAR();
		fvar.setName(this.getName());
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
		return problem.makeRealVar(getName(), min, max);
	}
}
