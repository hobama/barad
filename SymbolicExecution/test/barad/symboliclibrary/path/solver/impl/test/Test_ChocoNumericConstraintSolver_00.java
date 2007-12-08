
package barad.symboliclibrary.path.solver.impl.test;

import junit.framework.Assert;

import org.junit.Test;

import test.BaseTestSuite;
import barad.symboliclibrary.floats.FADD;
import barad.symboliclibrary.floats.FCONST;
import barad.symboliclibrary.floats.FMUL;
import barad.symboliclibrary.floats.FSUB;
import barad.symboliclibrary.floats.FVAR;
import barad.symboliclibrary.integers.IADD;
import barad.symboliclibrary.integers.ICONST;
import barad.symboliclibrary.integers.IMUL;
import barad.symboliclibrary.integers.ISUB;
import barad.symboliclibrary.integers.IVAR;
import barad.symboliclibrary.path.floats.FloatPathConstraint;
import barad.symboliclibrary.path.floats.IF_FCMPEQ;
import barad.symboliclibrary.path.floats.IF_FCMPGE;
import barad.symboliclibrary.path.floats.IF_FCMPGT;
import barad.symboliclibrary.path.floats.IF_FCMPLE;
import barad.symboliclibrary.path.floats.IF_FCMPLT;
import barad.symboliclibrary.path.floats.IF_FCMPNE;
import barad.symboliclibrary.path.integers.IF_ICMPEQ;
import barad.symboliclibrary.path.integers.IF_ICMPGE;
import barad.symboliclibrary.path.integers.IF_ICMPGT;
import barad.symboliclibrary.path.integers.IF_ICMPLE;
import barad.symboliclibrary.path.integers.IF_ICMPLT;
import barad.symboliclibrary.path.integers.IF_ICMPNE;
import barad.symboliclibrary.path.integers.IntegerPathConstraint;
import barad.symboliclibrary.path.solver.NumericConstraintSolver;
import barad.symboliclibrary.path.solver.impl.ChocoNumericConstraintSolver;

/**
 * Tests the ChocoNummeric constraintSolver class. It covers
 * all arithmetic and comparison operations plus some more 
 * complicated expressions
 * NOTE: Before symbolically executing code with constraints different 
 * from the ones int his test suite it is a nice idea to write a unit test 
 * first
 * @author svetoslavganov
 *
 */
public class Test_ChocoNumericConstraintSolver_00 extends BaseTestSuite {
	private NumericConstraintSolver solver;

	public Test_ChocoNumericConstraintSolver_00() {
		solver = new ChocoNumericConstraintSolver();
	}

	//Create a new problem
	@Override
	public void setUp() {
		solver.createRealProblem();
		solver.createIntProblem();
	}

	/*
	 *	REAL NUMBER CONSTRAINTS 
	 */

	//==

	//10f == X + 20f
	@Test
	public void test_solveRealProblem_00() throws Exception {
		FCONST expr1 = new FCONST(10f);
		FVAR fvarX = new FVAR();
		solver.addInputVaribale(fvarX.getId());
		FADD expr2 = new FADD(fvarX, new FCONST(20));
		FloatPathConstraint pc = new IF_FCMPEQ(expr1, expr2);
		solver.addRealCosntraint(pc);
		System.out.println(pc.toString());
		solver.solveRealProblem();
		solver.concretizeReal();
		solver.printConcretizedSolution();
		Double fvarXValue = solver.getConcretizedRealValues().get(fvarX.toString());
		Assert.assertEquals(fvarXValue, -9.999999999999996);
	}

	//10f == X - 20f
	@Test
	public void test_solveRealProblem_01() throws Exception {
		FCONST expr1 = new FCONST(10f);
		FVAR fvarX = new FVAR();
		solver.addInputVaribale(fvarX.getId());
		FSUB expr2 = new FSUB(fvarX, new FCONST(20));
		FloatPathConstraint pc = new IF_FCMPEQ(expr1, expr2);
		solver.addRealCosntraint(pc);
		System.out.println(pc.toString());
		solver.solveRealProblem();
		solver.concretizeReal();
		solver.printConcretizedSolution();
		Double fvarXValue = solver.getConcretizedRealValues().get(fvarX.toString());
		Assert.assertEquals(fvarXValue, 30.000000000000004);
	}

	//10f == X * 20f
	@Test
	public void test_solveRealProblem_02() throws Exception {
		FCONST expr1 = new FCONST(10f);
		FVAR fvarX = new FVAR();
		solver.addInputVaribale(fvarX.getId());
		FMUL expr2 = new FMUL(fvarX, new FCONST(20));
		FloatPathConstraint pc = new IF_FCMPEQ(expr1, expr2);
		solver.addRealCosntraint(pc);
		System.out.println(pc.toString());
		solver.solveRealProblem();
		solver.concretizeReal();
		solver.printConcretizedSolution();
		Double fvarXValue = solver.getConcretizedRealValues().get(fvarX.toString());
		Assert.assertEquals(fvarXValue, 0.5000000000000002);
	}

	//FIXME: This is still broken - fix if possible. Most probably
	//this operation will end up as not supported (unfortunaetly)
	/*
	//10f == X / 20f
	@Test
	public void test_solveRealProblem_03() throws Exception {
		FCONST expr1 = new FCONST(10f);
		FVAR fvarX = new FVAR();
		solver.addInputVaribale(fvarX.getId());
		FDIV expr2 = new FDIV(fvarX, new FCONST(20));
		FloatPathConstraint pc = new IF_FCMPEQ(expr1, expr2);
		solver.addRealCosntraint(pc);
		System.out.println(pc.toString());
		solver.solveRealProblem();
		solver.concretizeReal();
		solver.printConcretizedSolution();
		Double fvarXValue = solver.getConcretizedRealValues().get(fvarX.toString());
		Assert.assertEquals(fvarXValue, 0.5000000000000002);
	}
	*/

	//!=

