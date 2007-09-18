package org.fitness.generator;
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
import org.eclipse.swt.printing.PrintDialog;
import org.eclipse.swt.printing.Printer;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.cloudgarden.resource.SWTResourceManager;

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
public class MainWindow extends org.eclipse.swt.widgets.Composite {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}

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
	private Group group1;
	private Button button2;
	private Combo combo3;
	private Combo combo2;
	private Label label3;
	private Label label2;
	private Printer printer; 
	private static Shell shell;
	private Button button4;

	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/
	public static void main(String[] args) {
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
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	public MainWindow(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
		combo1.select(1);
		combo2.select(2);
		combo3.select(2);
	}

	private void initGUI() {
		try {
			FormLayout thisLayout = new FormLayout();
			this.setLayout(thisLayout);
			this.setSize(868, 511);
			{
				label8 = new Label(this, SWT.NONE);
				label8.setText("WORKOUT GENERATOR ");
				FormData label8LData = new FormData();
				label8LData.width = 217;
				label8LData.height = 21;
				label8LData.left =  new FormAttachment(0, 1000, 7);
				label8LData.top =  new FormAttachment(0, 1000, 14);
				label8.setLayoutData(label8LData);
				label8.setFont(SWTResourceManager.getFont("Segoe UI", 14, 2, false, false));
			}
			{
				text1 = new Text(this, SWT.NONE);
				FormData text1LData = new FormData();
				text1LData.width = 197;
				text1LData.height = 21;
				text1LData.left =  new FormAttachment(0, 1000, 14);
				text1LData.top =  new FormAttachment(0, 1000, 133);
				text1.setLayoutData(text1LData);
				text1.setText("0");
			}
			{
				styledText1 = new StyledText(this, SWT.H_SCROLL|SWT.V_SCROLL);
				FormData styledText1LData = new FormData();
				styledText1LData.width = 830;
				styledText1LData.height = 298;
				styledText1LData.left =  new FormAttachment(0, 1000, 7);
				styledText1LData.top =  new FormAttachment(0, 1000, 182);
				styledText1.setLayoutData(styledText1LData);
				styledText1.setFont(SWTResourceManager.getFont("Segoe UI", 12, 0, false, false));
				styledText1.setCursor(SWTResourceManager.getCursor(SWT.CURSOR_WAIT));
				styledText1.setDoubleClickEnabled(false);
				styledText1.setHorizontalIndex(10);
				styledText1.setHorizontalPixel(10);
				styledText1.setEditable(false);
			}
			{
				group1 = new Group(this, SWT.NONE);
				FormLayout group1Layout = new FormLayout();
				group1.setLayout(group1Layout);
				group1.setText("Generation");
				FormData group1LData = new FormData();
				group1LData.width = 176;
				group1LData.height = 52;
				group1LData.top =  new FormAttachment(0, 1000, 54);
				group1LData.right =  new FormAttachment(1000, 1000, -14);
				group1.setLayoutData(group1LData);
				{
					button2 = new Button(group1, SWT.PUSH | SWT.CENTER);
					FormData button2LData = new FormData();
					button2LData.width = 77;
					button2LData.height = 28;
					button2LData.left =  new FormAttachment(0, 1000, 92);
					button2LData.top =  new FormAttachment(0, 1000, 13);
					button2.setLayoutData(button2LData);
					button2.setText("Clear");
					button2.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							button2WidgetSelected(evt);
						}
					});
				}
				{
					button4 = new Button(group1, SWT.PUSH | SWT.CENTER);
					button4.setText("Generate");
					FormData button4LData = new FormData();
					button4LData.width = 77;
					button4LData.height = 28;
					button4LData.left =  new FormAttachment(0, 1000, 7);
					button4LData.top =  new FormAttachment(0, 1000, 13);
					button4.setLayoutData(button4LData);
					button4.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent evt) {
							button1WidgetSelected(evt);
						}
					});
				}
			}
			{
				combo3 = new Combo(this, SWT.DROP_DOWN|SWT.READ_ONLY);
				FormData combo3LData = new FormData();
				combo3LData.width = 177;
				combo3LData.height = 23;
				combo3LData.left =  new FormAttachment(0, 1000, 455);
				combo3LData.top =  new FormAttachment(0, 1000, 68);
				combo3.setLayoutData(combo3LData);
				combo3.setItems(new java.lang.String[] {"Beginner"," Intermediate"," Advanced"});
				combo3.setLayoutDeferred(true);
			}
			{
				combo2 = new Combo(this, SWT.DROP_DOWN|SWT.READ_ONLY);
				FormData combo2LData = new FormData();
				combo2LData.width = 177;
				combo2LData.height = 23;
				combo2LData.left =  new FormAttachment(0, 1000, 231);
				combo2LData.top =  new FormAttachment(0, 1000, 68);
				combo2.setLayoutData(combo2LData);
				combo2.setItems(new java.lang.String[] {"Slow","Normal","Fast"});
			}
			{
				label3 = new Label(this, SWT.NONE);
				label3.setText("Experience");
				FormData label3LData = new FormData();
				label3LData.width = 56;
				label3LData.height = 21;
				label3LData.top =  new FormAttachment(0, 1000, 42);
				label3LData.left =  new FormAttachment(0, 1000, 455);
				label3.setLayoutData(label3LData);
			}
			{
				label2 = new Label(this, SWT.NONE);
				label2.setText("Metabolism");
				FormData label2LData = new FormData();
				label2LData.width = 70;
				label2LData.height = 21;
				label2LData.left =  new FormAttachment(0, 1000, 231);
				label2LData.top =  new FormAttachment(0, 1000, 42);
				label2.setLayoutData(label2LData);
			}
			{
				label1 = new Label(this, SWT.NONE);
				FormData label1LData = new FormData();
				label1LData.width = 56;
				label1LData.height = 21;
				label1LData.left =  new FormAttachment(0, 1000, 14);
				label1LData.top =  new FormAttachment(0, 1000, 42);
				label1.setLayoutData(label1LData);
				label1.setText("Gender");
			}
			{
				label4 = new Label(this, SWT.NONE);
				label4.setText("Age(years)");
				FormData label4LData1 = new FormData();
				label4LData1.width = 70;
				label4LData1.height = 21;
				label4LData1.left =  new FormAttachment(0, 1000, 14);
				label4LData1.top =  new FormAttachment(0, 1000, 112);
				label4.setLayoutData(label4LData1);
			}
			{
				label5 = new Label(this, SWT.NONE);
				label5.setText("Height(cm)");
				FormData label5LData = new FormData();
				label5LData.width = 63;
				label5LData.height = 21;
				label5LData.left =  new FormAttachment(0, 1000, 231);
				label5LData.top =  new FormAttachment(0, 1000, 112);
				label5.setLayoutData(label5LData);
			}
			{
				label6 = new Label(this, SWT.NONE);
				label6.setText("Weight(kg)");
				FormData label6LData = new FormData();
				label6LData.width = 63;
				label6LData.height = 21;
				label6LData.left =  new FormAttachment(0, 1000, 455);
				label6LData.top =  new FormAttachment(0, 1000, 112);
				label6.setLayoutData(label6LData);
			}
			{
				text2 = new Text(this, SWT.NONE);
				FormData text2LData = new FormData();
				text2LData.width = 197;
				text2LData.height = 21;
				text2LData.left =  new FormAttachment(0, 1000, 231);
				text2LData.top =  new FormAttachment(0, 1000, 133);
				text2.setLayoutData(text2LData);
				text2.setText("0");
			}
			{
				text3 = new Text(this, SWT.NONE);
				FormData text3LData = new FormData();
				text3LData.width = 197;
				text3LData.height = 21;
				text3LData.left =  new FormAttachment(0, 1000, 455);
				text3LData.top =  new FormAttachment(0, 1000, 133);
				text3.setLayoutData(text3LData);
				text3.setText("0");
			}
			{
				combo1 = new Combo(this, SWT.DROP_DOWN|SWT.READ_ONLY);
				FormData combo1LData = new FormData();
				combo1LData.width = 177;
				combo1LData.height = 23;
				combo1LData.left =  new FormAttachment(0, 1000, 14);
				combo1LData.top =  new FormAttachment(0, 1000, 68);
				combo1.setLayoutData(combo1LData);
				combo1.setItems(new java.lang.String[] {"Male","Female"});
			}
			{
				label7 = new Label(this, SWT.NONE);
				label7.setText("Weekly workout program");
				FormData label7LData = new FormData();
				label7LData.width = 168;
				label7LData.height = 21;
				label7LData.left =  new FormAttachment(0, 1000, 7);
				label7LData.top =  new FormAttachment(0, 1000, 161);
				label7.setLayoutData(label7LData);
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void menuPrint() {
		PrintDialog dialog = new PrintDialog(shell, SWT.NONE);
		PrinterData data = dialog.open();
		if (data == null) {
			return;
		}
		if (data.printToFile) {
			data.fileName = "workout.txt";
		}
		printer = new Printer();
		String textToPrint = styledText1.getText();
	}
	
	private void button1WidgetSelected(SelectionEvent evt) {	
		//initailize
		String workout = "";
		styledText1.setText(workout);
		if (text1.getText().trim().equals("") || Integer.parseInt(text1.getText().trim()) == 0) {
            MessageBox messageBox = new MessageBox(shell);
            messageBox.setText("Incomplete input");
            messageBox.setMessage("Please enter your age!!!");
            messageBox.open();
            return;
        }
        if (text2.getText().trim().equals("") || Integer.parseInt(text2.getText().trim()) == 0) {
            MessageBox messageBox = new MessageBox(shell);
            messageBox.setText("Incomplete input");
            messageBox.setMessage("Please enter your height!!!");
            messageBox.open();
            return;
        }
        if (text3.getText().trim().equals("") || Integer.parseInt(text3.getText().trim()) == 0) {
        	 MessageBox messageBox = new MessageBox(shell);
        	 messageBox.setText("Incomplete input");
             messageBox.setMessage("Please enter your weight!!!");
             messageBox.open();
            return;
        }
        //Input data
        String gender = combo1.getText().trim();
        String metabolism = combo2.getText().trim();
        String experience = combo3.getText().trim();
        int age = Integer.parseInt(text1.getText().trim());
        int height = Integer.parseInt(text2.getText().trim());
        int weight = Integer.parseInt(text3.getText().trim()); 
        //Auiliary variables for coeficients
        int repCoeficient = 0;
        int setCoeficient = 0;
        double cardioCoeficient = 0;
        cardioCoeficient = (height - weight) / 100.0;
        //set cardio coeficient
        if (metabolism.equals("Slow")) {
        	cardioCoeficient = cardioCoeficient * 1.6;
        } else if (metabolism.equals("Normal"))  {
        	cardioCoeficient = cardioCoeficient * 1.2;
        } else if (metabolism.equals( "Fast")) {
        	cardioCoeficient = cardioCoeficient * 0.9;
        }
        if (gender.equals("Male")) {
        	if (experience.equals("Beginner")) {
        		if (age >= 0 && age <= 20) {
        			cardioCoeficient = cardioCoeficient * 0.5;
                }
                if (age > 20 && age <= 30) {
                	cardioCoeficient = cardioCoeficient * 1.2;
                }
                if (age > 30 && age <= 45) {
                	cardioCoeficient = cardioCoeficient * 2.0;
                }
                if (age > 45) {
                	cardioCoeficient = cardioCoeficient * 1.5;
                }
                repCoeficient = 12;
                setCoeficient = 2;
            } else if (experience.equals("Intermediate")) {
            	if (age >= 0 && age <= 20) {
                	cardioCoeficient = cardioCoeficient * 0.5;
                }
                if (age > 20 && age <= 30) {
                	cardioCoeficient = cardioCoeficient * 1.2;
                }
                if (age > 30 && age <= 45) {
                	cardioCoeficient = cardioCoeficient * 2.0;
                }
                if (age > 45) {
                	cardioCoeficient = cardioCoeficient * 1.6;
                }
                repCoeficient = 10;
                setCoeficient = 3;
            } else if (experience.equals("Advanced")) {
            	if (age >= 0 && age <= 20) {
                	cardioCoeficient = cardioCoeficient * 0.5;
            	}
	            if (age > 20 && age <= 30) {
	            	cardioCoeficient = cardioCoeficient * 1.2;
	            }
	            if (age > 30 && age <= 45) {
	             	cardioCoeficient = cardioCoeficient * 2.0;
	            }
	            if (age > 45) {
	            	cardioCoeficient = cardioCoeficient * 1.7;
	            }
	            repCoeficient = 8;
	            setCoeficient = 4;
	        }
	        workout = workout + "\tWEEKLY WORKOUT FOR MALE\n\n";
	        workout = workout + "Metabolism: " + metabolism + "\n";
	        workout = workout + "Experience: " + experience + "\n";
	        workout = workout + "Age: " + age + "\n";
	        workout = workout + "Weight: " + weight + "\n";
	        workout = workout + "Height: " + height + "\n";
	        //Monday
	        workout = workout + "   \nMonday:\n";
	        //Pecs
	        workout = workout + "   \n\tPecs\n";
	        workout = workout + "\t\tBench Press -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(repCoeficient) + " reps\n";
	        if (experience.equals("Beginner")) {
	        	workout = workout + "\t\tInclined Bench Press(head up) -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
	        }
	        if (experience.equals("Advanced")) {
	        	workout = workout + "\t\tInclined Bench Press(head up) -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(repCoeficient) + " reps\n";
	        	workout = workout + "\t\tDumbell Fly -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
	        }
	        ///Triceps
	        workout = workout + "   \n\tTriceps\n";
	        workout = workout + "\t\tFrench Press -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(repCoeficient) + " reps\n";
	        if (experience.equals("Intermediate")) {
	        	workout = workout + "\t\tPull Down extensions -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
	        }
	        if (experience.equals("Advanced")) {
	        	workout = workout + "\t\tBech Press(narrow grip) -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(repCoeficient) + " reps\n";
	            workout = workout + "\t\tPull Down extensions -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
	        }
	        ///Cardio
	        workout = workout + "   \n\tCardio\n";
	        workout = workout + "\t\tCardio -- " + Math.round(30 * cardioCoeficient) + "minutes\n";      
	        ///Tuesday
	        workout = workout + "   \nTuesday:\n";
	        workout = workout + "\t Free day\n";
	        //Wendesday
	        workout = workout + "   \nWendesday:\n";
	        ///Back
	        workout = workout + "   \n\tBack\n";
	        workout = workout + "\t\tPull Down(behind the neck) -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(repCoeficient) + " reps\n";
	        if (experience.equals("Intermediate")){
	        	workout = workout + "\t\tDumbell Rowing -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
	        }
	        if (experience.equals("Advanced")) {
	        	workout = workout + "\t\tBarbel Rowing -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(repCoeficient) + " reps\n";
	            workout = workout + "\t\tDumbell Rowing -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
	        }
	        ///Biceps
	        workout = workout + "   \n\tBiceps\n";
	        workout = workout + "\t\tBarbell Curl -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(repCoeficient) + " reps\n";
	        if (experience.equals("Intermediate")) {
	        	workout = workout + "\t\tScott Bench(with Dumbbelll) -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
	        }
	        if (experience.equals("Advanced")) {
	        	workout = workout + "\t\tDumbell Rowing(sitting) -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(repCoeficient) + " reps\n";
	            workout = workout + "\t\tScott Bench(with Dumbell) -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
	        }
	        ///Cardio
	        workout = workout + "   \n\tCardio\n";
	        workout = workout + "\t\tCardio -- " + Math.round(30 * cardioCoeficient) + "minutes\n";
	        //Thursday
	        workout = workout + "   \nThursday:\n";
	        workout = workout + "\t Free day\n";
	        //Friday
	        workout = workout + "   \nFriday:\n";
	        ///Legs
	        workout = workout + "   \n\tLegs\n";
	        workout = workout + "\t\tLeg Extension -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
	        workout = workout + "\t\tLeg Curl -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
	        if (experience.equals("Intermediate")) {
	        	workout = workout + "\t\tSquats -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(repCoeficient) + " reps\n";
	        }
	        if (experience.equals("Advanced")) {
	        	workout = workout + "\t\tSquats -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(repCoeficient) + " reps\n";
	            workout = workout + "\t\tLeg Press -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
	        }
	        ///Shoulders
	        workout = workout + "   \n\tShoulders\n";
	        workout = workout + "\t\tBarbell Press(behind neck) -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
	        if (experience.equals("Intermediate")) {
	        	workout = workout + "\t\tSide Fly -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
	        }
	        if (experience == "Advanced") {
	        	workout = workout + "\t\tSide Fly -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
	            workout = workout + "\t\tSide Fly(inclined) -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
	            workout = workout + "\t\tFront Pickups(Dumbell) -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
	        }
	        ///Cardio
	        workout = workout + "   \n\tCardio\n";
	        workout = workout + "\t\tCardio -- " + Math.round(30 * cardioCoeficient) + "minutes\n";
	        //Saturday
	        workout = workout + "   \nSaturday:\n";
	        workout = workout + "\t Free day\n";
	        //Sunday
	        workout = workout + "   \nSunday:\n";
	        workout = workout + "\t Free day\n";
	    } else {
	         if (experience.equals("Beginner")) {
	         	if (age >= 0 && age <= 20) {
	            	cardioCoeficient = cardioCoeficient * 0.7;
	            }
	            if (age > 20 && age <= 30) {
	            	cardioCoeficient = cardioCoeficient * 1.5;
	            }
	            if (age > 30 && age <= 45) {
	            	cardioCoeficient = cardioCoeficient * 2.2;
	            }
	            if (age > 45) {
	            	cardioCoeficient = cardioCoeficient * 1.5;
	            }
	            repCoeficient = 20;
	            setCoeficient = 1;
	        } else if (experience.equals("Intermediate")) {
	        	 if (age >= 0 && age <= 20) {
	             	cardioCoeficient = cardioCoeficient * 0.7;
	             }
	             if (age > 20 && age <= 30) {
	             	cardioCoeficient = cardioCoeficient * 1.5;
	             }
	             if (age > 30 && age <= 45) {
	             	cardioCoeficient = cardioCoeficient * 2.2;
	             }
	             if (age > 45) {
	            	 cardioCoeficient = cardioCoeficient * 1.6;
	             }
	             repCoeficient = 15;
	             setCoeficient = 2;
	        } else if (experience.equals("Advanced")) {
	        	if (age >= 0 && age <= 20) {
	            	cardioCoeficient = cardioCoeficient * 0.7;
	            }
	            if (age > 20 && age <= 30) {
	            	cardioCoeficient = cardioCoeficient * 1.5;
	            }
	            if (age > 30 && age <= 45) {
	            	cardioCoeficient = cardioCoeficient * 2.2;
	            }
	            if (age > 45) {
	            	cardioCoeficient = cardioCoeficient * 1.7;
	            }
	            repCoeficient = 12;
	            setCoeficient = 3;
	        }
	        workout = workout + "\tWEEKLY WORKOUT FOR FEMALE\n\n";
	        workout = workout + "Metabolism: " + metabolism + "\n";
	        workout = workout + "Experience: " + experience + "\n";
	        workout = workout + "Age: " + age + "\n";
	        workout = workout + "Weight: " + weight + "\n";
	        workout = workout + "Height: " + height + "\n";
	        //Monday
	        workout = workout + "   \nMonday:\n";
	        ///Pecs
	        workout = workout + "   \n\tPecs\n";
	        workout = workout + "\t\tBench Press -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(repCoeficient) + " reps\n";
	        if (experience.equals("Intermediate")) {
	        	workout = workout + "\t\tInclined Dumbell Fly(head up) -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
	        }
	        if (combo2.getText().equals("Advanced")) {
	        	workout = workout + "\t\tInclined Dumbell Fly(head up) -- " + String.valueOf(repCoeficient + 2) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
	        }
	        ///Back
	        workout = workout + "   \n\tBack\n";
	        workout = workout + "\t\tPull down(behind the neck) -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(repCoeficient) + " reps\n";
	        if (experience.equals("Intermediate")) {
	        	workout = workout + "\t\tDumbell Rowing -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
	        }
	        if (experience.equals("Advanced")) {
	        	workout = workout + "\t\tDumbell Rowing -- " + String.valueOf(repCoeficient + 2) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
	        }
	        ///Cardio
	        workout = workout + "   \n\tCardio\n";
	        workout = workout + "\t\tCardio -- " + Math.round(45 * cardioCoeficient) + "minutes\n";
	        //Tuesday
	        workout = workout + "   \nTuesday:\n";
	        workout = workout + "\t Free day\n";
	        //Wendesday
	        workout = workout + "   \nWendesday:\n";      
	        ///Biceps
	        workout = workout + "   \n\tBiceps\n";
	        workout = workout + "\t\tDumbell Rowing(standing) -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(repCoeficient) + " reps\n";
	        if (experience.equals("Intermediate")) {
	        	workout = workout + "\t\tDumbell Rowing(standing) -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
	        }
	        if (experience.equals("Advanced")) {
	        	workout = workout + "\t\tDumbell Rowing(standing) -- " + String.valueOf(setCoeficient + 1) + " set(s) X " + String.valueOf(repCoeficient) + " reps\n";
	        }
	        ///Triceps
	        workout = workout + "   \n\tTriceps\n";
	        workout = workout + "\t\tPull Down Extensions -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(repCoeficient) + " reps\n";
	        if (experience.equals("Intermediate")) {
	        	workout = workout + "\t\tKick Back(Dumbell) -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
	        }
	        if (experience.equals("Advanced")) {
	        	workout = workout + "\t\tKick Back(Dumbell) -- " + String.valueOf(setCoeficient + 1) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
	        }
	        ///Cardio
	        workout = workout + "   \n\tCardio\n";
	        workout = workout + "\t\tCardio -- " + Math.round(45 * cardioCoeficient) + "minutes\n";
	        //Thursday
	        workout = workout + "   \nThursday:\n";
	        workout = workout + "\t Free day\n";
	        //Friday
	        workout = workout + "   \nFriday:\n";
	        ///Legs
	        workout = workout + "   \n\tLegs\n";
	        workout = workout + "\t\tLeg Extension -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
	        workout = workout + "\t\tLeg Curl -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
	        if (experience.equals("Intermediate")) {
	        	workout = workout + "\t\tLeg Press -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(repCoeficient) + " reps\n";
	        }
	        if (experience.equals("Advanced")) {
	        	workout = workout + "\t\tLeg Press -- " + String.valueOf(setCoeficient + 1) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
	        }
	        ///Shoulders
	        workout = workout + "   \n\tShoulders\n";
	        workout = workout + "\t\tSide Fly -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
	        if (experience.equals("Intermediate")) {
	        	workout = workout + "\t\tFront Pickups(Dumbell) -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
	        }
	        if (experience.equals("Advanced")) {
	        	workout = workout + "\t\tSide Fly(inclined) -- " + String.valueOf(setCoeficient) + " set(s) X " + String.valueOf(setCoeficient + 5) + " reps\n";
	        }
	        ///Cardio
	        workout = workout + "   \n\tCardio\n";
	        workout = workout + "\t\tCardio -- " + Math.round(45 * cardioCoeficient) + "minutes\n";
	        //Saturday
	        workout = workout + "   \nSaturday:\n";
	        workout = workout + "\t Free day\n";
	        //Sunday
	        workout = workout + "   \nSunday:\n";
	        workout = workout + "\t Free day\n";
	    }
        //show the result
    	styledText1.setText(workout);
	}
	
	private void button2WidgetSelected(SelectionEvent evt) {
		styledText1.setText("");
	}
}
