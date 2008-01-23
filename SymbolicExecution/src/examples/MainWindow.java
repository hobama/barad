package examples;

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
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * This code was edited or generated using CloudGarden's Jigloo
 * SWT/Swing GUI Builder, which is free for non-commercial
 * use. If Jigloo is being used commercially (ie, by a corporation,
 * company or business for any purpose whatever) then you
 * should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details.
 * Use of Jigloo implies acceptance of these licensing terms.
 * A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
 * THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
 * LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
@SuppressWarnings("all")
public class MainWindow extends Composite {
	private Label label1;
	private Label label2;
	private Label label3;
	private Label label4;
	private Label label5;
	private Label label6;
	private Label label7;
	private Label label8;
	private Label label9;
	private Text text1;
	private Text text2;
	private Text text3;	
	private Text text4;
	private Text text5;
	private Combo combo1;
	private Button radioButton1;
	private Button radioButton2;
	private Button button1;
	private Button button2;
	private Button button3;
	private static Shell shell;

	/**
	 * Auto-generated main method to display this 
	 * org.eclipse.swt.widgets.Composite inside a new Shell.
	 */
	public static void main(String[] ars) {
		showGUI();
	}

	/**
	 * Auto-generated method to display this 
	 * org.eclipse.swt.widgets.Composite inside a new Shell.
	 */
	public static void showGUI() { 
		Display display = Display.getDefault();
		shell = new Shell(display);
		MainWindow inst = new MainWindow(shell, SWT.NULL);
		Point size = inst.getSize();
		shell.setLayout(new FillLayout());
		shell.layout();
		if(size.x == 0 && size.y == 0) {
			inst.pack();
			shell.pack();
		} else {
			Rectangle shellBounds = shell.computeTrim(0, 0, size.x, size.y);
			shell.setSize(shellBounds.width, shellBounds.height);
		}
		shell.open();
		while (!shell.isDisposed()) {
			if (!shell.getDisplay().readAndDispatch())
				display.sleep();
		}
	}

	public MainWindow(Composite parent, int style) {
		super(parent, style);
		this.setLayout(new FormLayout());
		this.setSize(665, 196);
		initGUI();
		this.layout();
		combo1.select(1);
	}

