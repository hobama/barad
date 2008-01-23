package barad.symboliclibrary.strings;

import java.io.Serializable;

import barad.symboliclibrary.floats.FCONST;
import barad.symboliclibrary.floats.FloatInterface;
import barad.symboliclibrary.integers.ICONST;
import barad.symboliclibrary.integers.IntegerInterface;
import barad.symboliclibrary.test.Descriptor;
import dk.brics.automaton.Automaton;
import dk.brics.automaton.BasicAutomata;

/**
 * Class that represents symbolic string
 * @author svetoslavganov
 */
@SuppressWarnings("unused")
public class SymbolicString extends SymbolicStringEntity implements StringInterface, Serializable {
	private static final long serialVersionUID = 1L; 
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
		this.length = defaultStringLength;
		this.begIndex = 0;
		this.endIndex = defaultStringLength - 1;
		this.automaton = createAutomaton(defaultStringLength);
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
	 * Creates a symbolic string that takes a constant value and a 
	 * definate bounded length
	 * @param string The string value
	 */
	public SymbolicString(String string, int length) {
		super("SymbolicString");
		this.length = length;
		begIndex = 0;
		endIndex = length - 1;
		automaton = BasicAutomata.makeString(string);
		for (int i = string.length(); i < length; i++) {
			automaton = automaton.concatenate(BasicAutomata.makeChar(' '));
		}
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
		descriptor = symbolicString.descriptor;
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
		SymbolicString result = new SymbolicString();
		result.length = length;
		result.begIndex = begIndex.getValue();
		result.endIndex = endIndex;
		result.automaton = automaton.clone();
		result.name = name;
		return result;
	}
	
	/**
	 * Cretes a symbolic string that represents a substring of this one
	 * @param begIndex The beginning index 
	 * @param endIndex The end index exclusive
	 * @return New symbolic string that is substring of this one
	 */
	public StringInterface substring(ICONST begIndex, ICONST endIndex) {
		SymbolicString result = new SymbolicString();
		result.length = length;
		result.begIndex = begIndex.getValue();
		result.endIndex = endIndex.getValue();
		result.automaton = automaton.clone();
		result.name = name;
		return result;
	}
	
	/**
	 * Cretes a symbolic string that is concatenation of two symbolic strings
	 * @param symbolicString Symbolic string that is to be appended to this one
	 * @return New symbolic string that is concatenation of this one and the parmeter
	 */
	public StringInterface concat(StringInterface stringInterface) {
		SymbolicString result = new SymbolicString();
		result.length = length + ((SymbolicString)stringInterface).length;
		result.begIndex = begIndex;
		result.endIndex = endIndex + ((SymbolicString)stringInterface).endIndex;
		result.automaton = automaton.concatenate(((SymbolicString)stringInterface).automaton);
		result.name = name;
		if (descriptor != null) {
			result.descriptor = (Descriptor)descriptor.clone();
		}
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
		clone.name = name;
		if (descriptor != null) {
			clone.descriptor = (Descriptor)descriptor.clone();
		}
		return clone;
	}
	
	/**
	 * Returns a symbolic string that does not begin or end
	 * with a space character
	 * @return The trimmed string
	 */
	public SymbolicString trim() {
		//TODO: Most probably this should be removed
//		String concretized = concretize();
//		int begIndex = this.begIndex;
//		int endIndex = this.endIndex;
//		if (concretized.length() > 0) {
//			while (begIndex < concretized.length() && Character.isWhitespace(concretized.charAt(begIndex))) {
//				begIndex++;
//			}
//			while (endIndex > begIndex && Character.isWhitespace(concretized.charAt(endIndex))) {
//				endIndex--;
//			}
//		}
//		SymbolicString trimmed = new SymbolicString(this);
//		trimmed.setBegIndex(begIndex);
//		trimmed.setEndIndex(endIndex);
//		return trimmed;
		return this;
	}

	@Override
    public String toString() {
		String value = getId();
		String singleton = automaton.getSingleton();
		if (singleton != null) {
			value = value + " Singlton value: " + singleton; 
		}
    	return value;
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
	
	public static StringInterface valueOf(Object obj) {
		if (obj instanceof StringInterface) {
			return (StringInterface)obj; 
		} else {
			throw new IllegalArgumentException();
		}
	} 
	
	public static StringInterface valueOf(IntegerInterface integerInterface) {
		if (integerInterface instanceof ICONST) {
			ICONST iconst = (ICONST)integerInterface;
			return new SymbolicString(String.valueOf(iconst.getValue())); 
		}
		return new SymbolicString(5);
	} 
	
	public static StringInterface valueOf(FloatInterface floatInterface) {
		if (floatInterface instanceof FCONST) {
			FCONST fconst = (FCONST)floatInterface;
			return new SymbolicString(String.valueOf(fconst.getValue())); 
		}
		return new SymbolicString(5);
	} 
}