	//10f != X + 20f
	@Test
	public void test_solveRealProblem_04() throws Exception {
		FCONST expr1 = new FCONST(10f);
		FVAR fvarX = new FVAR();
		solver.addInputVaribale(fvarX.getId());
		FADD expr2 = new FADD(fvarX, new FCONST(20));
		FloatPathConstraint pc = new IF_FCMPNE(expr1, expr2);
		solver.addRealCosntraint(pc);
		System.out.println(pc.toString());
		solver.solveRealProblem();
		solver.concretizeReal();
		solver.printConcretizedSolution();
		Double fvarXValue = solver.getConcretizedRealValues().get(fvarX.toString());
		Assert.assertEquals(fvarXValue, -10.099999999999994);
	}

	//10f != X - 20f
	@Test
	public void test_solveRealProblem_05() throws Exception {
		FCONST expr1 = new FCONST(10f);
		FVAR fvarX = new FVAR();
		solver.addInputVaribale(fvarX.getId());
		FSUB expr2 = new FSUB(fvarX, new FCONST(20));
		FloatPathConstraint pc = new IF_FCMPNE(expr1, expr2);
		solver.addRealCosntraint(pc);
		System.out.println(pc.toString());
		solver.solveRealProblem();
		solver.concretizeReal();
		solver.printConcretizedSolution();
		Double fvarXValue = solver.getConcretizedRealValues().get(fvarX.toString());
		Assert.assertEquals(fvarXValue, 29.90000000000001);
	}

	//10f != X * 20f
	@Test
	public void test_solveRealProblem_06() throws Exception {
		FCONST expr1 = new FCONST(10f);
		FVAR fvarX = new FVAR();
		solver.addInputVaribale(fvarX.getId());
		FMUL expr2 = new FMUL(fvarX, new FCONST(20));
		FloatPathConstraint pc = new IF_FCMPNE(expr1, expr2);
		solver.addRealCosntraint(pc);
		System.out.println(pc.toString());
		solver.solveRealProblem();
		solver.concretizeReal();
		solver.printConcretizedSolution();
		Double fvarXValue = solver.getConcretizedRealValues().get(fvarX.toString());
		Assert.assertEquals(fvarXValue, 0.4950000000000003);
	}

	//FIXME: This is still broken - fix if possible. Most probably
	//this operation will end up as not supported (unfortunaetly)
	/*
	//10f != X / 20f
	@Test
	public void test_solveRealProblem_07() throws Exception {
		FCONST expr1 = new FCONST(10f);
		FVAR fvarX = new FVAR();
		solver.addInputVaribale(fvarX.getId());
		FDIV expr2 = new FDIV(fvarX, new FCONST(20));
		FloatPathConstraint pc = new IF_FCMPNE(expr1, expr2);
		solver.addRealCosntraint(pc);
		System.out.println(pc.toString());
		solver.solveRealProblem();
		solver.concretizeReal();
		solver.printConcretizedSolution();
		Double fvarXValue = solver.getConcretizedRealValues().get(fvarX.toString());
		Assert.assertEquals(fvarXValue, 0.4950000000000003);
	}
	*/
	
	//>

	//10f > X + 20f
	@Test
	public void test_solveRealProblem_08() throws Exception {
		FCONST expr1 = new FCONST(10f);
		FVAR fvarX = new FVAR();
		solver.addInputVaribale(fvarX.getId());
		FADD expr2 = new FADD(fvarX, new FCONST(20));
		FloatPathConstraint pc = new IF_FCMPGT(expr1, expr2);
		solver.addRealCosntraint(pc);
		System.out.println(pc.toString());
		solver.solveRealProblem();
		solver.concretizeReal();
		solver.printConcretizedSolution();
		Double fvarXValue = solver.getConcretizedRealValues().get(fvarX.toString());
		Assert.assertEquals(fvarXValue, -10.099999999999994);
	}

	//10f > X - 20f
	@Test
	public void test_solveRealProblem_09() throws Exception {
		FCONST expr1 = new FCONST(10f);
		FVAR fvarX = new FVAR();
		solver.addInputVaribale(fvarX.getId());
		FSUB expr2 = new FSUB(fvarX, new FCONST(20));
		FloatPathConstraint pc = new IF_FCMPGT(expr1, expr2);
		solver.addRealCosntraint(pc);
		System.out.println(pc.toString());
		solver.solveRealProblem();
		solver.concretizeReal();
		solver.printConcretizedSolution();
		Double fvarXValue = solver.getConcretizedRealValues().get(fvarX.toString());
		Assert.assertEquals(fvarXValue, 29.90000000000001);
	}

	//10f > X * 20f
	@Test
	public void test_solveRealProblem_10() throws Exception {
		FCONST expr1 = new FCONST(10f);
		FVAR fvarX = new FVAR();
		solver.addInputVaribale(fvarX.getId());
		FMUL expr2 = new FMUL(fvarX, new FCONST(20));
		FloatPathConstraint pc = new IF_FCMPGT(expr1, expr2);
		solver.addRealCosntraint(pc);
		System.out.println(pc.toString());
		solver.solveRealProblem();
		solver.concretizeReal();
		solver.printConcretizedSolution();
		Double fvarXValue = solver.getConcretizedRealValues().get(fvarX.toString());
		Assert.assertEquals(fvarXValue, 0.4950000000000003);
	}
	
	//FIXME: This is still broken - fix if possible. Most probably
	//this operation will end up as not supported (unfortunaetly)
	/*
	//10f > X / 20f
	@Test
	public void test_solveRealProblem_11() throws Exception {
		FCONST expr1 = new FCONST(10f);
		FVAR fvarX = new FVAR();
		solver.addInputVaribale(fvarX.getId());
		FDIV expr2 = new FDIV(fvarX, new FCONST(20));
		FloatPathConstraint pc = new IF_FCMPGT(expr1, expr2);
		solver.addRealCosntraint(pc);
		System.out.println(pc.toString());
		solver.solveRealProblem();
		solver.concretizeReal();
		solver.printConcretizedSolution();
		Double fvarXValue = solver.getConcretizedRealValues().get(fvarX.toString());
		Assert.assertEquals(fvarXValue, 0.4950000000000003);
	}
	*/
	
