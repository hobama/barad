package barad.symboliclibrary.path.strings;

import java.io.Serializable;

import barad.symboliclibrary.strings.StringInterface;
import barad.symboliclibrary.strings.SymbolicString;

/**
 * This class represents symbolic constraint "string1".equals("string2")
 * @author svetoslavganov
 */
public class Equals extends SymbolicStringEquality implements Serializable { 
	private static final long serialVersionUID = 1;
	
	/**
	 * Creates new string equals constraint
	 * @param op1 The lhs string in the operation
	 * @param op2 The rhs string in the operation
	 */
	public Equals(StringInterface op1, StringInterface op2) {
		super(op1, op2, Type.EQUAL);
	}

	@Override
	public void evaluate() {
		super.evaluate();
	}

	@Override
	public SymbolicString getConstraintString() {
		return super.getConstraintString();
	}
}
