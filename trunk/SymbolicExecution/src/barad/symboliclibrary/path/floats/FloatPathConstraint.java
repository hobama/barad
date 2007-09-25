package barad.symboliclibrary.path.floats;

import barad.symboliclibrary.common.CommonInterface;
import barad.symboliclibrary.common.ConstraintType;
import barad.symboliclibrary.common.SymbolicEntity;
import barad.symboliclibrary.floats.FloatInterface;
import barad.symboliclibrary.path.PathConstraintInterface;

/**
 * Class that is should be extended by all float path constraints
 * @author svetoslavganov
 */
public abstract class FloatPathConstraint extends SymbolicEntity implements PathConstraintInterface, CommonInterface {
	protected FloatInterface op1;
	protected FloatInterface op2;
	protected String operator;
	protected boolean validInCurrentContext;
	protected final ConstraintType type = ConstraintType.IF;
	
	public FloatPathConstraint(FloatInterface op1, FloatInterface op2, String operator, String name) {
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
	* @return The expression as a string
	*/
	@Override
	public String toString() {
		  return " (" + op1.toString() + " " + operator + " " + op2.toString() + ") ";
	}

	public FloatPathConstraint inverse() {
		return null;
	}

	public FloatInterface getOp1() {
		return op1;
	}

	public void setOp1(FloatInterface op1) {
		this.op1 = op1;
	}

	public FloatInterface getOp2() {
		return op2;
	}

	public void setOp2(FloatInterface op2) {
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