	//<

	//10f < X + 20f
	@Test
	public void test_solveRealProblem_12() throws Exception {
		FCONST expr1 = new FCONST(10f);
		FVAR fvarX = new FVAR();
		solver.addInputVaribale(fvarX.getId());
		FADD expr2 = new FADD(fvarX, new FCONST(20));
		FloatPathConstraint pc = new IF_FCMPLT(expr1, expr2);
		solver.addRealCosntraint(pc);
		System.out.println(pc.toString());
		solver.solveRealProblem();
		solver.concretizeReal();
		solver.printConcretizedSolution();
		Double fvarXValue = solver.getConcretizedRealValues().get(fvarX.toString());
		Assert.assertEquals(fvarXValue, 100.0);
	}

	//10f < X - 20f
	@Test
	public void test_solveRealProblem_13() throws Exception {
		FCONST expr1 = new FCONST(10f);
		FVAR fvarX = new FVAR();
		solver.addInputVaribale(fvarX.getId());
		FSUB expr2 = new FSUB(fvarX, new FCONST(20));
		FloatPathConstraint pc = new IF_FCMPLT(expr1, expr2);
		solver.addRealCosntraint(pc);
		System.out.println(pc.toString());
		solver.solveRealProblem();
		solver.concretizeReal();
		solver.printConcretizedSolution();
		Double fvarXValue = solver.getConcretizedRealValues().get(fvarX.toString());
		Assert.assertEquals(fvarXValue, 100.0);
	}

	//10f < X * 20f
	@Test
	public void test_solveRealProblem_14() throws Exception {
		FCONST expr1 = new FCONST(10f);
		FVAR fvarX = new FVAR();
		solver.addInputVaribale(fvarX.getId());
		FMUL expr2 = new FMUL(fvarX, new FCONST(20));
		FloatPathConstraint pc = new IF_FCMPLT(expr1, expr2);
		solver.addRealCosntraint(pc);
		System.out.println(pc.toString());
		solver.solveRealProblem();
		solver.concretizeReal();
		solver.printConcretizedSolution();
		Double fvarXValue = solver.getConcretizedRealValues().get(fvarX.toString());
		Assert.assertEquals(fvarXValue, 100.0);
	}

	//FIXME: This is still broken - fix if possible. Most probably
	//this operation will end up as not supported (unfortunaetly)
	/*
	//10f < X / 20f
	@Test
	public void test_solveRealProblem_15() throws Exception {
		FCONST expr1 = new FCONST(10f);
		FVAR fvar = new FVAR();
		solver.addInputVaribale(fvar.getId());
		FDIV expr2 = new FDIV(fvar, new FCONST(20));
		FloatPathConstraint pc = new IF_FCMPLT(expr1, expr2);
		solver.addRealCosntraint(pc);
		System.out.println(pc.toString());
		solver.solveRealProblem();
		solver.concretizeReal();
		solver.printConcretizedSolution();
		Double fvarXValue = solver.getConcretizedRealValues().get(fvarX.toString());
		Assert.assertEquals(fvarXValue, 100.0);
	}
	*/
	
	//>=

	//10f >= X + 20f
	@Test
	public void test_solveRealProblem_16() throws Exception {
		FCONST expr1 = new FCONST(10f);
		FVAR fvarX = new FVAR();
		solver.addInputVaribale(fvarX.getId());
		FADD expr2 = new FADD(fvarX, new FCONST(20));
		FloatPathConstraint pc = new IF_FCMPGE(expr1, expr2);
		solver.addRealCosntraint(pc);
		System.out.println(pc.toString());
		solver.solveRealProblem();
		solver.concretizeReal();
		solver.printConcretizedSolution();
		Double fvarXValue = solver.getConcretizedRealValues().get(fvarX.toString());
		Assert.assertEquals(fvarXValue, -9.999999999999996);
	}

	//10f >= X - 20f
	@Test
	public void test_solveRealProblem_17() throws Exception {
		FCONST expr1 = new FCONST(10f);
		FVAR fvarX = new FVAR();
		solver.addInputVaribale(fvarX.getId());
		FSUB expr2 = new FSUB(fvarX, new FCONST(20));
		FloatPathConstraint pc = new IF_FCMPGE(expr1, expr2);
		solver.addRealCosntraint(pc);
		System.out.println(pc.toString());
		solver.solveRealProblem();
		solver.concretizeReal();
		solver.printConcretizedSolution();
		Double fvarXValue = solver.getConcretizedRealValues().get(fvarX.toString());
		Assert.assertEquals(fvarXValue, 30.000000000000004);
	}

	//10f >= X * 20f
	@Test
	public void test_solveRealProblem_18() throws Exception {
		FCONST expr1 = new FCONST(10f);
		FVAR fvarX = new FVAR();
		solver.addInputVaribale(fvarX.getId());
		FMUL expr2 = new FMUL(fvarX, new FCONST(20));
		FloatPathConstraint pc = new IF_FCMPGE(expr1, expr2);
		solver.addRealCosntraint(pc);
		System.out.println(pc.toString());
		solver.solveRealProblem();
		solver.concretizeReal();
		solver.printConcretizedSolution();
		Double fvarXValue = solver.getConcretizedRealValues().get(fvarX.toString());
		Assert.assertEquals(fvarXValue, 0.5000000000000002);
	}

	//FIXME: This is still broken - fix if possible. Most probably
	//this operation will end up as not supported (unfortunaetly)
	/*
	//10f >= X / 20f
	@Test
	public void test_solveRealProblem_19() throws Exception {
		FCONST expr1 = new FCONST(10f);
		FVAR fvarX = new FVAR();
		solver.addInputVaribale(fvarX.getId());
		FDIV expr2 = new FDIV(fvarX, new FCONST(20));
		FloatPathConstraint pc = new IF_FCMPGE(expr1, expr2);
		solver.addRealCosntraint(pc);
		System.out.println(pc.toString());
		solver.solveRealProblem();
		solver.concretizeReal();
		solver.printConcretizedSolution();
		Double fvarXValue = solver.getConcretizedRealValues().get(fvarX.toString());
		Assert.assertEquals(fvarXValue, 0.5000000000000002);
	}
	*/
	
