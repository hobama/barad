package barad.symboliclibrary.floats;

import java.io.Serializable;

/**
 * Class that is inherited by all classes in the
 * library that represent symbolic float operations
 * (primitives and expressions) 
 * @author Svetoslav Ganov
 */
public abstract class FloatOperation extends SymbolicFloatEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	protected FloatInterface op1;
	protected FloatInterface op2;
	protected String operation;

    public FloatOperation(FloatInterface op1, FloatInterface op2, String operation, String name) {
	    super(name);
	    if (op1 == null || op2 == null || operation == null) {
		    throw new IllegalArgumentException();
	    }
	    this.op1 = op1;
	    this.op2 = op2;
	    this.operation = operation;
   }
   
   /**
    * Gets the string equivalent of the expression
    * @return The expression as a string
    */
  	@Override
  	public String toString() {
  		return " (" + op1.toString() + " " + operation + " " + op2.toString() + ") ";
  	}
  	
    @Override
    public Object clone() {
	    return null;
    }
           
    /**
     * Multiplies the current expression with another symbolic
     * integer expression
     * @return New symbolic integer multiplication
     */
    public FMUL FMUL(SymbolicFloatEntity op) {
    	return new FMUL(this, op);
    }

    /**
     * Divides the current expression with another symbolic
     * integer expression
     * @return New symbolic integer division
     */
    public FDIV FDIV(SymbolicFloatEntity op) {
    	return new FDIV(this, op);
    }

    /**
     * Adds the current expression with another symbolic
     * integer expression
     * @return New symbolic integer addition
     */
    public FADD IADD(SymbolicFloatEntity op) {
    	return new FADD(this, op);
    }

    /**
     * Substracts from the current expression with another 
     * symbolic integer expression
     * @return New symbolic integer division
     */
    public FSUB FSUB(SymbolicFloatEntity op) {
    	return new FSUB(this, op);
    }

	public FloatInterface getOp1() {
		return op1;
	}

	public FloatInterface getOp2() {
		return op2;
	}
}
