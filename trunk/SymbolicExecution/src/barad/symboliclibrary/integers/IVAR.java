package barad.symboliclibrary.integers;

import java.io.Serializable;

public class IVAR extends SymbolicIntegerEntity implements Serializable {
	private static final long serialVersionUID = 1;

	public IVAR() {
		super("IVAR");
	}
	
	@Override
	public Object clone() {
		IVAR ivar = new IVAR();
		ivar.setName(this.getName());
		return ivar;
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

	public boolean Satisfiable() {
		return false;
	}
}
