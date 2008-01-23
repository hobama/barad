package barad.symboliclibrary.integers;

import java.io.Serializable;

import choco.Problem;
import choco.integer.IntExp;

/**
 * Class that implements symbolic integer constant
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
		iconst.name = name;
		return iconst;
	}
     
	@Override
    public String toString() {
    	return getName() + " " + String.valueOf(value);
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

    public ISUB ISUB(SymbolicIntegerEntity op) {
    	return new ISUB(this, op);
    }

	public IntExp getIntExp(Problem problem) {
		return problem.makeConstantIntVar(getName(), value);
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