package barad.symboliclibrary.string;

public class CHAR {
	private Values values;
	private CharacterType type;

	/**
	 * Creates a new symbolic character with one possible
	 * value i.e. character constant
	 * @param indexACII ASII code of the charater
	 */
	public CHAR(int indexACII, CharacterType type) {
	    this.type = type;
	    values = new Values();
	    values.setValue(indexACII, true);
	}
	
	/**
	 * Create a new symbolic character with specified type
	 * @param type The type
	 */
	public CHAR(CharacterType type) {
	    this.type = type;
	    values = new Values();
	    Reset();
	}
	
	
	/**
	 * Sets the possible values for that character to the
	 * intersection of its possible values with the ones
	 * of another character
	 * @param otherChar Another character
	 * @return true if values sets intersect, false otherwise
	 */
    public boolean intersection(CHAR otherChar) {
		boolean intersect = false;
    	for (int i = 0; i < 256; i++) {
			if (values.getValue(i)) { 
				if (!otherChar.values.getValue(i)) {
					values.setValue(i, false);
				} else {
					intersect = true;
				}
			}
		}
    	return intersect;
    }

    /**
	 * Sets the possible values for that character to the
	 * disjunction of its possible values with the ones
	 * of another character
	 * @param otherChar Another character
	 * @return true if this charater has value not present in the
	 * set of possible values of the other symbolic charater, 
	 * false otherwise
	 */
	public boolean disjunction(CHAR otherChar) {
    	for (int i = 0; i < 256; i++) {
			if (values.getValue(i) && otherChar.values.getValue(i)) {
				values.setValue(i, false);
			}
		}
    	return values.size == 0;        
	}
	   
	/**
	 * Reset the character possible values
	 */
    public void Reset() { 
    	switch (type) {
	   		case ALL:
	   			initAll();
	   	    	break;
	   	    case ALPHABETIC:
	   	    	initAlphabetic();
	   	        break;
	   	    case ALPHANUMERIC:
	   	    	initAlphabetic();
	   	    	initNumeric();
	   	    	break;
	   	    case NUMERIC:
	   	    	initNumeric();
	   	    	break;
	   	}
	}

	public int numberOfPossibleValues() {
		return values.size;
	}

	/**
	 * Generate a concrete value for the symbolic character.
	 * It just returns the first possible value
	 * @return A concrete charater value
	 */
	public Character getConcreteValue() {
	    Character value = null;
		if (values.size > 0) {
			for (int i = 0; i < 256; i++) {
				if (values.getValue(i)) {
					value = new Character((char)i);
					break;
				}
			}
	    }
		return value;
	}

	/**
	 * Auxiliary method that prints all possible values
	 * Needed for testing @see method Test below
	 */
	public void printPossibleValues() {
	    String allValues = "";
	    for (int i = 0; i < 256; i++) {
	        if (values.getValue(i)) {
	        	allValues = allValues + new Character((char)i) + " ";
	        } 
	    }
	    System.out.println("Values: " + allValues);
	}
	
	/**
     * Add to the possible values all ACII characters
     */
    private void initAll() {
    	for (int i = 0; i < 256; i++) {
	        	values.setValue(i, true);
	    	}
    }
    
    /**
     * Add to the possible values only alphabetic ACII characters
     */
    private void initAlphabetic() {
    	values.setValue(32, true);
	    for (int i = 65; i < 91; i++) {
	        values.setValue(i, true);
	    }
	    for (int i = 97; i < 123; i++) {
	        values.setValue(i, true);
	    }
    }
    
    /**
     * Add to the possible values only numeric ACII characters
     */
    private void initNumeric() {
    	values.setValue(32, true);
	        for (int i = 48; i < 58; i++) {
	        	values.setValue(i, true);
	        }
    }
    
    /**
     * Represents the set of possible values for a symbolic character.
     * For perforamnce the representation is as an array of booleans and
     * a convenince size property is maintained. getValue method is not
     * really needed but is added for consistences with the setValue
     * @author svetoslavganov
     *
     */
    private static class Values {
    	private int size;
    	private boolean[] values;
    	
    	public Values() {
    		size = 0;
    		values = new boolean[256];
    	}

		public boolean getValue(int index) {
			return values[index];
		}
		
		public void setValue(int index, boolean value) {
			values[index] = value;
			if (value) {
				size++;
			} else {
				size--;
			}
		}
    }
    
    /**
     * Ennumerates all possible types of values a symbolic character 
     * could take: 
     * ALL - all ASCII characters are possible
	 * ALPHABETIC - only alphabetic ASCII characters
	 * ALPHANUMERIC - only alphabic and nummeric ASCII
	 * NUMERIC - only numeric ASCII characters
     * @author svetoslavganov
     *
     */
	public static enum CharacterType {
		ALL,
		ALPHANUMERIC,
		ALPHABETIC,
		NUMERIC;
		
		private CharacterType () {
		}
	}
	
	/**
	 * Test method
	 * @param args 
	 */
	public static void main(String[] args) {
		CHAR all = new CHAR(CharacterType.ALL);
		all.printPossibleValues();
		CHAR alphanumeric = new CHAR(CharacterType.ALPHANUMERIC);
		alphanumeric.printPossibleValues();
		CHAR alphabetic = new CHAR(CharacterType.ALPHABETIC);
		alphabetic.printPossibleValues();
		CHAR numeric = new CHAR(CharacterType.NUMERIC);
		numeric.printPossibleValues();
		boolean result = alphanumeric.disjunction(numeric);
		System.out.println("INTERSECTION: " + result);
		alphanumeric.printPossibleValues();
	}
}