	//<=

	//10f <= X + 20f
	@Test
	public void test_solveRealProblem_20() throws Exception {
		FCONST expr1 = new FCONST(10f);
		FVAR fvarX = new FVAR();
		solver.addInputVaribale(fvarX.getId());
		FADD expr2 = new FADD(fvarX, new FCONST(20));
		FloatPathConstraint pc = new IF_FCMPLE(expr1, expr2);
		solver.addRealCosntraint(pc);
		System.out.println(pc.toString());
		solver.solveRealProblem();
		solver.concretizeReal();
		solver.printConcretizedSolution();
		Double fvarXValue = solver.getConcretizedRealValues().get(fvarX.toString());
		Assert.assertEquals(fvarXValue, 100.0);
	}

	//10f <= X - 20f
	@Test
	public void test_solveRealProblem_21() throws Exception {
		FCONST expr1 = new FCONST(10f);
		FVAR fvarX = new FVAR();
		solver.addInputVaribale(fvarX.getId());
		FSUB expr2 = new FSUB(fvarX, new FCONST(20));
		FloatPathConstraint pc = new IF_FCMPLE(expr1, expr2);
		solver.addRealCosntraint(pc);
		System.out.println(pc.toString());
		solver.solveRealProblem();
		solver.concretizeReal();
		solver.printConcretizedSolution();
		Double fvarXValue = solver.getConcretizedRealValues().get(fvarX.toString());
		Assert.assertEquals(fvarXValue, 100.0);
	}

	//10f <= X * 20f
	@Test
	public void test_solveRealProblem_22() throws Exception {
		FCONST expr1 = new FCONST(10f);
		FVAR fvarX = new FVAR();
		solver.addInputVaribale(fvarX.getId());
		FMUL expr2 = new FMUL(fvarX, new FCONST(20));
		FloatPathConstraint pc = new IF_FCMPLE(expr1, expr2);
		solver.addRealCosntraint(pc);
		System.out.println(pc.toString());
		solver.solveRealProblem();
		solver.concretizeReal();
		solver.printConcretizedSolution();
		Double fvarXValue = solver.getConcretizedRealValues().get(fvarX.toString());
		Assert.assertEquals(fvarXValue, 100.0);
	}

	//FIXME: This is still broken - fix if possible. Most probably
	//this operation will end up as not supported (unfortunaetly)
	/*
	//10f <= X / 20f
	@Test
	public void test_solveRealProblem_23() throws Exception {
		FCONST expr1 = new FCONST(10f);
		FVAR fvarX = new FVAR();
		solver.addInputVaribale(fvarX.getId());
		FDIV expr2 = new FDIV(fvarX, new FCONST(20));
		FloatPathConstraint pc = new IF_FCMPLE(expr1, expr2);
		solver.addRealCosntraint(pc);
		System.out.println(pc.toString());
		solver.solveRealProblem();
		solver.concretizeReal();
		solver.printConcretizedSolution();
		Double fvarXValue = solver.getConcretizedRealValues().get(fvarX.toString());
		Assert.assertEquals(fvarXValue, 100.0);
	}
	*/

	//More complicated

	//	2 * (X + 10f)  <= Y - 20f
	@Test
	public void test_solveRealProblem_24() throws Exception {
		//2 * (X + 10f)
		FVAR fvarX = new FVAR();
		solver.addInputVaribale(fvarX.getId());
		FADD expr1 = new FADD(fvarX, new FCONST(10));
		FMUL expr2 = new FMUL(new FCONST(2), expr1);
		//Y - 20f
		FVAR fvarY = new FVAR();
		solver.addInputVaribale(fvarY.getId());
		FSUB expr3 = new FSUB(fvarY, new FCONST(20));
		//2 * (X + 10f)  <= Y - 20f
		FloatPathConstraint pc = new IF_FCMPLE(expr2, expr3);
		//Solve
		solver.addRealCosntraint(pc);
		System.out.println(pc.toString());
		solver.solveRealProblem();
		solver.concretizeReal();
		solver.printConcretizedSolution();
		Double fvarXValue = solver.getConcretizedRealValues().get(fvarX.toString());
		Assert.assertEquals(fvarXValue, 30.000000000000025);
		Double fvarYValue = solver.getConcretizedRealValues().get(fvarY.toString());
		Assert.assertEquals(fvarYValue, 100.0);
	}
	
	// X < 20f && X < 5f
	@Test
	public void test_solveRealProblem25() throws Exception {
		//X > 5f
		FVAR fvarX = new FVAR();
		solver.addInputVaribale(fvarX.getId());
		FloatPathConstraint pc1 = new IF_FCMPLT(fvarX, new FCONST(20));
		//X > 20f
		FVAR fvarY = new FVAR();
		solver.addInputVaribale(fvarY.getId());
		FloatPathConstraint pc2 = new IF_FCMPLT(fvarX, new FCONST(5));
		//Solve
		solver.addRealCosntraint(pc1);
		solver.addRealCosntraint(pc2);
		System.out.println(pc1.toString() + "&&" + pc2.toString());
		solver.solveRealProblem();
		solver.concretizeReal();
		solver.printConcretizedSolution();
		Double fvarXValue = solver.getConcretizedRealValues().get(fvarX.toString());
		Assert.assertEquals(fvarXValue, 4.900000000000002);
	}
	
