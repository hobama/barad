package barad.symboliclibrary.string;

import java.util.LinkedList;
import java.util.List;

import javax.sound.midi.Instrument;

import barad.symboliclibrary.path.StringPathConstraint;

import dk.brics.automaton.Automaton;
import dk.brics.automaton.BasicAutomata;
import dk.brics.automaton.BasicOperations;


public class Test {
	
	public void test(String s, int i, boolean b, long l, double d, float f) {
 
		int x = 100;
		if (s.equals("test")) {
			s = s.substring(2);
		}
		if (s.equals("st")) {
			s = "opala";
		}
	}
	
	public static void main(String[] args) {
		/*
		SymbolicString s1 = new SymbolicString(7);
		s1.setBegIndex(2);
		s1.setEndIndex(6);
		SymbolicString s2 = new SymbolicString("etli");
		Equals e1 = new Equals(s1, s2);
		System.out.println("1st case: Constraint Valid: " + e1.isValid());
		System.out.println("1st case: Concretized: " + e1.concretize());
		System.out.println("1st case: Accepted: " + e1.getConstraintString().accept("Svetlio"));
		Equals e2 = (Equals)e1.inverse();
		System.out.println("2nd case: Constraint Valid: " + e2.isValid());
		System.out.println("2st case: Concretized: " + e2.concretize());
		System.out.println("2st case: Accepted: " + e2.getConstraintString().accept("Svetlio"));
		*/
		
		/**
		 * TODO: Test again thoroughly
		 */
		
		StringInterface s1 = new SymbolicString(10);
		StringInterface s2 = new SymbolicString("Svetlio");
		Equals e1 = new Equals(s1, s2);
		System.out.println("1st case: Constraint Valid: " + e1.isValid());
		System.out.println("1st case: Concretized: " + e1.concretize());
		System.out.println("1st case: Accepted: " + e1.getConstraintString().accept("Svetliofff"));
		StringInterface s3 = s1.substring(3);
		StringInterface s4 = new SymbolicString("Sve");
		Equals e2 = (Equals)new Equals(s3, s4).inverse();
		System.out.println("2nd case: Constraint Valid: " + e2.isValid());
		System.out.println("2st case: Concretized: " + e2.concretize());
		System.out.println("2st case: Accepted: " + e2.getConstraintString().accept("SveSvfffff"));
		
		//StringInterface allConstraints = e1.getConstraintString()
	}
}