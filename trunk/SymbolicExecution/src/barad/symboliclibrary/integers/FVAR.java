package barad.symboliclibrary.floats;

import java.io.Serializable;

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

	public boolean Satisfiable() {
		return false;
	}
}
