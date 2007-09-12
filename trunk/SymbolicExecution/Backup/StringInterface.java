package barad.symboliclibrary.string;

import java.util.ArrayList;

import barad.symboliclibrary.common.CommonInterface;

public interface StringInterface extends CommonInterface {
	/**
	 * Greates new substring operation
	 * @param Beginning index
	 * @param End index
	 * @return New symbolic string variable
	 */
    //SVAR substring(int beg, int end);
	/**
	 * Concatenates two strings
	 * @param Instance that implements StringInterface
	 * @return New symbolic string variable
	 */
    //SVAR CONCAT(StringInterface s);
	/**
	 * Returns an enumeration with possible values
	 * @return All possible value for a symbolic string
	 * entity. If set enumeration it has precedence over
	 * the set representation
	 */
    ArrayList<String> getEnumeration();
    
	/**
	 * Set an enumeration with possible values
	 * @param Enumeration with possible values
	 */
    void setEnumeration(ArrayList<String> enumeration);
}
