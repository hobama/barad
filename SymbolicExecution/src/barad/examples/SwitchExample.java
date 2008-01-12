package barad.examples;

public class SwitchExample {
	public void SwitchExample(int x){
		switch(x) {
		case 1: 
			break;
		case 2:
			break;
		default:
			break;
		}
	}
	
	private void TestString() {
		String str = "Svetlio";
		if (str.equals("Svetlio")) {
			str = "";
		}
	}
	
	private void TestInteger() {
		String str = "Svetlio";
		if (!str.equals("Svetlio")) {
			str = "";
		}
	}
}
