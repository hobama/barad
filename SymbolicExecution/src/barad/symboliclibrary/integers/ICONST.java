package barad.symboliclibrary.integers;

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
     
	@Override
    public String toString() {
    	return getName() + " " + String.valueOf(value);
    }

    public boolean Satisfiable() {
    	return true;
    }

    public int getValue() {
    	return value;
    }
    
    public void setValue(int value) {
    	this.value = value;
    }
       
    public IMUL IMUL(SymbolicIntegerEntity op) {
    	return new IMUL(this, op);
    }

    public IDIV IDIV(SymbolicIntegerEntity op) {
    	return new IDIV(this, op);
    }

    public IADD IADD(SymbolicIntegerEntity op) {
    	return new IADD(this, op);
    }

    public ISUB IDIF(SymbolicIntegerEntity op) {
    	return new ISUB(this, op);
    }
}