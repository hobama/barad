package barad.instrument;


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
		enqueue("Initialize", new String[]{});
	}
	
	/**
	 * Creates a new queue with the dafault size - 5
	 */
	public BytecodeQueue(int size) {
		bytecodes = new Entry[size];
		this.size = size;
		head = 0;
		tail = 1;
		enqueue("Initialize", new String[]{});
	}
	
	/**
	 * Add a new entry to the queue
	 * @param type The bytecode's type
	 * @param parameters The string arguments i.e. descriptor and so on
	 */
	public void enqueue(String type, String[] parameters) {
		bytecodes[tail++] = new Entry(type, parameters);
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
		if (index < 0) {
			index = 0;
		}
		return bytecodes[index];
	}
	
	public static class Entry {
		public String type;
		public String[] parameters;
		
		public Entry(String type, String[] parameters) {
			this.type = type;
			this.parameters = parameters;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
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
