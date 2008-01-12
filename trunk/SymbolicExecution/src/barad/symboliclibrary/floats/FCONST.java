package barad.symboliclibrary.floats;

import java.io.Serializable;

import choco.Problem;
import choco.real.RealExp;

/**
 * Class that implements symbolic float constant
 * @author Svetoslav Ganov
 */
public class FCONST extends SymbolicFloatEntity implements Serializable { 
	private static final long serialVersionUID = 1L;
	private float value;

	public FCONST(float value) {
		super("FCONST");
        this.value = value;
	}
	
	@Override
	public Object clone() {
		FCONST fconst = new FCONST(value);
		fconst.name = name;
		return fconst;
	}
     
	@Override
    public String toString() {
    	return getName() + " " + String.valueOf(value);
    }

    public float getValue() {
    	return value;
    }
    
    public void setValue(float value) {
    	this.value = value;
    }
       
    public FMUL FMUL(SymbolicFloatEntity op) {
    	return new FMUL(this, op);
    }

    public FDIV FDIV(SymbolicFloatEntity op) {
    	return new FDIV(this, op);
    }

    public FADD IADD(SymbolicFloatEntity op) {
    	return new FADD(this, op);
    }

    public FSUB FSUB(SymbolicFloatEntity op) {
    	return new FSUB(this, op);
    }
    
	public RealExp getRealExp(Problem problem) {
		return problem.makeRealVar(getId(), value, value);
	}
}