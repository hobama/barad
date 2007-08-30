package barad.symboliclibrary.integer;

import java.io.Serializable;

/**
 * Class that implements symbolic integer constant
 * 
 * @author Svetoslav Ganov
 */
public class ICONST extends SymbolicIntegerEntity implements Serializable { 
	private static final long serialVersionUID = 1L;
	private int value;

	public ICONST(int value) {
		super("ICONST");
        this.value = value;
	}
	
	@Override
	public Object clone() {
		ICONST iconst = new ICONST(value);
		iconst.setName(this.getName());
		return iconst;
	}
        
    public String getStrValue() {
    	return getName() + " " + String.valueOf(value);
    }

    public boolean Satisfiable() {
    	return true;
    }

    //AUXILIARY
    public int getValue() {
    	return value;
    }
    
    public void setValue(int value) {
    	this.value = value;
    }
       
    // ARITHMETIC
    public IMUL IMUL(SymbolicIntegerEntity op) {
    	return new IMUL(this, op);
    }

    public IDIV IDIV(SymbolicIntegerEntity op) {
    	return new IDIV(this, op);
    }

    public IADD IADD(SymbolicIntegerEntity op) {
    	return new IADD(this, op);
    }

    public IDIF IDIF(SymbolicIntegerEntity op) {
    	return new IDIF(this, op);
    }

    //LOGICAL
    /*
    public IAND IAND(SymbolicIntegerEntity op) {
    	return new IAND(this, op);
    }

    public IOR IOR(SymbolicIntegerEntity op) {
    	return new IOR(this, op);
    }
	
    //COMPARISON
    public IEQ IEQ(SymbolicIntegerEntity op) {
    	return new IEQ(this, op);
    }

    public IGT IGT(SymbolicIntegerEntity op) {
    	return new IGT(this, op);
    }

    public ILT ILT(SymbolicIntegerEntity op) {
    	return new ILT(this, op);
    }

    public ILTEQ ILTEQ(SymbolicIntegerEntity op) {
    	return new ILTEQ(this, op);
    }

    public IGTEQ IGTEQ(SymbolicIntegerEntity op) {
    	return new IGTEQ(this, op);
    }
    */
}