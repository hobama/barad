package barad.instrument;

import junit.framework.Assert;

import org.junit.Test;

import util.BaseTestSuite;
import barad.instrument.util.BytecodeQueue;

public class Test_BytecodeQueue_00 extends BaseTestSuite {
	//test the case when tail > head
	@Test
	public void test_SymbolicString_01 () throws Exception { 
		//add records
		BytecodeQueue bytecodeQueue = new BytecodeQueue(5);
		bytecodeQueue.enqueue("Record1", new String[]{});
		bytecodeQueue.enqueue("Record2", new String[]{});
		bytecodeQueue.enqueue("Record3", new String[]{});
		//check content
		assertEquals("Record3", bytecodeQueue.getLatestEntry(0).getType());
		assertEquals("Record2", bytecodeQueue.getLatestEntry(1).getType());
		assertEquals("Record1", bytecodeQueue.getLatestEntry(2).getType());
		try {
			bytecodeQueue.getLatestEntry(3);
			Assert.fail();
		} catch (IndexOutOfBoundsException iobe) {
			/*ignore*/
		}
	}
	
	//test the case when head > tail
	@Test
	public void test_SymbolicString_02 () throws Exception { 
		//add records
		BytecodeQueue bytecodeQueue = new BytecodeQueue(5);
		bytecodeQueue.enqueue("Record1", new String[]{});
		bytecodeQueue.enqueue("Record2", new String[]{});
		bytecodeQueue.enqueue("Record3", new String[]{});
		bytecodeQueue.enqueue("Record4", new String[]{});
		bytecodeQueue.enqueue("Record5", new String[]{});
		bytecodeQueue.enqueue("Record6", new String[]{});
		//check content
		assertEquals("Record3", bytecodeQueue.dequeue().getType());
		assertEquals("Record6", bytecodeQueue.getLatestEntry(0).getType());
		assertEquals("Record5", bytecodeQueue.getLatestEntry(1).getType());
		assertEquals("Record4", bytecodeQueue.getLatestEntry(2).getType());
		try {
			bytecodeQueue.getLatestEntry(3);
			Assert.fail();
		} catch (IndexOutOfBoundsException iobe) {
			/*ignore*/
		}
		try {
			bytecodeQueue.getLatestEntry(4);
			Assert.fail();
		} catch (IndexOutOfBoundsException iobe) {
			/*ignore*/
		}
	}
}

