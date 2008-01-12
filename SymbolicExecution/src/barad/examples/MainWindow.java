package barad.examples;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.printing.Printer;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import barad.annotations.Initializer;
import barad.annotations.Initializer.Type;
import barad.symboliclibrary.ui.widgets.SymbolicCombo;

@SuppressWarnings("all")
public class MainWindow extends Composite {
	private Combo combo1;
	private Label label1;
	private Label label8;
	private Label label7;
	private Text text3;
	private Text text2;
	private Text text1;
	private StyledText styledText1;
	private Label label6;
	private Label label5;
	private Label label4;
	private Button button2;
	private Combo combo3;
	private Combo combo2;
	private Label label3;
	private Label label2;
	private Printer printer; 
	private static Shell shell;
	private Button button4;
	
//	//to be removed
//	public static void main(String[] ars) {
//		showGUI();
//	}
//	
//	//to be removed
//	public static void showGUI() { 
//		Display display = Display.getDefault();
//		shell = new Shell(display);
//		MainWindow inst = new MainWindow(shell, SWT.NULL);
//		Point size = inst.getSize();
//		shell.setLayout(new FillLayout());
//		shell.layout();
//		if(size.x == 0 && size.y == 0) {
//			inst.pack();
//			shell.pack();
//		} else {
//			Rectangle shellBounds = shell.computeTrim(0, 0, size.x, size.y);
//			shell.setSize(shellBounds.width, shellBounds.height);
//		}
//		shell.open();
//		while (!shell.isDisposed()) {
//			if (!shell.getDisplay().readAndDispatch())
//			display.sleep();
//		}
//	}
//	
//	//to be removed
	public MainWindow(Composite parent, int style) {
		super(parent, style);
//		this.setLayout(new FormLayout());
//		this.setSize(868, 511);
//		initGUI();
//		this.layout();
//		combo1.select(1);
//		combo2.select(2);
//		combo3.select(2);;
	}
//	
	//@Initializer(field = {"field1", "field2"}, type = Type.ALL)
    private void initGUI() {
//		{
//			label8 = new Label(this, SWT.NONE);
//			label8.setText("WORKOUT GENERATOR ");
//			FormData label8LData = new FormData();
//			label8LData.width = 217;
//			label8LData.height = 21;
//			label8LData.left =  new FormAttachment(0, 1000, 7);
//			label8LData.top =  new FormAttachment(0, 1000, 14);
//			label8.setLayoutData(label8LData);
//		}
//		{
//			text1 = new Text(this, SWT.NONE);
//			FormData text1LData = new FormData();
//			text1LData.width = 197;
//			text1LData.height = 21;
//			text1LData.left =  new FormAttachment(0, 1000, 14);
//			text1LData.top =  new FormAttachment(0, 1000, 133);
//			text1.setLayoutData(text1LData);
//			text1.setText("0");
//		}
//		{
//			styledText1 = new StyledText(this, SWT.H_SCROLL|SWT.V_SCROLL);
//			FormData styledText1LData = new FormData();
//			styledText1LData.width = 830;
//			styledText1LData.height = 298;
//			styledText1LData.left =  new FormAttachment(0, 1000, 7);
//			styledText1LData.top =  new FormAttachment(0, 1000, 182);
//			styledText1.setLayoutData(styledText1LData);
//		}
//		{
//			combo3 = new Combo(this, SWT.DROP_DOWN|SWT.READ_ONLY);
//			FormData combo3LData = new FormData();
//			combo3LData.width = 177;
//			combo3LData.height = 23;
//			combo3LData.left =  new FormAttachment(0, 1000, 455);
//			combo3LData.top =  new FormAttachment(0, 1000, 68);
//			combo3.setLayoutData(combo3LData);
//			combo3.setItems(new java.lang.String[] {"Beginner"," Intermediate"," Advanced"});
//			combo3.setLayoutDeferred(true);
//		}
//		{
//			combo2 = new Combo(this, SWT.DROP_DOWN|SWT.READ_ONLY);
//			FormData combo2LData = new FormData();
//			combo2LData.width = 177;
//			combo2LData.height = 23;
//			combo2LData.left =  new FormAttachment(0, 1000, 231);
//			combo2LData.top =  new FormAttachment(0, 1000, 68);
//			combo2.setLayoutData(combo2LData);
//			combo2.setItems(new java.lang.String[] {"Slow","Normal","Fast"});
//		}
//		{
//			label3 = new Label(this, SWT.NONE);
//			label3.setText("Experience");
//			FormData label3LData = new FormData();
//			label3LData.width = 56;
//			label3LData.height = 21;
//			label3LData.top =  new FormAttachment(0, 1000, 42);
//			label3LData.left =  new FormAttachment(0, 1000, 455);
//			label3.setLayoutData(label3LData);
//		}
//		{
//			label2 = new Label(this, SWT.NONE);
//			label2.setText("Metabolism");
//			FormData label2LData = new FormData();
//			label2LData.width = 70;
//			label2LData.height = 21;
//			label2LData.left =  new FormAttachment(0, 1000, 231);
//			label2LData.top =  new FormAttachment(0, 1000, 42);
//			label2.setLayoutData(label2LData);
//		}
//		{
//			label1 = new Label(this, SWT.NONE);
//			label1.setText("Gender");
//			FormData label1LData = new FormData();
//			label1LData.top =  new FormAttachment(0, 1000, 42);
//			label1LData.left =  new FormAttachment(0, 1000, 14);
//			label1LData.height = 21;
//			label1LData.width = 56;
//			label1.setLayoutData(label1LData);
//
//		}
//		{
//			label4 = new Label(this, SWT.NONE);
//			label4.setText("Age in years");
//			FormData label4LData1 = new FormData();
//			label4LData1.width = 150;
//			label4LData1.height = 21;
//			label4LData1.left =  new FormAttachment(0, 1000, 14);
//			label4LData1.top =  new FormAttachment(0, 1000, 112);
//			label4.setLayoutData(label4LData1);
//		}
//		{
//			label5 = new Label(this, SWT.NONE);
//			label5.setText("Height in centimeters");
//			FormData label5LData = new FormData();
//			label5LData.width = 150;
//			label5LData.height = 21;
//			label5LData.left =  new FormAttachment(0, 1000, 231);
//			label5LData.top =  new FormAttachment(0, 1000, 112);
//			label5.setLayoutData(label5LData);
//		}
//		{
//			label6 = new Label(this, SWT.NONE);
//			label6.setText("Weight in kilos");
//			FormData label6LData = new FormData();
//			label6LData.width = 150;
//			label6LData.height = 21;
//			label6LData.left =  new FormAttachment(0, 1000, 455);
//			label6LData.top =  new FormAttachment(0, 1000, 112);
//			label6.setLayoutData(label6LData);
//		}
//		{
//			text2 = new Text(this, SWT.NONE);
//			FormData text2LData = new FormData();
//			text2LData.width = 197;
//			text2LData.height = 21;
//			text2LData.left =  new FormAttachment(0, 1000, 231);
//			text2LData.top =  new FormAttachment(0, 1000, 133);
//			text2.setLayoutData(text2LData);
//			text2.setText("0");
//		}
//		{
//			text3 = new Text(this, SWT.NONE);
//			FormData text3LData = new FormData();
//			text3LData.width = 197;
//			text3LData.height = 21;
//			text3LData.left =  new FormAttachment(0, 1000, 455);
//			text3LData.top =  new FormAttachment(0, 1000, 133);
//			text3.setLayoutData(text3LData);
//			text3.setText("0");
//		}
//		{
//			combo1 = new Combo(this, SWT.DROP_DOWN|SWT.READ_ONLY);
//			FormData combo1LData = new FormData();
//			combo1LData.width = 177;
//			combo1LData.height = 23;
//			combo1LData.left =  new FormAttachment(0, 1000, 14);
//			combo1LData.top =  new FormAttachment(0, 1000, 68);
//			combo1.setLayoutData(combo1LData);
//			combo1.setItems(new java.lang.String[] {"Male","Female"});
//		}
//		{
//			label7 = new Label(this, SWT.NONE);
//			label7.setText("Weekly workout program");
//			FormData label7LData = new FormData();
//			label7LData.width = 168;
//			label7LData.height = 21;
//			label7LData.left =  new FormAttachment(0, 1000, 7);
//			label7LData.top =  new FormAttachment(0, 1000, 161);
//			label7.setLayoutData(label7LData);
//		}
//		{
//			button4 = new Button(this, SWT.PUSH | SWT.CENTER);
//			button4.setText("Generate");
//			FormData button4LData = new FormData();
//			button4LData.left =  new FormAttachment(0, 1000, 679);
//			button4LData.top =  new FormAttachment(0, 1000, 63);
//			button4LData.width = 77;
//			button4LData.height = 28;
//			button4.setLayoutData(button4LData);
////			button4.addSelectionListener(new SelectionAdapter() {
////				public void widgetSelected(SelectionEvent evt) {
////					button1WidgetSelected(evt);
////				}
////			});
//		}
//		{
//			button2 = new Button(this, SWT.PUSH | SWT.CENTER);
//			FormData button2LData = new FormData();
//			button2LData.left =  new FormAttachment(0, 1000, 770);
//			button2LData.top =  new FormAttachment(0, 1000, 63);
//			button2LData.width = 77;
//			button2LData.height = 28;
//			button2.setLayoutData(button2LData);
//			button2.setText("Clear");
////			button2.addSelectionListener(new SelectionAdapter() {
////				public void widgetSelected(SelectionEvent evt) {
////					button2WidgetSelected(evt);
////				}
////			});
//		}
    	
	}
	
