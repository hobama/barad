package barad.symboliclibrary.string;

import barad.symboliclibrary.common.CommonInterface;

public interface StringInterface extends CommonInterface{

	/**
	 * Cretes a symbolic string that represents a character at specific
	 * position in this one
	 */
	public StringInterface charAt(int index);
	
	/**
	 * Cretes a symbolic string that represents a substring of this one
	 */
	public StringInterface substring(int begIndex);
	
	/**
	 * Cretes a symbolic string that represents a substring of this one
	 */
	public StringInterface substring(int begIndex, int endIndex);
	
	/**
	 * Cretes a symbolic string that is concatenation of two symbolic strings
	 */
	public StringInterface concat(StringInterface stringInterface);
	
	/**
	 * Verifies if this symbolic string can take the specified value
	 */
	public boolean accept(String string);
	
	/**
	 * Generaes the shortest concrete value if such one exists
	 */
	public String concretize();
	
	/**
	 * Returns the beginning position of the string in the automaton
	 * @return
	 */
	public int getBegIndex();
	
	/**
	 * Returns the end position of the string in the automaton
	 * @return
	 */
	public int getEndIndex();
}