	//	2 * (X + 10f)  <= Y - 20f && X < Y && Y < 25f
	@Test
	public void test_solveRealProblem_26() throws Exception {
		//2 * (X + 10f)
		FVAR fvarX = new FVAR();
		solver.addInputVaribale(fvarX.getId());
		FADD expr1 = new FADD(fvarX, new FCONST(10));
		FMUL expr2 = new FMUL(new FCONST(2), expr1);
		//Y - 20f
		FVAR fvarY = new FVAR();
		solver.addInputVaribale(fvarY.getId());
		FSUB expr3 = new FSUB(fvarY, new FCONST(20));
		//2 * (X + 10f)  <= Y - 20f
		FloatPathConstraint pc1 = new IF_FCMPLE(expr2, expr3);
		// X  <= Y
		FloatPathConstraint pc2 = new IF_FCMPLT(fvarX, fvarY);
		//Y < 25f
		FloatPathConstraint pc3 = new IF_FCMPLT(fvarY, new FCONST(24));
		//Solve
		solver.addRealCosntraint(pc1);
		solver.addRealCosntraint(pc2);
		solver.addRealCosntraint(pc3);
		System.out.println(pc1.toString() + "&&" + pc2.toString());
		solver.solveRealProblem();
		solver.concretizeReal();
		solver.printConcretizedSolution();
		Double fvarXValue = solver.getConcretizedRealValues().get(fvarX.toString());
		Assert.assertEquals(fvarXValue, -8.049999999999995);
		Double fvarYValue = solver.getConcretizedRealValues().get(fvarY.toString());
		Assert.assertEquals(fvarYValue, 23.900000000000006);
	}
	
	/*
	 *	INTEGER NUMBER CONSTRAINTS 
	 */
	//==

	//10 == X + 20
	@Test
	public void test_solveIntProblem_00() throws Exception {
		ICONST expr1 = new ICONST(10);
		IVAR ivarX = new IVAR();
		solver.addInputVaribale(ivarX.getId());
		IADD expr2 = new IADD(ivarX, new ICONST(20));
		IntegerPathConstraint pc = new IF_ICMPEQ(expr1, expr2);
		solver.addIntCosntraint(pc);
		System.out.println(pc.toString());
		solver.solveIntProblem();
		solver.concretizeInt();
		solver.printConcretizedSolution();
		Integer ivarXValue = solver.getConcretizedIntValues().get(ivarX.toString());
		Assert.assertEquals(ivarXValue, new Integer(-10));
	}

	
	//10 == X - 20
	@Test
	public void test_solveIntProblem_01() throws Exception {
		ICONST expr1 = new ICONST(10);
		IVAR ivarX = new IVAR();
		solver.addInputVaribale(ivarX.getId());
		ISUB expr2 = new ISUB(ivarX, new ICONST(20));
		IntegerPathConstraint pc = new IF_ICMPEQ(expr1, expr2);
		solver.addIntCosntraint(pc);
		System.out.println(pc.toString());
		solver.solveIntProblem();
		solver.concretizeInt();
		solver.printConcretizedSolution();
		Integer ivarXValue = solver.getConcretizedIntValues().get(ivarX.toString());
		Assert.assertEquals(ivarXValue, new Integer(30));
	}

	//20 == X * 10
	@Test
	public void test_solveIntProblem_02() throws Exception {
		ICONST expr1 = new ICONST(20);
		IVAR ivarX = new IVAR();
		solver.addInputVaribale(ivarX.getId());
		IMUL expr2 = new IMUL(ivarX, new ICONST(10));
		IntegerPathConstraint pc = new IF_ICMPEQ(expr1, expr2);
		solver.addIntCosntraint(pc);
		System.out.println(pc.toString());
		solver.solveIntProblem();
		solver.concretizeInt();
		solver.printConcretizedSolution();
		Integer ivarXValue = solver.getConcretizedIntValues().get(ivarX.toString());
		Assert.assertEquals(ivarXValue, new Integer(2));
	}

	/*
	 *NOTE: Choco does not support integer division, neither
	 *provides operation through which to model it 
	 */
	/*
	//10 == X / 20
	@Test
	public void test_solveIntProblem_03() throws Exception {
		ICONST expr1 = new ICONST(10);
		IVAR ivarX = new IVAR();
		solver.addInputVaribale(ivarX.getId());
		IDIV expr2 = new IDIV(ivarX, new ICONST(20));
		IntegerPathConstraint pc = new IF_ICMPEQ(expr1, expr2);
		solver.addIntCosntraint(pc);
		System.out.println(pc.toString());
		solver.solveIntProblem();
		solver.concretizeInt();
		solver.printConcretizedSolution();
		Integer ivarXValue = solver.getConcretizedIntValues().get(ivarX.toString());
		Assert.assertEquals(ivarXValue, new Integer(???));
	}
	*/

	//!=

	//10 != X + 20
	@Test
	public void test_solveIntProblem_04() throws Exception {
		ICONST expr1 = new ICONST(10);
		IVAR ivarX = new IVAR();
		solver.addInputVaribale(ivarX.getId());
		IADD expr2 = new IADD(ivarX, new ICONST(20));
		IntegerPathConstraint pc = new IF_ICMPNE(expr1, expr2);
		solver.addIntCosntraint(pc);
		System.out.println(pc.toString());
		solver.solveIntProblem();
		solver.concretizeInt();
		solver.printConcretizedSolution();
		Integer ivarXValue = solver.getConcretizedIntValues().get(ivarX.toString());
		Assert.assertEquals(ivarXValue, new Integer(-100));
	}

	//10 != X - 20
	@Test
	public void test_solveIntProblem_05() throws Exception {
		ICONST expr1 = new ICONST(10);
		IVAR ivarX = new IVAR();
		solver.addInputVaribale(ivarX.getId());
		ISUB expr2 = new ISUB(ivarX, new ICONST(20));
		IntegerPathConstraint pc = new IF_ICMPNE(expr1, expr2);
		solver.addIntCosntraint(pc);
		System.out.println(pc.toString());
		solver.solveIntProblem();
		solver.concretizeInt();
		solver.printConcretizedSolution();
		Integer ivarXValue = solver.getConcretizedIntValues().get(ivarX.toString());
		Assert.assertEquals(ivarXValue, new Integer(-100));
	}