	private void button1WidgetSelected(SelectionEvent evt) {
		combo1 = new Combo(this, SWT.DROP_DOWN|SWT.READ_ONLY);
		combo2 = new Combo(this, SWT.DROP_DOWN|SWT.READ_ONLY);
		combo3 = new Combo(this, SWT.DROP_DOWN|SWT.READ_ONLY);
		String string1 = combo1.getText();
		String string2 = combo3.getText();
		String string3 = combo2.getText();
		
		
		if (string3.equals("Slow")) {
        	//cardioCoeficient = cardioCoeficient * 1.6;
        } else if (string3.equals("Normal"))  {
        	//cardioCoeficient = cardioCoeficient * 1.2;
        } else if (string3.equals( "Fast")) {
        	//cardioCoeficient = cardioCoeficient * 0.9;
        }
		if (string1.equals("Male")) {
			if (string2.equals("Beginner")) {
	//			if (age >= 0 && age <= 20) {
	//    			cardioCoeficient = cardioCoeficient * 0.5;
	//            } if (age > 20 && age <= 30) {
	//            	cardioCoeficient = cardioCoeficient * 1.2;
	//            } if (age > 30 && age <= 45) {
	//            	cardioCoeficient = cardioCoeficient * 2.0;
	//            } if (age > 45) {
	//            	cardioCoeficient = cardioCoeficient * 1.5;
	//            }
			} else if(string2.equals("Intermediate")) {
	//			if (age >= 0 && age <= 20) {
	//    			cardioCoeficient = cardioCoeficient * 0.5;
	//            } if (age > 20 && age <= 30) {
	//            	cardioCoeficient = cardioCoeficient * 1.2;
	//            } if (age > 30 && age <= 45) {
	//            	cardioCoeficient = cardioCoeficient * 2.0;
	//            } if (age > 45) {
	//            	cardioCoeficient = cardioCoeficient * 1.5;
	//            }
			} else if(string2.equals("Advanced")) {
	//			if (age >= 0 && age <= 20) {
	//    			cardioCoeficient = cardioCoeficient * 0.5;
	//            } if (age > 20 && age <= 30) {
	//            	cardioCoeficient = cardioCoeficient * 1.2;
	//            } if (age > 30 && age <= 45) {
	//            	cardioCoeficient = cardioCoeficient * 2.0;
	//            } if (age > 45) {
	//            	cardioCoeficient = cardioCoeficient * 1.5;
	//            }
			}
		} else if (string1.equals("Female")) {
			if (string2.equals("Beginner")) {
	//			if (age >= 0 && age <= 20) {
	//    			cardioCoeficient = cardioCoeficient * 0.5;
	//            } if (age > 20 && age <= 30) {
	//            	cardioCoeficient = cardioCoeficient * 1.2;
	//            } if (age > 30 && age <= 45) {
	//            	cardioCoeficient = cardioCoeficient * 2.0;
	//            } if (age > 45) {
	//            	cardioCoeficient = cardioCoeficient * 1.5;
	//            }
			} else if(string2.equals("Intermediate")) {
	//			if (age >= 0 && age <= 20) {
	//    			cardioCoeficient = cardioCoeficient * 0.5;
	//            } if (age > 20 && age <= 30) {
	//            	cardioCoeficient = cardioCoeficient * 1.2;
	//            } if (age > 30 && age <= 45) {
	//            	cardioCoeficient = cardioCoeficient * 2.0;
	//            } if (age > 45) {
	//            	cardioCoeficient = cardioCoeficient * 1.5;
	//            }
			} else if(string2.equals("Advanced")) {
	//			if (age >= 0 && age <= 20) {
	//    			cardioCoeficient = cardioCoeficient * 0.5;
	//            } if (age > 20 && age <= 30) {
	//            	cardioCoeficient = cardioCoeficient * 1.2;
	//            } if (age > 30 && age <= 45) {
	//            	cardioCoeficient = cardioCoeficient * 2.0;
	//            } if (age > 45) {
	//            	cardioCoeficient = cardioCoeficient * 1.5;
	//            }
			}
		}
}
			
			
//			FormData combo1LData = new FormData();
//			combo1LData.width = 177;
//			combo1LData.height = 23;
//			combo1LData.left =  new FormAttachment(0, 1000, 455);
//			combo1LData.top =  new FormAttachment(0, 1000, 68);
//			combo1.setLayoutData(combo1LData);
//			combo1.setItems(new java.lang.String[] {"Beginner"," Intermediate"," Advanced"});
//		}
//		{
//			combo2 = new Combo(this, SWT.DROP_DOWN|SWT.READ_ONLY);
//			FormData combo2LData = new FormData();
//			combo2LData.width = 177;
//			combo2LData.height = 23;
//			combo2LData.left =  new FormAttachment(0, 1000, 231);
//			combo2LData.top =  new FormAttachment(0, 1000, 68);
//			combo2.setLayoutData(combo2LData);
//			combo2.setItems(new java.lang.String[] {"Slow","Normal","Fast"});
//		}
//		{
//			combo3 = new Combo(this, SWT.DROP_DOWN|SWT.READ_ONLY);
//			FormData combo3LData = new FormData();
//			combo3LData.width = 177;
//			combo3LData.height = 23;
//			combo3LData.left =  new FormAttachment(0, 1000, 455);
//			combo3LData.top =  new FormAttachment(0, 1000, 68);
//			combo3.setLayoutData(combo3LData);
//			combo3.setItems(new java.lang.String[] {"Beginner"," Intermediate"," Advanced"});
//			combo3.setLayoutDeferred(true);
//		}
//		{
//			text1 = new Text(this, SWT.NONE);
//			FormData text1LData = new FormData();
//			text1LData.width = 197;
//			text1LData.height = 21;
//			text1LData.left =  new FormAttachment(0, 1000, 14);
//			text1LData.top =  new FormAttachment(0, 1000, 133);
//			text1.setLayoutData(text1LData);
//			text1.setText("0");
//		}
//		{
//			text2 = new Text(this, SWT.NONE);
//			FormData text2LData = new FormData();
//			text2LData.width = 197;
//			text2LData.height = 21;
//			text2LData.left =  new FormAttachment(0, 1000, 231);
//			text2LData.top =  new FormAttachment(0, 1000, 133);
//			text2.setLayoutData(text2LData);
//			text2.setText("0");
//		}
//		{
//			text3 = new Text(this, SWT.NONE);
//			FormData text3LData = new FormData();
//			text3LData.width = 197;
//			text3LData.height = 21;
//			text3LData.left =  new FormAttachment(0, 1000, 455);
//			text3LData.top =  new FormAttachment(0, 1000, 133);
//			text3.setLayoutData(text3LData);
//			text3.setText("0");
//		}
	
