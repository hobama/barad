package barad.symboliclibrary.string;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;

import barad.symboliclibrary.integers.ICONST;

import dk.brics.automaton.Automaton;
import dk.brics.automaton.BasicAutomata;

import static barad.util.Properties.DEBUG;

/**
 * Class that represents symbolic string
 * @author svetoslavganov
 */
@SuppressWarnings("unused")
public class SymbolicString extends SymbolicStringEntity implements StringInterface, Serializable {
	private static final long serialVersionUID = 1L; 
	private Logger log;
	private int length;
	private int begIndex;
	private int endIndex;
	private Automaton automaton;
	private static final int defaultStringLength = Integer.parseInt(System.getProperty("default.string.length","50"));
	
	/**
	 * Create a symbolic string that has any value and the default length
	 */
	public SymbolicString() {
		super("SymbolicString");
		length = defaultStringLength;
		begIndex = 0;
		endIndex = defaultStringLength - 1;
		automaton = createAutomaton(defaultStringLength);
	}
	
	/**
	 * Create a symbolic string that has any value and specified length
	 * @param length The maximal length of the string, -1 for unbounded
	 */
	public SymbolicString(int length) {
		super("SymbolicString");
		this.length = length;
		begIndex = 0;
		endIndex = length - 1;
		automaton = createAutomaton(length);
	}
	
	/**
	 * Creates a symbolic string that takes a constant value
	 * @param string The string value
	 */
	public SymbolicString(String string) {
		super("SymbolicString");
		length = string.length();
		begIndex = 0;
		endIndex = string.length() - 1;
		automaton = BasicAutomata.makeString(string);
	}
	
	/**
	 * Cretes a symbolic string from another one
	 * @param symbolicString The other symbolic string
	 */
	public SymbolicString(SymbolicString symbolicString) {
		super("SymbolicString");
		length = symbolicString.length;
		begIndex = symbolicString.begIndex;
		endIndex = symbolicString.endIndex;
		automaton = symbolicString.automaton.clone();
	}
	
	/**
	 * Creates an automaton. If there is no specified length limit
	 * the created automaton accepts any string. Otherwise, it accepts any string
	 * If length is 0 the created automaton does not accept ant strings
	 * with length less than or equal to the specified limit
	 * @param length The length of the strings accepted by this automaton
	 * @return New automaton
	 */
	private Automaton createAutomaton(int length) {
		Automaton result = null;
		if (length > 0) {
			result = BasicAutomata.makeAnyChar();
			for (int i = 0; i < length - 1; i++) {
				result = result.concatenate(BasicAutomata.makeAnyChar());
			}
		} else if (length == 0) {
			result = BasicAutomata.makeEmpty();
		} else {
			result = BasicAutomata.makeAnyString();
		}
		return result;
	}
	
	/**
	 * Cretes a symbolic string that represents a character at specific
	 * position in this one
	 * @return New symbolic string that is the character at a specified position 
	 *         in this one
	 */
	public StringInterface charAt(ICONST index) {
		if (DEBUG) {
			System.out.println("CahrAt(" + index + ") Invoked");
		}
		SymbolicString result = new SymbolicString();
		result.length = length;
		result.begIndex = index.getValue();
		result.endIndex = index.getValue() + 1;
		result.automaton = automaton.clone();
		return result;
	}

	/**
	 * Cretes a symbolic string that represents a substring of this one
	 * @param begIndex The beginning index
	 * @return New symbolic string that is substring of this one
	 */
	public StringInterface substring(ICONST begIndex) {	
		if (DEBUG) {
			System.out.println("substring(" + begIndex.getValue() + ") Invoked");
		}
		SymbolicString result = new SymbolicString();
		result.length = length;
		result.begIndex = begIndex.getValue();
		result.endIndex = endIndex;
		result.automaton = automaton.clone();
		return result;
	}
	
	/**
	 * Cretes a symbolic string that represents a substring of this one
	 * @param begIndex The beginning index 
	 * @param endIndex The end index exclusive
	 * @return New symbolic string that is substring of this one
	 */
	public StringInterface substring(ICONST begIndex, ICONST endIndex) {
		if (DEBUG) {
			System.out.println("substring(" + begIndex.getValue() + ", " + endIndex.getValue() + ") Invoked");
		}
		SymbolicString result = new SymbolicString();
		result.length = length;
		result.begIndex = begIndex.getValue();
		result.endIndex = endIndex.getValue();
		result.automaton = automaton.clone();
		return result;
	}
	
	/**
	 * Cretes a symbolic string that is concatenation of two symbolic strings
	 * @param symbolicString Symbolic string that is to be appended to this one
	 * @return New symbolic string that is concatenation of this one and the parmeter
	 */
	public StringInterface concat(StringInterface stringInterface) {
		if (DEBUG) {
			log.info("concat() Invoked");
		}
		SymbolicString result = new SymbolicString();
		result.length = length + ((SymbolicString)stringInterface).length;
		result.begIndex = begIndex;
		result.endIndex = endIndex + ((SymbolicString)stringInterface).endIndex;
		result.automaton = automaton.concatenate(((SymbolicString)stringInterface).automaton);
		return result;
	}

	/**
	 * NOTE: For testing purposes
	 * TODO: Remove once entirely tested
	 * Checks if the symbolic string could take a certain concrete value
	 * @param value The value to be tested for acceptance
	 * @return true if the symbolic string colud take such value, false otherwise
	 */
	public boolean accept(String value) {
		return automaton.run(value);
	}

	/**
	 * Generaes the shortest concrete value if such one exists
	 * @return The shortest concrete value, null otherwise
	 */
	public String concretize() {
		String result = automaton.getShortestExample(true);
		if (result != null) {
			if (result.length() > endIndex) {
				result = result.substring(begIndex, endIndex + 1);
			} else if (!result.equals("")){
				result = null;
			}
		}
		return result; 
	}
	
	@Override
	public Object clone() {
		SymbolicString clone = new SymbolicString();
		clone.length = length;
		clone.begIndex = begIndex;
		clone.endIndex = endIndex;
		clone.automaton = automaton.clone();
		return clone;
	}

	@Override
    public String toString() {
    	return getId();
    }
	public Automaton getAutomaton() {
		return automaton;
	}

	public void setAutomaton(Automaton automaton) {
		this.automaton = automaton;
	}

	public int getBegIndex() {
		return begIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public void setBegIndex(int begIndex) {
		this.begIndex = begIndex;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}
}