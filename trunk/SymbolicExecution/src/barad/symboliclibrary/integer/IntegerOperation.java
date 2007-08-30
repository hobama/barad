package barad.symboliclibrary.integer;

import java.io.Serializable;

/**
 * Class that is inherited by all classes in the
 * library that represent symbolic integer operations
 * (primitives and expressions) 
 * 
 * @author Svetoslav Ganov
 */
public abstract class IntegerOperation extends SymbolicIntegerEntity implements Serializable {
	private static final long serialVersionUID = 1;
	protected IntegerInterface op1;
	protected IntegerInterface op2;
	protected String operation;

    public IntegerOperation(IntegerInterface op1, IntegerInterface op2, String operation, String name) {
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
    * 
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
     * 
     * @return New symbolic integer multiplication
     */
    public IMUL IMUL(SymbolicIntegerEntity op) {
    	return new IMUL(this, op);
    }

    /**
     * Divides the current expression with another symbolic
     * integer expression
     * 
     * @return New symbolic integer division
     */
    public IDIV IDIV(SymbolicIntegerEntity op) {
    	return new IDIV(this, op);
    }

    /**
     * Adds the current expression with another symbolic
     * integer expression
     * 
     * @return New symbolic integer addition
     */
    public IADD IADD(SymbolicIntegerEntity op) {
    	return new IADD(this, op);
    }

    /**
     * Substracts from the current expression with another 
     * symbolic integer expression
     * 
     * @return New symbolic integer division
     */
    public IDIF IDIF(SymbolicIntegerEntity op) {
    	return new IDIF(this, op);
    }
}