	private void button2WidgetSelected(SelectionEvent evt) {
		
	}
}







//Same structure as the WorkoutGenerator
/*package barad.examples; 

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.printing.Printer;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import barad.symboliclibrary.path.Path;
import barad.symboliclibrary.strings.SymbolicString;
import barad.symboliclibrary.ui.events.SymbolicEvent;
import barad.symboliclibrary.ui.events.SymbolicSelectionEvent;
import barad.symboliclibrary.ui.widgets.SymbolicComposite;

@SuppressWarnings("all")
public class MainWindow extends Composite {
	private Combo combo1;
	private Label label1;
	private Label label8;
	private Label label7;
	private Text text3;
	private Text text2;
	private Text text1;
	private StyledText styledText1;
	private Label label6;
	private Label label5;
	private Label label4;
	private Button button2;
	private Combo combo3;
	private Combo combo2;
	private Label label3;
	private Label label2;
	private Printer printer; 
	private static Shell shell;
	private Button button4;

	//to be removed
	public static void main(String[] ars) {
		showGUI();
	}
	
	//to be removed
	public static void showGUI() { 
	
	}
	
	//to be removed
	public MainWindow(Composite parent, int style) {
		super(new Shell(Display.getDefault()), SWT.NULL);
		initGUI();
	}
	
	//to be removed
	private void initGUI() {
	
	}
	
	private void button1WidgetSelected(SelectionEvent evt) {
		 
	}
	
	private void button2WidgetSelected(SelectionEvent evt) {
		
	}
}
*/









