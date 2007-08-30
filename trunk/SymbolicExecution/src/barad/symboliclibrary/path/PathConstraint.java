package barad.symboliclibrary.path;

import barad.symboliclibrary.common.CommonInterface;
import barad.symboliclibrary.common.ConstraintType;
import barad.symboliclibrary.common.SymbolicEntity;
import barad.symboliclibrary.integer.IntegerInterface;

public abstract class PathConstraint extends SymbolicEntity implements CommonInterface {
	protected IntegerInterface op1;
	protected IntegerInterface op2;
	protected String operator;
	protected boolean validInCurrentContext;
	protected final ConstraintType type = ConstraintType.IF;
	
	public PathConstraint(IntegerInterface op1, IntegerInterface op2, String operator, String name) {
		super(name);
		this.op1 = op1;
		this.op2 = op2;
		this.operator = operator;
		validInCurrentContext = false;
	}
	
	@Override
	public Object clone() {
		return null;
	}
	
   /**
	* Gets the string equivalent of the expression
	* 
	* @return The expression as a string
	*/
	@Override
	public String toString() {
		  return " (" + op1.toString() + " " + operator + " " + op2.toString() + ") ";
	}
	
   /**
	* Gets the unique id of the expression
	* 
	* @return The id of the expression
	*/
	public String getId() {
		return super.getId();
	}

   /**
	* Gets the name of the expression
	* 
	* @return The name of the expression
	*/
	public String getName() {
		 return super.getName();
	 }

	public PathConstraint inverse() {
		return null;
	}

	public IntegerInterface getOp1() {
		return op1;
	}

	public void setOp1(IntegerInterface op1) {
		this.op1 = op1;
	}

	public IntegerInterface getOp2() {
		return op2;
	}

	public void setOp2(IntegerInterface op2) {
		this.op2 = op2;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public boolean isValidInCurrentContext() {
		return validInCurrentContext;
	}

	public void setValidInCurrentContext(boolean validInCurrentContext) {
		this.validInCurrentContext = validInCurrentContext;
	}
	
	public ConstraintType getType() {
		return this.type;
	}
}
