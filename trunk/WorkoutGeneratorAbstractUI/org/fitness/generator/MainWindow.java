package org.fitness.generator;
import java.awt.Button;

import org.barad.abstractuserinterface.AbstractAction;
import org.barad.abstractuserinterface.AbstractConversation;
import org.barad.abstractuserinterface.AbstractGroup;
import org.barad.abstractuserinterface.AbstractLabel;
import org.barad.abstractuserinterface.AbstractSelectionInput;
import org.barad.abstractuserinterface.AbstractTextInput;
import org.eclipse.swt.events.SelectionEvent;


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
public class MainWindow /*extends org.eclipse.swt.widgets.Composite*/ {
	{
		//TODO: This should be genrated
		//SWTResourceManager.registerResourceUser(this);
	}
	private AbstractTextInput textInput1;
	//private Text text1;
	private AbstractTextInput textInput2;
	//private Text text1;
	private AbstractTextInput textInput3;
	//private Text text1;
	
	private AbstractLabel label1;
	private AbstractLabel label2;
	private AbstractLabel label3;
	private AbstractLabel label4;
	private AbstractLabel label5;
	private AbstractLabel label6;
	private AbstractLabel label7;
	private AbstractLabel label8;
	
	//private StyledText styledText1;
	private AbstractTextInput textInput4;
	
	private AbstractGroup group1;
	//private Group group1;
	private Button button2;
	private Button button4;
	
	//private Combo combo1;
	private AbstractSelectionInput selectionInput1;
	//private Combo combo2;
	private AbstractSelectionInput selectionInput2;
	//private Combo combo3;
	private AbstractSelectionInput selectionInput3;
	
	//private static Shell shell;
	private static AbstractConversation abstractConversation;
	

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
		//TODO: Define application class or something like that
		
		abstractConversation = new AbstractConversation();
		/*
		Display display = Display.getDefault();
		shell = new Shell(display);
		*/
		MainWindow inst = new MainWindow(abstractConversation/*shell, SWT.NULL*/);
		
