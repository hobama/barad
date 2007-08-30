package barad.examples;

import java.util.HashSet;

public class IfExample {
	
	private int test;
	
	//public IfExample() {
		//int x = 10;
		//x = x + 10;
	//}
	
	//This is OK
	public IfExample() {
		int x = 10;
		if (x > 8) {
			x = 6;
			if (x > 20) {
				x = 333;
				if (x == 4) {
					x = 300;
				} else {
					int z = 0;
				}
				Test(x);
			}
		} else {
			x = 1000;
			x = 123;
		}
	}
	
	private void Test(int x) {
		int y = 2;	
		if (x > 21) {
			x = 100;
			x = 200;
			HashSet<String> set1 = new HashSet<String>();
			set1.add("Test1");
			Dummy dummy = new Dummy(set1);
			if (y == 100) {
				y = 22;
				HashSet<String> set2 = new HashSet<String>();
				set2.add("Test2");
				dummy = new Dummy(set2);
			}
		} else {
			x = 20;
		}
	}
	

}