	private void initGUI() {
		{
			label1 = new Label(this, SWT.NONE);
			label1.setText("Air company");
			FormData label8LData = new FormData();
			label8LData.width = 70;
			label8LData.height = 14;
			label8LData.left =  new FormAttachment(0, 1000, 14);
			label8LData.top =  new FormAttachment(0, 1000, 7);
			label1.setLayoutData(label8LData);
		}
		{
			label2 = new Label(this, SWT.NONE);
			label2.setText("Passenger Data");
			FormData label3LData = new FormData();
			label3LData.width = 84;
			label3LData.height = 21;
			label3LData.top =  new FormAttachment(0, 1000, 7);
			label3LData.left =  new FormAttachment(0, 1000, 217);
			label2.setLayoutData(label3LData);
		}
		{
			label3 = new Label(this, SWT.NONE);
			label3.setText("Name");
			FormData label5LData = new FormData();
			label5LData.width = 35;
			label5LData.height = 21;
			label5LData.left =  new FormAttachment(0, 1000, 212);
			label5LData.top =  new FormAttachment(0, 1000, 28);
			label3.setLayoutData(label5LData);
		}
		{
			label4 = new Label(this, SWT.NONE);
			label4.setText("ID");
			FormData label6LData = new FormData();
			label6LData.width = 14;
			label6LData.height = 21;
			label6LData.left =  new FormAttachment(0, 1000, 233);
			label6LData.top =  new FormAttachment(0, 1000, 56);
			label4.setLayoutData(label6LData);
		}
		{
			label5 = new Label(this, SWT.NONE);
			label5.setText(" From Code");
			FormData label4LData1 = new FormData();
			label4LData1.width = 63;
			label4LData1.height = 21;
			label4LData1.left =  new FormAttachment(0, 1000, 385);
			label4LData1.top =  new FormAttachment(0, 1000, 56);
			label5.setLayoutData(label4LData1);
		}
		{
			label6 = new Label(this, SWT.NONE);
			FormData label1LData = new FormData();
			label1LData.width = 49;
			label1LData.height = 21;
			label1LData.left =  new FormAttachment(0, 1000, 535);
			label1LData.top =  new FormAttachment(0, 1000, 56);
			label6.setLayoutData(label1LData);
			label6.setText("To Code");
		}
		{
			label7 = new Label(this, SWT.NONE);
			FormData label9LData = new FormData();
			label9LData.width = 84;
			label9LData.height = 21;
			label9LData.left =  new FormAttachment(0, 1000, 161);
			label9LData.top =  new FormAttachment(0, 1000, 84);
			label7.setLayoutData(label9LData);
			label7.setText("Passenger Class");
		}
		{
			label8 = new Label(this, SWT.NONE);
			label8.setText("Calculations");
			FormData label7LData = new FormData();
			label7LData.width = 70;
			label7LData.height = 21;
			label7LData.left =  new FormAttachment(0, 1000, 245);
			label7LData.top =  new FormAttachment(0, 1000, 126);
			label8.setLayoutData(label7LData);
		}
		{
			label9 = new Label(this, SWT.NONE);
			FormData label10LData = new FormData();
			label10LData.width = 70;
			label10LData.height = 21;
			label10LData.left =  new FormAttachment(0, 1000, 486);
			label10LData.top =  new FormAttachment(0, 1000, 161);
			label9.setLayoutData(label10LData);
			label9.setText("Amount Due");
		}
		{
			text1 = new Text(this, SWT.NONE);
			FormData text8LData = new FormData();
			text8LData.width = 393;
			text8LData.height = 21;
			text8LData.left =  new FormAttachment(0, 1000, 252);
			text8LData.top =  new FormAttachment(0, 1000, 28);
			text1.setLayoutData(text8LData);
		}
		{
			text2 = new Text(this, SWT.NONE);
			FormData text2LData = new FormData();
			text2LData.width = 127;
			text2LData.height = 21;
			text2LData.left =  new FormAttachment(0, 1000, 252);
			text2LData.top =  new FormAttachment(0, 1000, 56);
			text2.setLayoutData(text2LData);
		}
		{
			text3 = new Text(this, SWT.NONE);
			FormData text4LData = new FormData();
			text4LData.width = 64;
			text4LData.height = 21;
			text4LData.left =  new FormAttachment(0, 1000, 462);
			text4LData.top =  new FormAttachment(0, 1000, 56);
			text3.setLayoutData(text4LData);
		}
		{
			text4 = new Text(this, SWT.NONE);
			FormData text7LData = new FormData();
			text7LData.width = 64;
			text7LData.height = 21;
			text7LData.left =  new FormAttachment(0, 1000, 581);
			text7LData.top =  new FormAttachment(0, 1000, 56);
			text4.setLayoutData(text7LData);
		}
		{
			text5 = new Text(this, SWT.NONE);
			FormData text5LData = new FormData();
			text5LData.width = 85;
			text5LData.height = 21;
			text5LData.left =  new FormAttachment(0, 1000, 560);
			text5LData.top =  new FormAttachment(0, 1000, 161);
			text5.setLayoutData(text5LData);

		}
		{
			combo1 = new Combo(this, SWT.DROP_DOWN);
			FormData combo1LData = new FormData();
			combo1LData.width = 142;
			combo1LData.height = 23;
			combo1LData.left =  new FormAttachment(0, 1000, 252);
			combo1LData.top =  new FormAttachment(0, 1000, 84);
			combo1.setLayoutData(combo1LData);
			combo1.setItems(new java.lang.String[] {"Business", "Child", "Student"});
		}
		{
			radioButton1 = new Button(this, SWT.RADIO | SWT.LEFT);
			FormData radioButton3LData = new FormData();
			radioButton3LData.width = 98;
			radioButton3LData.height = 14;
			radioButton3LData.left =  new FormAttachment(0, 1000, 14);
			radioButton3LData.top =  new FormAttachment(0, 1000, 26);
			radioButton1.setLayoutData(radioButton3LData);
			radioButton1.setText("SouthWest");
		}
		{
			radioButton2 = new Button(this, SWT.RADIO | SWT.LEFT);
			FormData button3LData = new FormData();
			button3LData.width = 98;
			button3LData.height = 14;
			button3LData.left =  new FormAttachment(0, 1000, 14);
			button3LData.top =  new FormAttachment(0, 1000, 49);
			radioButton2.setLayoutData(button3LData);
			radioButton2.setText("Lufthansa");
		}
		{
			button1 = new Button(this, SWT.PUSH | SWT.CENTER);
			FormData button4LData = new FormData();
			button4LData.width = 77;
			button4LData.height = 28;
			button4LData.left =  new FormAttachment(0, 1000, 42);
			button4LData.top =  new FormAttachment(0, 1000, 154);
			button1.setLayoutData(button4LData);
			button1.setText("Close");
			button1.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent evt) {
					button1WidgetSelected(evt);
				}
			});
		}
		{
			button2 = new Button(this, SWT.PUSH | SWT.CENTER);
			button2.setText("Generate");
			FormData button1LData = new FormData();
			button1LData.left =  new FormAttachment(0, 1000, 245);
			button1LData.top =  new FormAttachment(0, 1000, 154);
			button1LData.width = 77;
			button1LData.height = 28;
			button2.setLayoutData(button1LData);
			button2.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent evt) {
					button2WidgetSelected(evt);
				}
			});
		}
		{
			button3 = new Button(this, SWT.PUSH | SWT.CENTER);
			FormData button5LData = new FormData();
			button5LData.width = 77;
			button5LData.height = 28;
			button5LData.left =  new FormAttachment(0, 1000, 336);
			button5LData.top =  new FormAttachment(0, 1000, 154);
			button3.setLayoutData(button5LData);
			button3.setText("Clear");
			button3.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent evt) {
					button3WidgetSelected(evt);
				}
			});
		}
	}

	private void button1WidgetSelected(SelectionEvent evt) {
		dispose();
		System.exit(0);
	}

	private void button2WidgetSelected(SelectionEvent evt) {
		if (!text1.getText().trim().equals("") && !text2.getText().trim().equals("")
				&& !text3.getText().trim().equals("") && !text4.getText().trim().equals("")) {
			int amountDue = 0;
			int coeficient = 0;
			if (radioButton1.getSelection()){
				coeficient = 1;
			} else if (radioButton2.getSelection()){
				coeficient = 2;
			}
			int distanceRange = Integer.parseInt(text4.getText().trim()) - Integer.parseInt(text3.getText().trim());
			String passengerClass = combo1.getText().trim();
			if (passengerClass.equals("Business")) {
				if (distanceRange < 50) {
					amountDue = 120 * coeficient;
				} else if ( distanceRange < 60) {
					amountDue = 130 * coeficient;
				} else if (distanceRange < 70) {
					amountDue = 145 * coeficient;
				} else if (distanceRange < 80) {
					amountDue = 150 * coeficient;
				} else if (distanceRange < 100){
					amountDue = 160 * coeficient;
				}
			} else if (passengerClass.equals("Child")) {
				if (distanceRange < 40) {
					amountDue = 100 * coeficient;
				} else if (distanceRange < 45) {
					amountDue = 110 * coeficient;
				} else if (distanceRange < 50) {
					amountDue = 120 * coeficient;
				} else if (distanceRange < 70) {
					amountDue = 140 * coeficient;
				} else if (distanceRange < 80) {
					amountDue = 150 * coeficient; 
				} else if (distanceRange < 85) {
					amountDue = 155 * coeficient;
				} else if (distanceRange < 100) {
					amountDue = 160 * coeficient;
				}   
			} else if (passengerClass.equals("Student")) {
				if (distanceRange < 20) {
					amountDue = 140 * coeficient;
				} else if (distanceRange < 60) {
					amountDue = 160 * coeficient;
				} else if (distanceRange < 65) {
					amountDue = 170 * coeficient;
				} else if (distanceRange < 70) {
					amountDue = 160 * coeficient;
				} else if (distanceRange < 100) {
					amountDue = 190 * coeficient;
				}
			} else if (passengerClass.equals("Privileged")) {
				if (distanceRange < 20) {
					amountDue = 80 * coeficient;
				} else if (distanceRange < 60) {
					amountDue = 90 * coeficient;
				} else if (distanceRange < 65) {
					amountDue = 100 * coeficient;
				} else if (distanceRange < 70) {
					amountDue = 110 * coeficient;
				} else if (distanceRange < 100) {
					amountDue = 115 * coeficient;
				}
			}
			this.text5.setText(String.valueOf(amountDue));
		}
	}

	private void button3WidgetSelected(SelectionEvent evt) {
		text1.setText(""); 
		text2.setText("");
		text3.setText("");
		text4.setText("");
		text5.setText("");
		combo1.setText("");
		radioButton1.setSelection(true);
		radioButton2.setSelection(false);       
	}
}