//private Combo combo;
//private StyledText styledText;
	
	//INTEGER EXAMPLES
	//public void test(int x, int y) {
		//Integers_1 - OK
		/*
		int z = 0;
		if (x >= 0) {
			z = 1;
        } else {
        	z = 2;
        } 
        */
		
		//Integers_2 - OK
		/*
		int z = 0;
		if (x <= 0) {
			z = 1;
        } else {
        	z = 2;
        } 
        */
		
		//Integers_3 - OK
		/*
		int z = 0;
		if (x < 0) {
			z = 1;
        } else {
        	z = 2;
        } 
        */
		
		//Integers_4 - OK
		/*
		int z = 0;
		if (x > 0) {
			z = 1;
        } else {
        	z = 2;
        } 
        */
		
		//Integers_5 - OK
		/*
		int z = 0;
		if (x == 0) {
			z = 1;
        } else {
        	z = 2;
        } 
        */
		
		//Integers_6 - OK
		/*
		int z = 0;
		if (x != 0) {
			z = 1;
        } else {
        	z = 2;
        } 
        */
        
		//Integers_7 - OK
		/*
		if (x > 12) {
			if (y > 50) {
				y = 20;
			} else {
				y = 30;
			}
			x = 10;
		} else {
			x = 40;
		}
		*/

		//Integers_8 - OK
		/*
		if (x > 12) {
			if (y > 50) {
				y = 20;
			}
			x = 10;
		} else {
			x = 40;
		}
		*/
		
		//Integers_9 - OK
		/*
		switch (x) {
		case 1:
			y = 1;
			break;
		case 8:
			y = 2;
			break;
		}
		*/
		
		//Integers_10 - OK
		/*
		switch (x) {
		case 1:
			y = 1;
			break;
		case 2:
			y = 2;
			break;
		}
		*/
		
		//Integers_11 - FIXME
	    /*
		int z = 0;
		switch (x) {
		case 1:
			switch (y) {
			case 1:
				if (y > 10) {
					z = 1;
				} else {
					z = 2;
				}
				break;
			case 2:
				z = 2;
				break;
			}
			break;
		case 2:
			z = 3;
			break;
		}
		*/
		
		//Integers_11 FIXME
		/*
		int z = 0;
		switch (y) {
		case 1:
			if (y > 10) {
				z = 1;
			} else {
				z = 2;
			}
			break;
		case 2:
			z = 2;
			break;
		}
		*/
		
		//Integers_12 - OK
		/*
		int z = 0;
		if (y > 10) {
			switch (y) {
			case 1:
				z = 1;
				break;
			case 2:
				z = 2;
				break;
			}
		} else {
			z = 2;
		}
		*/

		//Integers_13 - OK
		/*
		if (x > 1) {
			y = y + 5;
			if (y == 20) {
				x = 12;
			}
		}
		else {
			x = 40;
		}
		*/
		
		//Integers_14 -OK
		/*
		int z = 0;
		if (x >= 0 && x <= 20) {
			z = 1;
        }
        */
	//}
			
	//FLOAT EXAMPLES
	//public void test(float x, float y) {
		//Floats_1 - OK
		/*
		int z = 0;
		if (x >= 0) {
			z = 1;
        } else {
        	z = 2;
        } 
        */
		
		//Floats_2 - OK
		/*
		int z = 0;
		if (x <= 0) {
			z = 1;
        } else {
        	z = 2;
        } 
        */
		
		//Floats_3 - OK
		/*
		int z = 0;
		if (x < 0) {
			z = 1;
        } else {
        	z = 2;
        } 
        */
		
		//Floatss_4 - OK
		/*
		int z = 0;
		if (x > 0) {
			z = 1;
        } else {
        	z = 2;
        } 
        */
		
		//Floats_5 - OK
		/*
		int z = 0;
		if (x == 0) {
			z = 1;
        } else {
        	z = 2;
        } 
        */
		
		//Floats_6 - OK
		/*
		int z = 0;
		if (x != 0) {
			z = 1;
        } else {
        	z = 2;
        } 
        */
	
		//Floats_7 - OK
		/*
		if (x > 12) {
			if (y > 50) {
				y = 20;
			} else {
				y = 30;
			}
			x = 10;
		} else {
			x = 40;
		}
		*/
		
		//Floats_8 - OK
		/*
		if (x > 12) {
			if (y > 50) {
				y = 20;
			}
			x = 10;
		} else {
			x = 40;
		}
		*/
	
		//Floats_9 - OK
		/*
		if (x > 1) {
			y = y + 5;
			if (y == 20) {
				x = 12;
			}
		}
		else {
			x = 40;
		}
		*/

		//Floats_10 - OK
		/*
		if (x < 10) {
			x = 7;			
			if (y <= 20) {
				x = 100;
				y = 100;
				x = x + 20;
				y = y + 10;
			} else {
				x = 1;
			}
		} else {
			x = 22;
		}
		*/
	
		//Float_11 - OK
	   /*	
	   if (x < 20.0f) {
			x = 10.0f;
		} else {
			nextMethod(x, y);
		}
	}

	private void nextMethod(float x, float y) {
		if (x >= 10.0f) {
			y = 10f;
		} else {
			y = 20f;
		}
	}*/
	
	
	//public void test(String string1, String string2, int age) {	
		//STRING EXAMPLES
		//String_1 - OK
		/*
		int x = 0;
		if (string1.equals("test")) {
			String substring = string1.substring(2);
			if (substring.equals("t")) {
				x = 1;
			}
		} else {
			x = 2;
		}
		*/
		
		//String_1 - OK
		/*
		int x = 0;
		if (string1.equals("test")) {
			String substring = string1.substring(2);
			if (substring.equals("t")) {
				x = 1;
			}
		} else {
			x = 2;
		}
		*/
		
		//String_2 - OK
		/*
		int x = 0;
		if (string1.equals("Beginner")) {
			x = 1;
		} else if(string1.equals("Intermediate")) {
			x = 2;
		} else if(string1.equals("Advanced")) {
			x = 3;
		}
		*/
	
		//String_2 - OK
		/*
		int x = 0;
		if (string1.equals("Male")) {
			x = 1;
		} else {
			x = 2;
		} 
		*/
		
