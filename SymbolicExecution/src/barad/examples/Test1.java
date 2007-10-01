package barad.examples;

import java.util.Iterator;

import choco.Constraint;
import choco.ContradictionException;
import choco.Problem;
import choco.integer.IntConstraint;
import choco.integer.IntDomainVar;
import choco.integer.IntExp;

public class Test1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Problem problem = new Problem();
		problem.getSolver().setRestart(true);
		IntDomainVar x = problem.makeEnumIntVar("X", 0, 100);
		IntDomainVar y = problem.makeEnumIntVar("Y", 12, 12);
		
		IntConstraint c2 = (IntConstraint)problem.lt(x, y);
		problem.post(c2);
		
		/*
		IntDomainVar z = problem.makeEnumIntVar("Z", 0, 100);
		IntExp e1 = problem.plus(x, 10);
		IntExp e2 = problem.plus(y, 20);
		IntConstraint c1 = (IntConstraint)problem.lt(e1, e2);
		problem.postCut(c1);
		IntConstraint c2 = (IntConstraint)problem.lt(x, y);
		problem.postCut(c2);
		IntConstraint c3 = (IntConstraint)problem.lt(y, z);
		problem.postCut(c3);
		IntConstraint c4 = (IntConstraint)problem.lt(x, z);
		problem.postCut(c4);
		IntConstraint c5 = (IntConstraint)problem.gt(x, 10);
		problem.postCut(c5);
	    */
		problem.solve();
		System.out.println(c2.pretty());
		System.out.println(problem.solutionToString());
		//System.out.println("x.canBeInstantiatedTo(1): " + x.canBeInstantiatedTo(1));
		//System.out.println();
		
		/*
		problem.eraseConstraint(c5);
                       

		try {
			problem.propagate();
		} catch (Exception e) {
			
		}
		problem.solve();
		System.out.println(problem.solutionToString());
		System.out.println("x.canBeInstantiatedTo(1): " + x.canBeInstantiatedTo(1));
		*/
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
