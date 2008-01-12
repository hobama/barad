package barad.examples;

public class Test2 {
	public static void main(String[] args) {
		String desc = "(org/eclipse/swt/events/SelectionEvent;)V";
		System.out.println(desc.matches("\\(org/eclipse/swt/events/[a-zA-Z]+;\\)V"));
	}
}
