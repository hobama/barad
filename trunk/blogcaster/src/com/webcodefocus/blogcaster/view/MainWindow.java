/*
 * $RCSfile: MainWindow.java,v $    $Revision: 1.7 $  $Date: 2005/06/07 21:14:04 $ - $Author: mikemking $
 * 
 * Copyright (c) 2005 - Michael King, Kevin McAllister
 * 
 * This library is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of the
 * License, or (at your option) any later version. See the file
 * LICENSE.txt included with this library for more information.
 * 
 */

package com.webcodefocus.blogcaster.view;

import java.util.Properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Widget;

import com.webcodefocus.blogcaster.Blogcaster;
import com.webcodefocus.blogcaster.control.AboutEventListener;
import com.webcodefocus.blogcaster.control.ExitEventListener;
import com.webcodefocus.blogcaster.control.PostEventListener;
import com.webcodefocus.blogcaster.control.SettingsEventListener;
import com.webcodefocus.blogcaster.control.markup.HyperlinkEventListener;
import com.webcodefocus.blogcaster.control.markup.SimpleTagEventListener;
import com.webcodefocus.blogcaster.model.markup.SimpleTag;

public class MainWindow
{
  final Shell shell;
  
  /**
   * Default Constructor, passes setup as true to the constructor
   * with the real meat.  Which will not open the settings window by
   * default
   */
  public MainWindow() {
	  this(true);   // tell the constructor not to open the Settings dialog
  }
  
  /**
   * Constructor
   * @param setup If true, then open the Settings dialog when the main
   * window is displayed; else, don't show Settings dialog.
   */
  public MainWindow(boolean setup) {
    // TODO Create a generic NewWindowEventListener that takes a window type and the shell and opens one.
    Display display = new Display();
    shell = new Shell(display, SWT.CLOSE | SWT.RESIZE);
    shell.setText(Blogcaster.NAME);
    
    // we're laying widgets out in a grid with 1 column
    GridLayout layout = new GridLayout();
    layout.numColumns = 1;
    shell.setLayout(layout);
    
    // create Title text box and position it in the layout
    Text titleTextArea = new Text(shell, SWT.MULTI | SWT.BORDER | SWT.WRAP);
    GridData titleCell = new GridData(GridData.FILL_HORIZONTAL);
    titleTextArea.setLayoutData(titleCell);
    
    // create Content text box
    // TODO: figure out how to make this display below the HTML widget bar!
    Text contentTextArea = new Text(shell, SWT.MULTI | SWT.BORDER | SWT.WRAP);
    
    // create the HTML widget bar and position it in the layout
    ToolBar htmlWidgetBar = this.createWidgetBar(contentTextArea); 
    GridData widgetCell = new GridData();
    htmlWidgetBar.setLayoutData(widgetCell);
    
    // position Content text box in the layout
    GridData contentCell = new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL);
    contentTextArea.setLayoutData(contentCell);
    
    // create Post button and position it in the layout
    Button postButton = new Button(shell, SWT.PUSH);
    postButton.setText("Post");
    postButton.setToolTipText("Posts an entry to your blog");
    postButton.addSelectionListener(new PostEventListener(shell, titleTextArea, contentTextArea));
    
    GridData buttonCell = new GridData(GridData.HORIZONTAL_ALIGN_END);
    postButton.setLayoutData(buttonCell);
    
    // generates the menu bar and binds it to the Shell
    this.createMenu(titleTextArea, contentTextArea);
        
    // display the window...
    shell.setSize(450, 390);      
    shell.open();
    
    // Once we have displayed the main window, if
    // we weren't setup already we should show the
    // settings window.
    if (!setup) {
      new SettingsWindow(shell);
    }
 