	//10 != X * 20
	@Test
	public void test_solveIntProblem_06() throws Exception {
		ICONST expr1 = new ICONST(10);
		IVAR ivarX = new IVAR();
		solver.addInputVaribale(ivarX.getId());
		IMUL expr2 = new IMUL(ivarX, new ICONST(20));
		IntegerPathConstraint pc = new IF_ICMPNE(expr1, expr2);
		solver.addIntCosntraint(pc);
		System.out.println(pc.toString());
		solver.solveIntProblem();
		solver.concretizeInt();
		solver.printConcretizedSolution();
		Integer ivarXValue = solver.getConcretizedIntValues().get(ivarX.toString());
		Assert.assertEquals(ivarXValue, new Integer(-100));
	}

	/*
	 *NOTE: Choco does not support integer division, neither
	 *provides operation through which to model it 
	 */
	/*
	//10 != X / 20
	@Test
	public void test_solveIntProblem_07() throws Exception {
		ICONST expr1 = new ICONST(10);
		IVAR ivarX = new IVAR();
		solver.addInputVaribale(ivarX.getId());
		IDIV expr2 = new IDIV(ivarX, new ICONST(20));
		IntegerPathConstraint pc = new IF_ICMPNE(expr1, expr2);
		solver.addIntCosntraint(pc);
		System.out.println(pc.toString());
		solver.solveIntProblem();
		solver.concretizeInt();
		solver.printConcretizedSolution();
		Integer ivarXValue = solver.getConcretizedIntValues().get(ivarX.toString());
		Assert.assertEquals(ivarXValue, new Integer(???));
	}
	*/

	//>

	//10 > X + 20
	@Test
	public void test_solveIntProblem_08() throws Exception {
		ICONST expr1 = new ICONST(10);
		IVAR ivarX = new IVAR();
		solver.addInputVaribale(ivarX.getId());
		IADD expr2 = new IADD(ivarX, new ICONST(20));
		IntegerPathConstraint pc = new IF_ICMPGT(expr1, expr2);
		solver.addIntCosntraint(pc);
		System.out.println(pc.toString());
		solver.solveIntProblem();
		solver.concretizeInt();
		solver.printConcretizedSolution();
		Integer ivarXValue = solver.getConcretizedIntValues().get(ivarX.toString());
		Assert.assertEquals(ivarXValue, new Integer(-100));
	}

	//10 > X - 20
	@Test
	public void test_solveIntProblem_09() throws Exception {
		ICONST expr1 = new ICONST(10);
		IVAR ivarX = new IVAR();
		solver.addInputVaribale(ivarX.getId());
		ISUB expr2 = new ISUB(ivarX, new ICONST(20));
		IntegerPathConstraint pc = new IF_ICMPGT(expr1, expr2);
		solver.addIntCosntraint(pc);
		System.out.println(pc.toString());
		solver.solveIntProblem();
		solver.concretizeInt();
		solver.printConcretizedSolution();
		Integer ivarXValue = solver.getConcretizedIntValues().get(ivarX.toString());
		Assert.assertEquals(ivarXValue, new Integer(-100));
	}

	//10 > X * 20
	@Test
	public void test_solveIntProblem_10() throws Exception {
		ICONST expr1 = new ICONST(10);
		IVAR ivarX = new IVAR();
		solver.addInputVaribale(ivarX.getId());
		IMUL expr2 = new IMUL(ivarX, new ICONST(20));
		IntegerPathConstraint pc = new IF_ICMPGT(expr1, expr2);
		solver.addIntCosntraint(pc);
		System.out.println(pc.toString());
		solver.solveIntProblem();
		solver.concretizeInt();
		solver.printConcretizedSolution();
		Integer ivarXValue = solver.getConcretizedIntValues().get(ivarX.toString());
		Assert.assertEquals(ivarXValue, new Integer(-100));
	}
	
	/*
	 *NOTE: Choco does not support integer division, neither
	 *provides operation through which to model it 
	 */
	/*
	//10 > X / 20
	@Test
	public void test_solveIntProblem_11() throws Exception {
		ICONST expr1 = new ICONST(10);
		IVAR ivarX = new IVAR();
		solver.addInputVaribale(ivarX.getId());
		IDIV expr2 = new IDIV(ivarX, new ICONST(20));
		IntegerPathConstraint pc = new IF_ICMPGT(expr1, expr2);
		solver.addIntCosntraint(pc);
		System.out.println(pc.toString());
		solver.solveIntProblem();
		solver.concretizeInt();
		solver.printConcretizedSolution();
		Integer ivarXValue = solver.getConcretizedIntValues().get(ivarX.toString());
		Assert.assertEquals(ivarXValue, new Integer(???));
	}
	*/
	
	//<

	//10 < X + 20
	@Test
	public void test_solveIntProblem_12() throws Exception {
		ICONST expr1 = new ICONST(10);
		IVAR ivarX = new IVAR();
		solver.addInputVaribale(ivarX.getId());
		IADD expr2 = new IADD(ivarX, new ICONST(20));
		IntegerPathConstraint pc = new IF_ICMPLT(expr1, expr2);
		solver.addIntCosntraint(pc);
		System.out.println(pc.toString());
		solver.solveIntProblem();
		solver.concretizeInt();
		solver.printConcretizedSolution();
		Integer ivarXValue = solver.getConcretizedIntValues().get(ivarX.toString());
		Assert.assertEquals(ivarXValue, new Integer(-9));
	}

	//10 < X - 20
	@Test
	public void test_solveIntProblem_13() throws Exception {
		ICONST expr1 = new ICONST(10);
		IVAR ivarX = new IVAR();
		solver.addInputVaribale(ivarX.getId());
		ISUB expr2 = new ISUB(ivarX, new ICONST(20));
		IntegerPathConstraint pc = new IF_ICMPLT(expr1, expr2);
		solver.addIntCosntraint(pc);
		System.out.println(pc.toString());
		solver.solveIntProblem();
		solver.concretizeInt();
		solver.printConcretizedSolution();
		Integer ivarXValue = solver.getConcretizedIntValues().get(ivarX.toString());
		Assert.assertEquals(ivarXValue, new Integer(31));
	}

