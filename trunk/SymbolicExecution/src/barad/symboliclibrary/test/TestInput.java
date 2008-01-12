package barad.symboliclibrary.test;

/**
 * This class represent a test input value
 * Note: this class has a natural ordering that is inconsistent with equals
 * @author svetoslavganov
 *
 */
public class TestInput implements Comparable {
	private String value;
	private Descriptor descriptor;
	private Integer hashCode;
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(descriptor.toString());
		stringBuilder.append(" Value: " + value);
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
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override 
	public boolean equals(Object obj) {
		if (obj == null) { 
			return false;
		}
		if (!(obj.getClass().equals(this.getClass()))) {
			return false;
		}
		TestInput other = (TestInput) obj;
		return ((value != null && value.equals(other.value)) || (value == other.value)) &&
			   ((descriptor != null && (descriptor.equals(other.descriptor)) || (descriptor == other.descriptor)));
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
		TestInput other = (TestInput)obj;
		if (hashCode() > other.hashCode()) {
			return 1;
		} else if (hashCode() < other.hashCode()) {
			return -1;
		}
		return 0;
	}
	
	/**
	 * Returns the descriptor which relates the input value
	 * to a property of a GUI widget
	 * @return The descriptor object
	 */
	public Descriptor getDescriptor() {
		return descriptor;
	}
	
	/**
	 * Sets the descriptor which relates the input value
	 * to a property of a GUI widget
	 * @param The descriptor object
	 */
	public void setDescriptor(Descriptor descriptor) {
		this.descriptor = descriptor;
	}
	
	/**
	 * Returns the input value
	 * @return The input value
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * Sets the input value
	 * @param The input value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/*
	 * Calculates the hash code
	 */
	private void calculateHashCode() {
		int hash = 0;
		if (value != null) {
			hash = hash + value.hashCode() * 3;
		}
		if (descriptor != null) {
			hash = hash + descriptor.hashCode() * 5;
		}
		hashCode = hash;
	}
}
