package barad.symboliclibrary.path.strings;

import static barad.util.Properties.DEBUG;
import static barad.util.Properties.VERBOSE;

import java.io.Serializable;

import org.apache.log4j.Logger;

import barad.symboliclibrary.strings.StringInterface;
import barad.symboliclibrary.strings.SymbolicString;
import dk.brics.automaton.Automaton;
import dk.brics.automaton.BasicAutomata;

/**
 * This class represents the basic symbolic class for constraints 
 * "string1".equals("string2") and !"string1".equals("string2")
 * @author svetoslavganov
 */
public class SymbolicStringEquality extends StringPathConstraint implements Serializable {
	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(getClass()); 
	private SymbolicString constraintString;
	private Type type;
	
	/**
	 * Creates new string equals constraint
	 * @param op1 The lhs string in the operation
	 * @param op2 The rhs string in the operation
	 */
	public SymbolicStringEquality(StringInterface op1, StringInterface op2, Type type) {
		super(op1, op2, type.getValue(), type.getValue());
		constraintString = new SymbolicString(-1);
		this.type = type;
	}
	
	/**
	 * Constructor that is used for cloning
	 */
	private SymbolicStringEquality(Type type) {
		super(null, null, type.getValue(), type.getValue());
		this.type = type;
	}
	
	/**
	 * Creates the inversed path constraint
	 * @return New Equals constraint that is the negation of the current one
	 * i.e. Equals.clone() => !Equals.clone()
	 */
	@Override
	public StringPathConstraint inverse() {
		StringPathConstraint clone = this;
		if (inverseCounter < 2) {
			inverseCounter++;
			if (type == Type.EQUAL) {
				type = Type.NOT_EQUAL;
			} else {
				type = Type.EQUAL;
			}
		    clone = (StringPathConstraint)clone();
		}
	    return clone;
	}
	
	/**
	 * Creates a clone of this Equals 
	 * @return A clone of this Equals
	 */
	@Override
	public Object clone() {
		SymbolicStringEquality clone = new SymbolicStringEquality(type);
		clone.constraintString = (SymbolicString)constraintString.clone();
		clone.op1 = (SymbolicString)op1.clone();
		clone.op2 = (SymbolicString)op2.clone();
		clone.type = type;
		clone.name = name;
		clone.inverseCounter = inverseCounter;
		if (type == Type.NOT_EQUAL)
			clone.operator = Type.EQUAL.getValue();
		else {
			clone.operator = Type.NOT_EQUAL.getValue();
		}
		return clone;
	}
	
	/**
	 * Evaluates the constrain and constructs an automaton that represents it
	 * @param isEquals true if the instance represents equals(), false if the 
	 * instance represents not equals
	 */
	public void evaluate() {	
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
			constraintString.setDescriptor(lhs.getDescriptor());
			if (type == Type.NOT_EQUAL) {
				constraintString.setAutomaton(lhs.getAutomaton().intersection(rhs.getAutomaton()));
				constraintString.setBegIndex(op1.getBegIndex());
				constraintString.setEndIndex(op1.getEndIndex());
			} else {
				constraintString.setAutomaton(lhs.getAutomaton().minus(rhs.getAutomaton()));
				constraintString.setBegIndex(op1.getBegIndex());
				constraintString.setEndIndex(op1.getEndIndex());
			}
			if (DEBUG && VERBOSE) { 
				log.info("Concrete value: " + concretize());
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
	
	public enum Type {
		EQUAL("equal"), 
		NOT_EQUAL("!Equal");
		
		private String value;
		
		private Type(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}
	}
}


