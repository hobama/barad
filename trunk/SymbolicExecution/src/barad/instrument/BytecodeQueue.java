package barad.instrument;

import org.objectweb.asm.Opcodes;

/**
 * Class that caches the last bytecode instructions in the order they
 * have been invoked. The number of the cached instructions is equal to
 * the capacity property if specified, otherwise as default the last
 * five are cached.
 * @author svetoslavganov
 */
public class BytecodeQueue {
	private Entry[] bytecodes;
	private int size;
	private int head;
	private int tail;
	private static final int DEFAULT_SIZE = 5;
	
	/**
	 * Creates a new queue with the dafault size - 5
	 */
	public BytecodeQueue() {
		bytecodes = new Entry[DEFAULT_SIZE];
		size = DEFAULT_SIZE;
		head = 0;
		tail = 1;
	}
	
	/**
	 * Creates a new queue with the dafault size - 5
	 */
	public BytecodeQueue(int size) {
		bytecodes = new Entry[size];
		this.size = size;
		head = 0;
		tail = 1;
	}
	
	/**
	 * Add a new entry to the queue
	 * @param opcode The bytecode's opcode
	 * @param parameters The string arguments i.e. descriptor and so on
	 */
	public void enqueue(String opcode, String[] parameters) {
		bytecodes[tail++] = new Entry(opcode, parameters);
		if (tail == size) {
			tail = 0;
		}
		if (tail == head) {
			dequeue();
		}
	}
	
	/**
	 * Remove the last instruction entry from the queue
	 * @return The last entry
	 */
	public Entry dequeue() {
		System.out.println("Entry removed");
		Entry result = bytecodes[head++];
		if (head == size) {
			head = 0;
		}
		return result;
	}
	
	/**
	 * Gets the bytecode instruction with the specified offset from
	 * the last instruction. If the offset is 0 the last instruction 
	 * is returned. If offset is 1 the instruction before the last is
	 * returned and so on.
	 * @param offset The offset from the last instruction 
	 * @return The entry at the specified position
	 */
	public Entry getLatestEntry(int offset) {
		int index = (tail - offset - 1) % size;
		return bytecodes[index];
	}
	
	public static class Entry {
		public String opcode;
		public String[] parameters;
		
		public Entry(String opcode, String[] parameters) {
			this.opcode = opcode;
			this.parameters = parameters;
		}

		public String getOpcode() {
			return opcode;
		}

		public void setOpcode(String opcode) {
			this.opcode = opcode;
		}

		public String[] getParameters() {
			return parameters;
		}

		public void setParameters(String[] parameters) {
			this.parameters = parameters;
		}
	}
	
	public static void main(String[] args) {
		BytecodeQueue cache = new BytecodeQueue();
		int count = 0;
		for (int i = 0; i < 3; i++) {
			cache.enqueue("AALOAD", new String[]{"Svetoslav", "Ganov" + String.valueOf(count++)});
		}
		System.out.println(cache.getLatestEntry(0).getParameters()[1]);
	}
	
}
