package barad.examples;

import barad.symboliclibrary.string.SymbolicString;

public class TestAutomata {
	public static void main(String[] args) {

	SymbolicString ss1 = new SymbolicString(7);
	SymbolicString ss2 = new SymbolicString("Sve");
	//ss1.substringNotEqualTo(0, ss2);
	
	System.out.println(ss1.accept("Sve"));

 	}
}