		//TODO: What to do with this crap???
		/*
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
		*/
		abstractConversation.begin();
		//shell.open();
		/*
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		*/
		//TODO: Define event dispatch routine
		while (abstractConversation.isActive()) {
			if (!abstractConversation.readAndDispatch()) {
				//Is this a separate Thread??? see SWT source
				//abstractConversation.sleep();
			}
		}
	}

	//TODO: Do I need to pass this parameter
	public MainWindow(AbstractConversation abstractConversation/*org.eclipse.swt.widgets.Composite parent, int style*/) {
		//super(parent, style);
		initGUI();
		selectionInput1.select(1);
		//combo1.select(1);
		selectionInput2.select(2);
		//combo2.select(2);
		selectionInput3.select(2);
		//combo3.select(2);
	}

	private void initGUI() {
		//try {
			//TODO: Whet to do with that 
			/*
			FormLayout thisLayout = new FormLayout();
			this.setLayout(thisLayout);
			this.setSize(868, 511);
			*/
			{
				label1 = new AbstractLabel();
				label1.setText("Gender");
				label1.setTop(42);
				label1.setLeft(14);
				label1.setHeight(21);
				label1.setWidth(56);
				
				/*
				label1 = new Label(this, SWT.NONE);
				FormData label1LData = new FormData();
				label1LData.width = 56;
				label1LData.height = 21;
				label1LData.left =  new FormAttachment(0, 1000, 14);
				label1LData.top =  new FormAttachment(0, 1000, 42);
				label1.setLayoutData(label1LData);
				label1.setText("Gender");
				*/
			}
			{
				label2 = new AbstractLabel();
				label2.setText("Metabolism");
				label2.setTop(42);
				label2.setLeft(231);
				label2.setHeight(21);
				label2.setWidth(70);
				
				/*
				label2 = new Label(this, SWT.NONE);
				label2.setText("Metabolism");
				FormData label2LData = new FormData();
				label2LData.width = 70;
				label2LData.height = 21;
				label2LData.left =  new FormAttachment(0, 1000, 231);
				label2LData.top =  new FormAttachment(0, 1000, 42);
				label2.setLayoutData(label2LData);
				*/
			}
			{
				label3 = new AbstractLabel();
				label3.setText("Experience");
				label3.setTop(42);
				label3.setLeft(455);
				label3.setHeight(21);
				label3.setWidth(56);
				
				/*
				label3 = new Label(this, SWT.NONE);
				label3.setText("Experience");
				FormData label3LData = new FormData();
				label3LData.width = 56;
				label3LData.height = 21;
				label3LData.top =  new FormAttachment(0, 1000, 42);
				label3LData.left =  new FormAttachment(0, 1000, 455);
				label3.setLayoutData(label3LData);
				*/
			}
			{
				label4 = new AbstractLabel();
				label4.setText("Age(years)");
				label4.setTop(112);
				label4.setLeft(14);
				label4.setHeight(21);
				label4.setWidth(70);
			
				/*
				label4 = new Label(this, SWT.NONE);
				label4.setText("Age(years)");
				FormData label4LData1 = new FormData();
				label4LData1.width = 70;
				label4LData1.height = 21;
				label4LData1.left =  new FormAttachment(0, 1000, 14);
				label4LData1.top =  new FormAttachment(0, 1000, 112);
				label4.setLayoutData(label4LData1);
				*/
			}
			{
				label5 = new AbstractLabel();
				label5.setText("Height(cm)");
				label5.setTop(112);
				label5.setLeft(231);
				label5.setHeight(21);
				label5.setWidth(63);
				
				/*
				label5 = new Label(this, SWT.NONE);
				label5.setText("Height(cm)");
				FormData label5LData = new FormData();
				label5LData.width = 63;
				label5LData.height = 21;
				label5LData.left =  new FormAttachment(0, 1000, 231);
				label5LData.top =  new FormAttachment(0, 1000, 112);
				label5.setLayoutData(label5LData);
				*/
			}
			{
				label6 = new AbstractLabel();
				label6.setText("Weight(kg)");
				label6.setTop(112);
				label6.setLeft(455);
				label6.setHeight(21);
				label6.setWidth(63);
				
				/*
				label6 = new Label(this, SWT.NONE);
				label6.setText("Weight(kg)");
				FormData label6LData = new FormData();
				label6LData.width = 63;
				label6LData.height = 21;
				label6LData.left =  new FormAttachment(0, 1000, 455);
				label6LData.top =  new FormAttachment(0, 1000, 112);
				label6.setLayoutData(label6LData);
				*/
			}
			{
				label7 = new AbstractLabel();
				label7.setText("Weekly workout program");
				label7.setTop(161);
				label7.setLeft(7);
				label7.setHeight(21);
				label7.setWidth(168);
				
				/*
				label7 = new Label(this, SWT.NONE);
				label7.setText("Weekly workout program");
				FormData label7LData = new FormData();
				label7LData.width = 168;
				label7LData.height = 21;
				label7LData.left =  new FormAttachment(0, 1000, 7);
				label7LData.top =  new FormAttachment(0, 1000, 161);
				label7.setLayoutData(label7LData);
				*/
			}
			{
				label8 = new AbstractLabel();
				label8.setText("WORKOUT GENERATOR");
				label8.setTop(14);
				label8.setLeft(7);
				label8.setHeight(21);
				label8.setWidth(217);
				
				/*
				label8 = new Label(this, SWT.NONE);
				label8.setText("WORKOUT GENERATOR ");
				FormData label8LData = new FormData();
				label8LData.width = 217;
				label8LData.height = 21;
				label8LData.left =  new FormAttachment(0, 1000, 7);
				label8LData.top =  new FormAttachment(0, 1000, 14);
				label8.setLayoutData(label8LData);
				label8.setFont(SWTResourceManager.getFont("Segoe UI", 14, 2, false, false));
				*/
			}
			{
				textInput1 = new AbstractTextInput();
				textInput1.setWidth(197);
				textInput1.setHeight(21);
				textInput1.setLeft(14);
				textInput1.setTop(133);
				textInput1.setText("0");
				textInput1.setLength(10);
				
				/*
				textInput1 = new Text(this, SWT.NONE);
				FormData text1LData = new FormData();
				text1LData.width = 197;
				text1LData.height = 21;
				text1LData.left =  new FormAttachment(0, 1000, 14);
				text1LData.top =  new FormAttachment(0, 1000, 133);
				textInput1.setLayoutData(text1LData);
				textInput1.setText("0");
				*/
			}
			{
				textInput2 = new AbstractTextInput();
				textInput2.setWidth(197);
				textInput2.setHeight(21);
				textInput2.setLeft(231);
				textInput2.setTop(133);
				textInput2.setText("0");
				textInput2.setLength(10);
				
				/*
				textInput2 = new Text(this, SWT.NONE);
				FormData text2LData = new FormData();
				text2LData.width = 197;
				text2LData.height = 21;
				text2LData.left =  new FormAttachment(0, 1000, 231);
				text2LData.top =  new FormAttachment(0, 1000, 133);
				textInput2.setLayoutData(text2LData);
				textInput2.setText("0");
				*/
			}
			{
				textInput3 = new AbstractTextInput();
				textInput3.setWidth(197);
				textInput3.setHeight(21);
				textInput3.setLeft(455);
				textInput3.setTop(133);
				textInput3.setText("0");
				textInput3.setLength(10);
				
				/*
				textInput3 = new Text(this, SWT.NONE);
				FormData text3LData = new FormData();
				text3LData.width = 197;
				text3LData.height = 21;
				text3LData.left =  new FormAttachment(0, 1000, 455);
				text3LData.top =  new FormAttachment(0, 1000, 133);
				textInput3.setLayoutData(text3LData);
				textInput3.setText("0");
				*/
			}
			{
				textInput4 = new AbstractTextInput();
				textInput4.setWidth(830);
				textInput4.setHeight(298);
				textInput4.setLeft(7);
				textInput4.setTop(182);
				textInput4.setText("");
				textInput4.setLength(10000);
				
				/*
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
				*/
			}
			
			{
				selectionInput1 = new AbstractSelectionInput();
				//selectionInput1.setLabel("Gender");
				selectionInput1.setOptions(new String[]{"Male","Female"});

				/*
				combo1 = new Combo(this, SWT.DROP_DOWN|SWT.READ_ONLY);
				FormData combo1LData = new FormData();
				combo1LData.width = 177;
				combo1LData.height = 23;
				combo1LData.left =  new FormAttachment(0, 1000, 14);
				combo1LData.top =  new FormAttachment(0, 1000, 68);
				combo1.setLayoutData(combo1LData);
				combo1.setItems(new java.lang.String[] {"Male","Female"});
				*/
			}
			{
				selectionInput2 = new AbstractSelectionInput();
				//selectionInput2.setLabel("Metabolism");
				selectionInput2.setOptions(new String[]{"Slow","Normal","Fast"});
				/*
				combo2 = new Combo(this, SWT.DROP_DOWN|SWT.READ_ONLY);
				FormData combo2LData = new FormData();
				combo2LData.width = 177;
				combo2LData.height = 23;
				combo2LData.left =  new FormAttachment(0, 1000, 231);
				combo2LData.top =  new FormAttachment(0, 1000, 68);
				combo2.setLayoutData(combo2LData);
				combo2.setItems(new java.lang.String[] {"Slow","Normal","Fast"});
				*/
			}
			{
				
				selectionInput3 = new AbstractSelectionInput();
				//selectionInput3.setLabel("Metabolism");
				selectionInput3.setOptions(new String[]{"Beginner"," Intermediate"," Advanced"});
				/*
				combo3 = new Combo(this, SWT.DROP_DOWN|SWT.READ_ONLY);
				FormData combo3LData = new FormData();
				combo3LData.width = 177;
				combo3LData.height = 23;
				combo3LData.left =  new FormAttachment(0, 1000, 455);
				combo3LData.top =  new FormAttachment(0, 1000, 68);
				combo3.setLayoutData(combo3LData);
				combo3.setItems(new java.lang.String[] {"Beginner"," Intermediate"," Advanced"});
				combo3.setLayoutDeferred(true);
				*/
			}
			{
				group1 = new AbstractGroup();
				group1.setText("Generation");
				group1.setWidth(176);
				group1.setHeight(52);
				group1.setRight(-14);
				
				/*
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
				*/
				{
					AbstractAction button1 = new AbstractAction();
					button1.setWidth(77);
					button1.setHeight(28);
					button1.setLeft(92);
					button1.setTop(13);
					button1.setText("Clear");
					
					/*
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
					*/
				}
				{
					AbstractAction button2 = new AbstractAction();
					button2.setWidth(77);
					button2.setHeight(28);
					button2.setLeft(7);
					button2.setTop(13);
					button2.setText("Clear");
					
					/*
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
					*/
					
				}
			}
			//TODO: What to do with this guty?
			//this.layout();
			/*
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
	}
	
	private void button1WidgetSelected(SelectionEvent evt) {	
		//initailize
		String workout = "";
		textInput4.setText(workout);
		if (textInput1.getText().trim().equals("") || Integer.parseInt(textInput1.getText().trim()) == 0) {
            //TODO: Define message class
			/*
			MessageBox messageBox = new MessageBox(shell);
            messageBox.setText("Incomplete input");
            messageBox.setMessage("Please enter your age!!!");
            messageBox.open();
            */
            return;
        }
        if (textInput2.getText().trim().equals("") || Integer.parseInt(textInput2.getText().trim()) == 0) {
        	//TODO: Define message class
        	/*
        	MessageBox messageBox = new MessageBox(shell);
            messageBox.setText("Incomplete input");
            messageBox.setMessage("Please enter your height!!!");
            messageBox.open();
            return;
            */
        }
        if (textInput3.getText().trim().equals("") || Integer.parseInt(textInput3.getText().trim()) == 0) {
        	//TODO: Define message class
        	/*
        	MessageBox messageBox = new MessageBox(shell);
        	messageBox.setText("Incomplete input");
            messageBox.setMessage("Please enter your weight!!!");
            messageBox.open();
            */
            return;
        }
        //Input data
        String gender = selectionInput1.getText().trim();
        String metabolism = selectionInput2.getText().trim();
        String experience = selectionInput3.getText().trim();
        int age = Integer.parseInt(textInput1.getText().trim());
        int height = Integer.parseInt(textInput2.getText().trim());
        int weight = Integer.parseInt(textInput3.getText().trim()); 
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
	        if (metabolism.equals("Advanced")) {
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
        textInput4.setText(workout);
	}
	
	private void button2WidgetSelected(SelectionEvent evt) {
		textInput4.setText("");
	}
}
