package barad.symboliclibrary.path.integers;

import choco.Problem;
import choco.integer.IntConstraint;
import barad.symboliclibrary.common.CommonInterface;
import barad.symboliclibrary.common.ConstraintType;
import barad.symboliclibrary.common.SymbolicEntity;
import barad.symboliclibrary.integers.IntegerInterface;
import barad.symboliclibrary.integers.UnsupportedOperationByChoco;
import barad.symboliclibrary.path.PathConstraintInterface;

/**
 * Class that is should be extended by all integer path constraints
 * @author svetoslavganov
 */
public abstract class IntegerPathConstraint extends SymbolicEntity implements PathConstraintInterface, CommonInterface {
	protected IntegerInterface op1;
	protected IntegerInterface op2;
	protected String operator;
	protected boolean validInCurrentContext;
	protected final ConstraintType type = ConstraintType.IF;
	
	public IntegerPathConstraint(IntegerInterface op1, IntegerInterface op2, String operator, String name) {
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
	 * Returns Choco integer constraint that represents 
	 * this integer constriant
	 * @param problem Choco Problem instance
	 * @return New Choco integer constraint instance
	 */
	public abstract IntConstraint getIntConstraint(Problem problem) throws UnsupportedOperationByChoco;
	
   /**
	* Gets the string equivalent of the expression
	* @return The expression as a string
	*/
	@Override
	public String toString() {
		  return " (" + op1.toString() + " " + operator + " " + op2.toString() + ") ";
	}

	public IntegerPathConstraint inverse() {
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