	//20 < X * 10
	@Test
	public void test_solveIntProblem_14() throws Exception {
		ICONST expr1 = new ICONST(20);
		IVAR ivarX = new IVAR();
		solver.addInputVaribale(ivarX.getId());
		IMUL expr2 = new IMUL(ivarX, new ICONST(10));
		IntegerPathConstraint pc = new IF_ICMPLT(expr1, expr2);
		solver.addIntCosntraint(pc);
		System.out.println(pc.toString());
		solver.solveIntProblem();
		solver.concretizeInt();
		solver.printConcretizedSolution();
		Integer ivarXValue = solver.getConcretizedIntValues().get(ivarX.toString());
		Assert.assertEquals(ivarXValue, new Integer(3));
	}

	/*
	 *NOTE: Choco does not support integer division, neither
	 *provides operation through which to model it 
	 */
	/*
	//10 < X / 20
	@Test
	public void test_solveIntProblem_15() throws Exception {
		ICONST expr1 = new ICONST(10);
		IVAR ivarX = new IVAR();
		solver.addInputVaribale(ivarX.getId());
		IDIV expr2 = new IDIV(ivarX, new ICONST(20));
		IntegerPathConstraint pc = new IF_ICMPLT(expr1, expr2);
		solver.addIntCosntraint(pc);
		System.out.println(pc.toString());
		solver.solveIntProblem();
		solver.concretizeInt();
		solver.printConcretizedSolution();
		Integer ivarXValue = solver.getConcretizedIntValues().get(ivarX.toString());
		Assert.assertEquals(ivarXValue, new Integer(???));
	}
	*/

	//>=

	//10 >= X + 20
	@Test
	public void test_solveIntProblem_16() throws Exception {
		ICONST expr1 = new ICONST(10);
		IVAR ivarX = new IVAR();
		solver.addInputVaribale(ivarX.getId());
		IADD expr2 = new IADD(ivarX, new ICONST(20));
		IntegerPathConstraint pc = new IF_ICMPGE(expr1, expr2);
		solver.addIntCosntraint(pc);
		System.out.println(pc.toString());
		solver.solveIntProblem();
		solver.concretizeInt();
		solver.printConcretizedSolution();
		Integer ivarXValue = solver.getConcretizedIntValues().get(ivarX.toString());
		Assert.assertEquals(ivarXValue, new Integer(-100));
	}

	//10 >= X - 20
	@Test
	public void test_solveIntProblem_17() throws Exception {
		ICONST expr1 = new ICONST(10);
		IVAR ivarX = new IVAR();
		solver.addInputVaribale(ivarX.getId());
		ISUB expr2 = new ISUB(ivarX, new ICONST(20));
		IntegerPathConstraint pc = new IF_ICMPGE(expr1, expr2);
		solver.addIntCosntraint(pc);
		System.out.println(pc.toString());
		solver.solveIntProblem();
		solver.concretizeInt();
		solver.printConcretizedSolution();
		Integer ivarXValue = solver.getConcretizedIntValues().get(ivarX.toString());
		Assert.assertEquals(ivarXValue, new Integer(-100));
	}

	//10 >= X * 20
	@Test
	public void test_solveIntProblem_18() throws Exception {
		ICONST expr1 = new ICONST(10);
		IVAR ivarX = new IVAR();
		solver.addInputVaribale(ivarX.getId());
		IMUL expr2 = new IMUL(ivarX, new ICONST(20));
		IntegerPathConstraint pc = new IF_ICMPGE(expr1, expr2);
		solver.addIntCosntraint(pc);
		System.out.println(pc.toString());
		solver.solveIntProblem();
		solver.concretizeInt();
		solver.printConcretizedSolution();
		Integer ivarXValue = solver.getConcretizedIntValues().get(ivarX.toString());
		Assert.assertEquals(ivarXValue, new Integer(-100));
	}

	/*
	 *NOTE: Choco does not support integer division, neither
	 *provides operation through which to model it 
	 */
	/*
	//10 >= X / 20
	@Test
	public void test_solveIntProblem_19() throws Exception {
		ICONST expr1 = new ICONST(10);
		IVAR ivarX = new IVAR();
		solver.addInputVaribale(ivarX.getId());
		IDIV expr2 = new IDIV(ivarX, new ICONST(20));
		IntegerPathConstraint pc = new IF_ICMPGE(expr1, expr2);
		solver.addIntCosntraint(pc);
		System.out.println(pc.toString());
		solver.solveIntProblem();
		solver.concretizeInt();
		solver.printConcretizedSolution();
		Integer ivarXValue = solver.getConcretizedIntValues().get(ivarX.toString());
		Assert.assertEquals(ivarXValue, new Integer(???));
	}
	*/
	//<=

	//10 <= X + 20
	@Test
	public void test_solveIntProblem_20() throws Exception {
		ICONST expr1 = new ICONST(10);
		IVAR ivarX = new IVAR();
		solver.addInputVaribale(ivarX.getId());
		IADD expr2 = new IADD(ivarX, new ICONST(20));
		IntegerPathConstraint pc = new IF_ICMPLE(expr1, expr2);
		solver.addIntCosntraint(pc);
		System.out.println(pc.toString());
		solver.solveIntProblem();
		solver.concretizeInt();
		solver.printConcretizedSolution();
		Integer ivarXValue = solver.getConcretizedIntValues().get(ivarX.toString());
		Assert.assertEquals(ivarXValue, new Integer(-10));
	}

	//10 <= X - 20
	@Test
	public void test_solveIntProblem_21() throws Exception {
		ICONST expr1 = new ICONST(10);
		IVAR ivarX = new IVAR();
		solver.addInputVaribale(ivarX.getId());
		ISUB expr2 = new ISUB(ivarX, new ICONST(20));
		IntegerPathConstraint pc = new IF_ICMPLE(expr1, expr2);
		solver.addIntCosntraint(pc);
		System.out.println(pc.toString());
		solver.solveIntProblem();
		solver.concretizeInt();
		solver.printConcretizedSolution();
		Integer ivarXValue = solver.getConcretizedIntValues().get(ivarX.toString());
		Assert.assertEquals(ivarXValue, new Integer(30));
	}