//		double cardioCoeficient = 0;
//		if (string1.equals("Male")) {
//			if (string2.equals("Beginner")) {
//				if (age >= 0 && age <= 20) {
//        			cardioCoeficient = cardioCoeficient * 0.5;
//                } if (age > 20 && age <= 30) {
//                	cardioCoeficient = cardioCoeficient * 1.2;
//                } if (age > 30 && age <= 45) {
//                	cardioCoeficient = cardioCoeficient * 2.0;
//                } if (age > 45) {
//                	cardioCoeficient = cardioCoeficient * 1.5;
//                }
//			} else if(string2.equals("Intermediate")) {
//				if (age >= 0 && age <= 20) {
//        			cardioCoeficient = cardioCoeficient * 0.5;
//                } if (age > 20 && age <= 30) {
//                	cardioCoeficient = cardioCoeficient * 1.2;
//                } if (age > 30 && age <= 45) {
//                	cardioCoeficient = cardioCoeficient * 2.0;
//                } if (age > 45) {
//                	cardioCoeficient = cardioCoeficient * 1.5;
//                }
//			} else if(string2.equals("Advanced")) {
//				if (age >= 0 && age <= 20) {
//        			cardioCoeficient = cardioCoeficient * 0.5;
//                } if (age > 20 && age <= 30) {
//                	cardioCoeficient = cardioCoeficient * 1.2;
//                } if (age > 30 && age <= 45) {
//                	cardioCoeficient = cardioCoeficient * 2.0;
//                } if (age > 45) {
//                	cardioCoeficient = cardioCoeficient * 1.5;
//                }
//			}
//		} else if (string1.equals("Female")) {
//			if (string2.equals("Beginner")) {
//				if (age >= 0 && age <= 20) {
//        			cardioCoeficient = cardioCoeficient * 0.5;
//                } if (age > 20 && age <= 30) {
//                	cardioCoeficient = cardioCoeficient * 1.2;
//                } if (age > 30 && age <= 45) {
//                	cardioCoeficient = cardioCoeficient * 2.0;
//                } if (age > 45) {
//                	cardioCoeficient = cardioCoeficient * 1.5;
//                }
//			} else if(string2.equals("Intermediate")) {
//				if (age >= 0 && age <= 20) {
//        			cardioCoeficient = cardioCoeficient * 0.5;
//                } if (age > 20 && age <= 30) {
//                	cardioCoeficient = cardioCoeficient * 1.2;
//                } if (age > 30 && age <= 45) {
//                	cardioCoeficient = cardioCoeficient * 2.0;
//                } if (age > 45) {
//                	cardioCoeficient = cardioCoeficient * 1.5;
//                }
//			} else if(string2.equals("Advanced")) {
//				if (age >= 0 && age <= 20) {
//        			cardioCoeficient = cardioCoeficient * 0.5;
//                } if (age > 20 && age <= 30) {
//                	cardioCoeficient = cardioCoeficient * 1.2;
//                } if (age > 30 && age <= 45) {
//                	cardioCoeficient = cardioCoeficient * 2.0;
//                } if (age > 45) {
//                	cardioCoeficient = cardioCoeficient * 1.5;
//                }
//			}
//		}
//	}
	
	
	
	
	
	
	
	
//	int c = 0;
//	String myName = "Svetlio";
//	if (string.equals(myName)) {
//		c = 20;
//	}/* else {
//		c = 10;
//	}
	
//	*/	
	

	
	/*
	public MainWindow() {
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
	public MainWindow() {
		
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
	
//}