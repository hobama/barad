package barad.symboliclibrary.string;

import java.io.Serializable;
import java.util.ArrayList;

import barad.symboliclibrary.string.CHAR.CharacterType;

public class SVAR extends SymbolicStringEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	protected ArrayList<CHAR> characters;
    protected ArrayList<String> enumeration;
    private CharacterType type;

    public SVAR(int length, CharacterType type){
    	super("SVAR");
	    this.type = type;
	    enumeration = new ArrayList<String>();
	    characters = new ArrayList<CHAR>();
	    for (int i = 0; i < length; i++) {
	        characters.add(new CHAR(this.type));
	    }
    }

    public SVAR(int length, CharacterType type, ArrayList<String> enumeration) {
        super("SVAR");
        this.type = type;
        this.enumeration = enumeration;
        characters = new ArrayList<CHAR>();
        for (int i = 0; i < length; i++) {
            characters.add(new CHAR(this.type));
        }
    }

    @Override
    public String toString() {
        return null;
    }

    public void printPossibleValuesforEachCharacter() {
    	for (CHAR c: characters) {
    		c.printPossibleValues();
        }
    }
        
    /*TODO: Finish this
    public SVAR SBSTR(int beg, int end) {
        return new SUBSTR(beg, end, this);
    }

    public SVAR CONCAT(IString s) {
        return new CONCAT(this, s);
    }
    */

    public ArrayList<CHAR> getCharacters() {
    	return characters;
	}

	public void setCharacters(ArrayList<CHAR> characters) {
		this.characters = characters;
	}

	public void setEnumeration(ArrayList<String> enumeration) {
		this.enumeration = enumeration;
	}

	public ArrayList<String> getEnumeration() {
		return enumeration;
	}
}
