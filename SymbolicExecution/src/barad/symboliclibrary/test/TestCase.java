package barad.symboliclibrary.test;

import java.util.HashSet;

/**
 * Class that represents a GUI test case
 * Note: this class has a natural ordering that is inconsistent with equals
 * @author svetoslavganov
 *
 */
public class TestCase implements Comparable {
	private static int idCounter = 0;
	private int id;
	private HashSet<TestInput> testInputs;
	private HashSet<Descriptor> testDescriptors;
	private Integer hashCode;
	private int lastPrime;
	private boolean irrelevant;

	public TestCase() {
		this.lastPrime = 3;
		this.id = idCounter++;
	}
	
	/**
	 * Determines if this test case subsumes another one.
	 * If the other test case is subsumed into this one
	 * it is marked as irrelevant
	 * @param other The other test case
	 * @return True if this case subsumes the other one,
	 * false otherwise
	 */
	public boolean subsumes(TestCase other) {
		boolean subsumes = true;
		if (testInputs.size() < other.testInputs.size()) {
			subsumes = false;
		} else {
			for (TestInput ti: other.testInputs) {
				subsumes = subsumes && testInputs.contains(ti);
			}
		}
		other.setIrrelevant(subsumes);
		return subsumes;
	}
	
	/**
	 * Merges this test case ans another test case if they have no 
	 * test inputs in common. The test inputs of the other test 
	 * case are added to this one and the other test case is marked
	 * as irrelevant. 
	 * @param other The other test case
	 * @return True if the tests were merged, false otherwise
	 */
	public boolean merge(TestCase other) {
		boolean merged = false;
		if (!intersects(other)) {
			testInputs.addAll(other.testInputs);
			testDescriptors.addAll(other.testDescriptors);
			other.setIrrelevant(true);
			merged = true;
		}
		return merged;
	}
	
	
	/**
	 * Compares this object to another object
	 * @param The instance we compare to
	 * @return -1 if this object is less that the other object,
	 * 		    0 if this object is equal to the other object,
	 * 	        1 if this object is greater that the other object
	 * Note: this class has a natural ordering that is inconsistent with equals
	 */
	public int compareTo(Object obj) {
		if (obj == null) {
			return 1;
		}
		if (!getClass().getName().equals(obj.getClass().getName())) {
			return 1;
		}
		TestCase other = (TestCase)obj;
		if (testInputs.size() > other.testInputs.size()) {
			return 1;
		} else if (testInputs.size() < other.testInputs.size()) {
			return -1;
		}
		if (this.equals(other)) {
			return 0;
		}
		return 1;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override 
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Test case UID:" + id + '\n');
		if (testInputs != null) {
			for (TestInput ti: testInputs) {
				stringBuilder.append(ti.toString() + '\n');	
			}
		} else {
			stringBuilder.append("This test case has no test inputs");
		}
		return stringBuilder.toString();
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		if (hashCode == null) {
			calculateHashCode();
		}
		return hashCode;
	}
	
	/**
	 * Compres this object to another inastance using as
	 * crieria the the test inputs i.e. two instances are
	 * euqal if and only if they have the same input values
	 */
	@Override 
	public boolean equals(Object obj) {
		if (obj == null) { 
			return false;
		}
		if (!(obj.getClass().equals(this.getClass()))) {
			return false;
		}
		TestCase other = (TestCase) obj;
		if (testInputs.size() != other.testInputs.size()) {
			return false;
		}
		boolean equals = true;
		for (TestInput ti: testInputs) {
			equals = equals && other.testInputs.contains(ti);
		}
		return equals;
	}
	
	/**
	 * Get the list of test inputs for this test case
	 * @return The list of test inputs
	 */
	public HashSet<TestInput> getTestInputs() {
		return testInputs;
	}

	/**
	 * Set the list of test inputs for this test case
	 * @param The list of test inputs
	 */
	public void setTestInputs(HashSet<TestInput> testInputs) {
		this.testDescriptors = new HashSet<Descriptor>();
		for (TestInput ti: testInputs) {
			testDescriptors.add(ti.getDescriptor());
		}
		this.testInputs = testInputs;
	}
	
	public boolean isIrrelevant() {
		return irrelevant;
	}

	public void setIrrelevant(boolean subsumed) {
		this.irrelevant = subsumed;
	}

	/**
	 * Get the unique id of a test case
	 * @return The id of the test case
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Determines if this test case intersects another one
	 * i.e. they have inputs for common widgets
	 * @param other the other test case
	 * @return True if this case intersects the other one,
	 * false otherwise
	 */
	private boolean intersects(TestCase other) {
		for (TestInput ti: testInputs) {
			if (other.testDescriptors.contains(ti.getDescriptor())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Calculate the hash code of this object and sets
	 * the hash field to its value
	 */
	private void calculateHashCode() {
		int hash = 0;
		for (TestInput ti : testInputs) {
			hash = hash + ti.hashCode() * getNextPrime();
		}
		hashCode = hash;
	}
	
	/**
	 * Calculates the next prime number and sets the lastPrime filed
	 * to its value. 
	 * @return The next prime number greater than the last such one
	 */
	private int getNextPrime() { // for N >= 5; O(log N)
		int nextPrime = lastPrime + 2;
		while (!isPrime(nextPrime)) {	
			nextPrime = nextPrime + 2;
		}
		lastPrime = nextPrime;
		return nextPrime;
	}
	
	/**
	 * Determines whether a number is prime or not
	 * @param candidate The candidate number
	 * @return True if the candidate is prime, false otherwise
	 */
	private boolean isPrime(int candidate) { // O (N**(1/2))
		for (int i = 3; i * i <= candidate; i = i + 2) {
			if (candidate % i == 0) {
				return false;
			} 
		}
		return true;
	}
}
