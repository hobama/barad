package barad.symboliclibrary.common;

public enum ConditionalMappings {
	IF_ICMPEQ("IF_ICMPNE"),
	IF_ICMPGE("IF_ICMPLT"),
	IF_ICMPGT("IF_ICMPLE"),
	IF_ICMPLE("IF_ICMPGT"),
	IF_ICMPLT("IF_ICMPGE"),
	IF_ICMPNE("IF_ICMPEQ");

	private String operation;
	
	private ConditionalMappings(String operation) {
		this.operation = operation;
	}
	
	public String getOperation() {
		return (operation);
	}
}
