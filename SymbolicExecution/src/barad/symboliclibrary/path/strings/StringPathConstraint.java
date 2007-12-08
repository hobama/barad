package barad.symboliclibrary.path.strings;

import barad.symboliclibrary.common.CommonInterface;
import barad.symboliclibrary.common.ConstraintType;
import barad.symboliclibrary.common.SymbolicEntity;
import barad.symboliclibrary.path.PathConstraintInterface;
import barad.symboliclibrary.string.StringInterface;
import barad.symboliclibrary.string.SymbolicString;

/**
 * Class that is should be extended by all string path constraints
 * @author svetoslavganov
 */
public abstract class StringPathConstraint extends SymbolicEntity implements PathConstraintInterface, CommonInterface {
	protected StringInterface op1;
	protected StringInterface op2;
	protected String operator;
	protected boolean validInCurrentContext;
	protected final ConstraintType type = ConstraintType.IF;
	
	public StringPathConstraint(StringInterface op1, StringInterface op2, String operator, String name) {
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
	
	public abstract SymbolicString getConstraintString();
	
   /**
	* Gets the string equivalent of the expression
	* @return The expression as a string
	*/
	@Override
	public String toString() {
		  return op1.toString() + "." + operator + "(" + op2.toString() + ")";
	}

	public StringPathConstraint inverse() {
		return null;
	}

	public StringInterface getOp1() {
		return op1;
	}

	public void setOp1(SymbolicString op1) {
		this.op1 = op1;
	}

	public StringInterface getOp2() {
		return op2;
	}

	public void setOp2(SymbolicString op2) {
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
