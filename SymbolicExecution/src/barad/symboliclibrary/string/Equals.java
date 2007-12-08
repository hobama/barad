package barad.symboliclibrary.string;

import static barad.util.Properties.DEBUG;

import java.io.Serializable;

import barad.symboliclibrary.path.strings.StringPathConstraint;
import dk.brics.automaton.Automaton;
import dk.brics.automaton.BasicAutomata;

/**
 * This class represents symbolic constraints "string1".equals("string2") and
 * !"string1".equals("string2")
 * @author svetoslavganov
 */
@SuppressWarnings("unused")
public class Equals extends StringPathConstraint implements Serializable {
	private static final long serialVersionUID = 1;
	private boolean isEquals;
	private SymbolicString constraintString;

	/**
	 * Creates new string equals constraint
	 * @param op1 The lhs string in the operation
	 * @param op2 The rhs string in the operation
	 */
	public Equals(StringInterface op1, StringInterface op2) {
		super(op1, op2, "equals", "equals");
		constraintString = new SymbolicString(-1);
	}
	
	/**
	 * Constructor that is used for cloning
	 */
	private Equals() {
		super(null, null, "equals", "equals");
	}
	
	/**
	 * Creates the inversed path constraint
	 * @return New Equals constraint that is the negation of the current one
	 * i.e. Equals.clone() => !Equals.clone()
	 */
	@Override
	public StringPathConstraint inverse() {
		isEquals = !isEquals;
		evaluate(isEquals);
	    StringPathConstraint clone = (StringPathConstraint)clone();
	    if(!isEquals) {
	    	clone.setOperator("!equals");
		}
	    return clone;
	}
	
	/**
	 * Creates a clone of this Equals 
	 * @return A clone of this Equals
	 */
	@Override
	public Object clone() {
		Equals equals = new Equals();
		equals.constraintString = (SymbolicString)constraintString.clone();
		equals.op1 = (SymbolicString)op1.clone();
		equals.op2 = (SymbolicString)op2.clone();
		equals.isEquals = isEquals;
		return equals;
	}
	
	/**
	 * Evaluates the constrain and constructs an automaton that represents it
	 * @param isEquals true if the instance represents equals(), false if the 
	 * instance represents not equals
	 */
	public void evaluate(boolean isEquals) {	
		int begIndexDifference = Math.abs(op1.getBegIndex() - op2.getBegIndex());
		if ((op1.getEndIndex() - op1.getBegIndex()) < (op2.getEndIndex() - op2.getBegIndex())) {
			constraintString.setAutomaton(BasicAutomata.makeEmpty());
		} else {
			SymbolicString lhs = (SymbolicString)op1.clone();
			SymbolicString rhs = (SymbolicString)op2.clone();
			if (lhs.getBegIndex() > rhs.getBegIndex()) {
				rhs.setAutomaton(extendAutomaton(rhs.getAutomaton(), begIndexDifference, true));
				rhs.setBegIndex(rhs.getBegIndex() + begIndexDifference);
				rhs.setEndIndex(rhs.getEndIndex() + begIndexDifference);
				rhs.setLength(rhs.getLength() + begIndexDifference);
			} else if (lhs.getBegIndex() < rhs.getBegIndex()) {
				lhs.setAutomaton(extendAutomaton(lhs.getAutomaton(), begIndexDifference, true));
				lhs.setBegIndex(lhs.getBegIndex() + begIndexDifference);
				lhs.setEndIndex(lhs.getEndIndex() + begIndexDifference);
				lhs.setLength(lhs.getLength() + begIndexDifference);
			}
			int endIndexDifference = Math.abs(lhs.getLength() - rhs.getLength());
			if (lhs.getLength() > rhs.getLength()) {
				rhs.setAutomaton(extendAutomaton(rhs.getAutomaton(), endIndexDifference, false));
				rhs.setLength(rhs.getLength() + endIndexDifference);
			} else if (lhs.getLength() < rhs.getLength()){
				lhs.setAutomaton(extendAutomaton(lhs.getAutomaton(), endIndexDifference, false));
				lhs.setLength(lhs.getLength() + endIndexDifference);
			}
			constraintString = lhs;
			if (isEquals) {
				constraintString.setAutomaton(lhs.getAutomaton().intersection(rhs.getAutomaton()));
				constraintString.setBegIndex(op1.getBegIndex());
				constraintString.setEndIndex(op1.getEndIndex());
			} else {
				constraintString.setAutomaton(lhs.getAutomaton().minus(rhs.getAutomaton()));
				constraintString.setBegIndex(op1.getBegIndex());
				constraintString.setEndIndex(op1.getEndIndex());
			}
			if (DEBUG) { 
				System.out.println("Concrete value: " + concretize());
			}
		}
	}
	
	/**
	 * Returns if the constraint is valid i.e. that there is at least
	 * one concrete value thas satisfies it
	 * @return true if at least on value is satisfies the constraint,
	 * false otherwise
	 */
	public boolean isValid() {
		String result = concretize();
		return !(constraintString.concretize() == null);
	}
	
	/**
	 * Retuens the shortest value that satifies this constraint 
	 * @return The concrete value, null otherwise
	 */
	public String concretize() {
		return constraintString.concretize();
	}
	
	/**
	 * Extends an automaton with specified characters as prefix or postfix
	 * @param automaton The automaton to be extended
	 * @param characters The number of characters
	 * @param beginning true as prefix, false as postfix
	 * @return The extended automaton
	 */
	private Automaton extendAutomaton(Automaton automaton, int characters, boolean beginning) {
		for (int i = 0; i < characters; i++) {
			if (beginning) {
				automaton = BasicAutomata.makeAnyChar().concatenate(automaton);
			} else {
				automaton = automaton.concatenate(BasicAutomata.makeAnyChar());
			}
		}
		return automaton;
	}
	
	/**
	 * Returns the symbolic string that represents the constraint
	 * @return The symbolic string that represents the constraint
	 */
	public SymbolicString getConstraintString() {
		return constraintString;
	}
}
