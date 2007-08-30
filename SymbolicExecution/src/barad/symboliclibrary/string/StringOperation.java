package barad.symboliclibrary.string;

public class StringOperation {
	protected StringInterface op1;
    protected StringInterface op2;
    protected String oprnd;

    public StringOperation(StringInterface operation1, StringInterface operation2, String operation) {
    	if (operation1 == null || operation2 == null || operation == null) {
            throw new IllegalArgumentException();
    	}
        op1 = operation1;
        op2 = operation2;
        oprnd = operation;
    }
       
    @Override
    public String toString() {
        return " (" + op1.toString() + " " + oprnd + " " + op2.toString() + ") ";
    }

    /*
    public SUBSTR SBSTR(int beg, int end) {
        return new SUBSTR(beg, end, this);
    }

    public CONCAT CONCAT(IString s) {
        return new CONCAT(this, s);
    }
	*/

}