    // event dispatching loop
    while (! shell.isDisposed()) {
      if (! display.readAndDispatch()) {
        display.sleep(); 
     }
    }
    display.dispose();
  }
  
  /**
   * @return the Shell of the MainWindow
   */
  public Shell getShell() {
    return this.shell;
  }
  
  /**
   * Creates the Menu bar (File menu, etc) and adds all the menu items.
   * @param titleField The title field of the main screen (passed to event listeners)
   * @param contentField The content field of the main screen (passed to event listeners)
   * @return void
   */
  private void createMenu(Text titleField, Text contentField) {
    // start creating menu
    Menu menuBar = new Menu(this.shell, SWT.BAR);
 
    MenuItem file = new MenuItem(menuBar, SWT.CASCADE);
    file.setText("File");
 
    Menu fileMenu = new Menu(this.shell, SWT.DROP_DOWN);
    file.setMenu(fileMenu);
 
    MenuItem postItem = new MenuItem(fileMenu, SWT.PUSH);
    postItem.setText("Post to Blog...");
    postItem.addSelectionListener(new PostEventListener(this.shell, titleField, contentField));
 
    MenuItem settingsItem = new MenuItem(fileMenu, SWT.PUSH);
    settingsItem.setText("Settings");
    settingsItem.addSelectionListener(new SettingsEventListener(this.shell)); 

    MenuItem separator = new MenuItem(fileMenu, SWT.SEPARATOR);
 
    MenuItem exitItem = new MenuItem(fileMenu, SWT.PUSH);
    exitItem.setText("Exit");
    exitItem.addSelectionListener(new ExitEventListener());
 
    MenuItem help = new MenuItem(menuBar, SWT.CASCADE);
    help.setText("Help");
 
    Menu helpMenu = new Menu(this.shell, SWT.DROP_DOWN);
    help.setMenu(helpMenu);
 
    MenuItem aboutItem = new MenuItem(helpMenu, SWT.PUSH);
    aboutItem.setText("About");
    aboutItem.addSelectionListener(new AboutEventListener(this.shell));
 
    this.shell.setMenuBar(menuBar);
    // finished creating menu
  }
  
  
	public Widget widget;
	public SelectionListener listener;
	
	public void triggerSelectionEvent(Properties properties) {
		listener.widgetSelected(SelectionEventGenerator.generateSelectionEvent(widget, properties));
	}
  
  
  /**
   * Creates the HTML widget bar and its buttons.
   * @param contentField Handle to the MainWindow content field (passed into event listeners)
   * @return ToolBar The generated toolbar, which can then be positioned on the screen.
   * @author Mike King
   */
  private ToolBar createWidgetBar(Text contentField) {
    ToolBar toolbar = new ToolBar(this.shell, SWT.HORIZONTAL);
    
    // Link button - opens the hyperlink dialog
    ToolItem linkButton = new ToolItem(toolbar, SWT.PUSH);
    linkButton.setText("Link");
    linkButton.setToolTipText("Insert a hyperlink"); 
    linkButton.addSelectionListener(new HyperlinkEventListener(this.shell, contentField));
    
    // Strong button - wraps text in bold
    ToolItem strongButton = new ToolItem(toolbar, SWT.CHECK);
    strongButton.setText("Bold");
    strongButton.setToolTipText("Make text bold");
    strongButton.addSelectionListener(new SimpleTagEventListener(strongButton, SimpleTag.STRONG_TAG, contentField));
    
    // Emphasis button - wraps text in bold
    ToolItem emButton = new ToolItem(toolbar, SWT.CHECK);
    emButton.setText("Italic");
    emButton.setToolTipText("Make text italic");
    emButton.addSelectionListener(new SimpleTagEventListener(emButton, SimpleTag.EMPHASIS_TAG, contentField));
    
    // Underline button - wraps text in bold
    // TODO: The underline tag is deprecated. Needs to be a span with style applied
    ToolItem underlineButton = new ToolItem(toolbar, SWT.CHECK);
    underlineButton.setText("Underline");
    underlineButton.setToolTipText("Underline text");
    underlineButton.addSelectionListener(new SimpleTagEventListener(underlineButton, SimpleTag.UNDERLINE_TAG, contentField));
    
    //SimpleTagEventListener listner = new SimpleTagEventListener(underlineButton, SimpleTag.UNDERLINE_TAG, contentField);
    //underlineButton.addSelectionListener(listner);
   
    return toolbar;
  }
}
