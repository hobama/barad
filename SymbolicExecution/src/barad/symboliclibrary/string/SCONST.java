package barad.symboliclibrary.string;

import java.io.Serializable;
import java.util.ArrayList;

import barad.symboliclibrary.string.CHAR.CharacterType;

public class SCONST  extends SymbolicStringEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private String value;
	private ArrayList<CHAR> characters;

    public SCONST(String value, CharacterType type) {
    	super("SCONST");
        this.value = value;
        characters = new ArrayList<CHAR>();
        for (Character c: value.toCharArray()) {
        	characters.add(new CHAR(c, type));
        }
    }

    public ArrayList<CHAR> getCharacters() {
		return characters;
	}

	public void setCharacters(ArrayList<CHAR> characters) {
		this.characters = characters;
	}

	@Override
	public String toString() {
            return value;
    }

	public ArrayList<String> getEnumeration() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setEnumeration(ArrayList<String> enumeration) {
		// TODO Auto-generated method stub
		
	}
}