	//20 <= X * 10
	@Test
	public void test_solveIntProblem_22() throws Exception {
		ICONST expr1 = new ICONST(20);
		IVAR ivarX = new IVAR();
		solver.addInputVaribale(ivarX.getId());
		IMUL expr2 = new IMUL(ivarX, new ICONST(10));
		IntegerPathConstraint pc = new IF_ICMPLE(expr1, expr2);
		solver.addIntCosntraint(pc);
		System.out.println(pc.toString());
		solver.solveIntProblem();
		solver.concretizeInt();
		solver.printConcretizedSolution();
		Integer ivarXValue = solver.getConcretizedIntValues().get(ivarX.toString());
		Assert.assertEquals(ivarXValue, new Integer(2));
	}

	/*
	 *NOTE: Choco does not support integer division, neither
	 *provides operation through which to model it 
	 */
	/*
	//10f <= X / 20f
	@Test
	public void test_solveIntProblem_23() throws Exception {
		ICONST expr1 = new ICONST(10);
		IVAR ivarX = new IVAR();
		solver.addInputVaribale(ivarX.getId());
		IDIV expr2 = new IDIV(ivarX, new ICONST(20));
		IntegerPathConstraint pc = new IF_ICMPLE(expr1, expr2);
		solver.addIntCosntraint(pc);
		System.out.println(pc.toString());
		solver.solveIntProblem();
		solver.concretizeInt();
		solver.printConcretizedSolution();
		Integer ivarXValue = solver.getConcretizedIntValues().get(ivarX.toString());
		Assert.assertEquals(ivarXValue, new Integer(???));
	}
	*/

	//More complicated

	/*
	 * NOTE: Due to a bug (most probably) in Choco library multiplication
	 * expressions of the kind - [const|var] * ([const|var] arithmetic operation [const|var]) 
	 * cause class cast exception inside the code of the cChoco libarary i.e. in our 
	 * experiments and examples we should avoid such kind of constraints!!!
	 * Example for sucn an expression; 2 * (X + 10f)  <= Y - 20f ()
	 */
	//	2 * X  <= Y - 20 && Y > 0
	@Test
	public void test_solveIntProblem_24() throws Exception {
		//2 * X
		IVAR ivarX = new IVAR();
		solver.addInputVaribale(ivarX.getId());
		IMUL expr2 = new IMUL(new ICONST(2), ivarX);
		//Y - 20f
		IVAR ivarY = new IVAR();
		solver.addInputVaribale(ivarY.getId());
		ISUB expr3 = new ISUB(ivarY, new ICONST(20));
		//2 * X  <= Y - 20
		IntegerPathConstraint pc1 = new IF_ICMPLE(expr2, expr3);
		//Y > 0
		IntegerPathConstraint pc2 = new IF_ICMPGT(ivarY, new ICONST(20));
		//Solve
		solver.addIntCosntraint(pc1);
		solver.addIntCosntraint(pc2);
		System.out.println(pc1.toString() + " && " + pc2.toString());
		solver.solveIntProblem();
		solver.concretizeInt();
		solver.printConcretizedSolution();
		Integer ivarXValue = solver.getConcretizedIntValues().get(ivarX.toString());
		Assert.assertEquals(ivarXValue, new Integer(-100));
		Integer ivarYValue = solver.getConcretizedIntValues().get(ivarY.toString());
		Assert.assertEquals(ivarYValue, new Integer(21));
	}
	
	// X < 20 && X > 5
	@Test
	public void test_solveIntProblem25() throws Exception {
		//X > 5f
		IVAR ivarX = new IVAR();
		solver.addInputVaribale(ivarX.getId());
		IntegerPathConstraint pc1 = new IF_ICMPLT(ivarX, new ICONST(20));
		//X > 20f
		IVAR IVARY = new IVAR();
		solver.addInputVaribale(IVARY.getId());
		IntegerPathConstraint pc2 = new IF_ICMPGT(ivarX, new ICONST(5));
		//Solve
		solver.addIntCosntraint(pc1);
		solver.addIntCosntraint(pc2);
		System.out.println(pc1.toString() + "&&" + pc2.toString());
		solver.solveIntProblem();
		solver.concretizeInt();
		solver.printConcretizedSolution();
		Integer ivarXValue = solver.getConcretizedIntValues().get(ivarX.toString());
		Assert.assertEquals(ivarXValue, new Integer(6));
	}
	
	//	2 * X <= Y - 20 && X < Y && Y > 25
	@Test
	public void test_solveIntProblem_26() throws Exception {
		//2 * X 
		IVAR ivarX = new IVAR();
		solver.addInputVaribale(ivarX.getId());
		IMUL expr2 = new IMUL(new ICONST(2), ivarX);
		//Y - 20
		IVAR ivarY = new IVAR();
		solver.addInputVaribale(ivarY.getId());
		ISUB expr3 = new ISUB(ivarY, new ICONST(20));
		//2 * X <= Y - 20
		IntegerPathConstraint pc1 = new IF_ICMPLE(expr2, expr3);
		// X  <= Y
		IntegerPathConstraint pc2 = new IF_ICMPLT(ivarX, ivarY);
		//Y < 25
		IntegerPathConstraint pc3 = new IF_ICMPGT(ivarY, new ICONST(25));
		//Solve
		solver.addIntCosntraint(pc1);
		solver.addIntCosntraint(pc2);
		solver.addIntCosntraint(pc3);
		System.out.println(pc1.toString() + "&&" + pc2.toString());
		solver.solveIntProblem();
		solver.concretizeInt();
		solver.printConcretizedSolution();
		Integer ivarXValue = solver.getConcretizedIntValues().get(ivarX.toString());
		Assert.assertEquals(ivarXValue, new Integer(-100));
		Integer IVARYValue = solver.getConcretizedIntValues().get(ivarY.toString());
		Assert.assertEquals(IVARYValue, new Integer(26));
	}
}
