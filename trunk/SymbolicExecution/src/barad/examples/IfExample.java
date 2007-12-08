package barad.examples;


public class IfExample {
	
	private int test;
	
	public void test(float x, int y) {
		//Float
		if (x < 10) {
			x = 7;
			if (y <= 20) {
				x = x + 20;
				y = y + 10;
			} else {
				x = 1;
			}
		} else {
			x = 22;
		}
		
		//Integer
		/*
		x = x + 10; 
		if (x >= 12) {
			x = 10;
		} else {
			x = 100;
		}
		*/
	}
	

	
	public IfExample() {
	/*	
		int x = 4;
		if (x >= 12) {
			x = 10;
		} else {
			x = 100;
		}
		*/
	}
	
	
	/*
	//Float
	public IfExample() {
		float f = 1;
		f = (f + 5.0f - 2.0f) / 10.0f * 12.0f;
		float f = 1;
		if (f >= 12f) {
			f = 10f;
		} else {
			f = 100f;
			Test(f);
		}
	}
	*/
	
	/*
	private void Test(float x) {
		if (x >= 10.0f) {
			x = 10f;
		} else {
			x = 100f;
		}
	}
	*/
	
	/*
	//String
	public IfExample() {
		String s1 = "test";
		if (s1.equals("test")) {
			s1 = s1.substring(2);
		} else {
			s1 = "opala";
		}
	}
	*/
	
	/*Works OK
	public IfExample() {
		int x = 0;
		int y = 0;
		
		switch (y) {
		case 1:
			x = 1;
			break;
		case 2:
			x = 2;
			break;
		}
	}
	*/
	//private enum Test {AAA, BBB;}
	
	/*
	public IfExample() {
		int x = 10;
		int y = 99;
		String s1 = "test";
		if (s1.equals("test")) {
		s1 = s1.substring(2);
			x = x + 10;
			switch (y) {
			case 1:
				x = 1;
				break;
			case 2:
				x = 2;
				Test(y);
				break;
			}
			x = x / 20;
		} else {
		s1 = "opala";
			y = x + 8;
		}
		x = x + y;
	}
	*/

	//This is OK
	/*
	public IfExample() {
		
		int y = 123;
		int x = 10;
		x = x + y;
		if (x > 8) {
			x = x + y;
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
	*/
	
	/*
	private void Test(int x) {
		int y = 2;	
		if (x > 21) {
			x = 100;
			x = 200;
			HashSet<String> set1 = new HashSet<String>();
			set1.add("Test1");
			Dummy dummy = new Dummy(set1);
			switch (y) {
			case 1:
				x = 1;
				break;
			case 2:
				x = 2;
				break;
			}
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
	*/	
	
}