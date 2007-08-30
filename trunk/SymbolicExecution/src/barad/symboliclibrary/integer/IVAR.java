package barad.symboliclibrary.integer;

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
		// TODO Auto-generated method stub
		return null;
	}
	/*
	public IAND IAND(SymbolicIntegerEntity op) {
		// TODO Auto-generated method stub
		return null;
	}
	*/
	public IDIF IDIF(SymbolicIntegerEntity op) {
		// TODO Auto-generated method stub
		return null;
	}

	public IDIV IDIV(SymbolicIntegerEntity op) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	public IEQ IEQ(SymbolicIntegerEntity op) {
		// TODO Auto-generated method stub
		return null;
	}

	public IGT IGT(SymbolicIntegerEntity op) {
		// TODO Auto-generated method stub
		return null;
	}

	public IGTEQ IGTEQ(SymbolicIntegerEntity op) {
		// TODO Auto-generated method stub
		return null;
	}

	public ILT ILT(SymbolicIntegerEntity op) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ILTEQ ILTEQ(SymbolicIntegerEntity op) {
		// TODO Auto-generated method stub
		return null;
	}
	*/
	public IMUL IMUL(SymbolicIntegerEntity op) {
		// TODO Auto-generated method stub
		return null;
	}
    /*
	public IOR IOR(SymbolicIntegerEntity op) {
		// TODO Auto-generated method stub
		return null;
	}
	*/
	public String getStrValue() {
		return getName();
	}

	public boolean Satisfiable() {
		// TODO Auto-generated method stub
		return false;
	}
}
