/*
 * $RCSfile: HyperlinkDialog.java,v $    $Revision: 1.2 $  $Date: 2005/06/04 21:07:41 $ - $Author: mikemking $
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

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.webcodefocus.blogcaster.common.SmartObject;
import com.webcodefocus.blogcaster.control.CloseWindowEventListener;
import com.webcodefocus.blogcaster.control.markup.SaveHyperlinkEventListener;

/**
 * Displays the hyperlink dialog window
 * @author Mike King
 */
public class HyperlinkDialog extends SmartObject
{
  final Shell shell;
  private final int LABEL_WIDTH = 85;
  private Text contentField;
  private Text urlTextBox;
  private Text displayTextBox;
  private Button newWindowCheckbox;
 
  /**
   * Creates the dialog window, places the widgets on it, and displays it.
   */
  public HyperlinkDialog(Shell parent, Text contentField) {
    shell = new Shell(parent, SWT.CLOSE | SWT.RESIZE);
    shell.setText("Insert hyperlink");
    
    this.contentField = contentField;
    
    // we're laying widgets out in a grid with 1 column
    GridLayout layout = new GridLayout();
    layout.numColumns = 2;
    shell.setLayout(layout);
    
    // Label for the URL field
    Label urlLabel = new Label(shell, SWT.NONE);
    urlLabel.setText("URL:");
    GridData urlLabelCell = new GridData();
    urlLabelCell.widthHint = LABEL_WIDTH;
    urlLabel.setLayoutData(urlLabelCell);
    
    // URL text box
    this.urlTextBox = new Text(shell, SWT.SINGLE | SWT.BORDER);
    this.urlTextBox.setText("http://");
    GridData urlCell = new GridData(GridData.FILL_HORIZONTAL);
    this.urlTextBox.setLayoutData(urlCell);
    
    // Label for the diplay text field
    Label displayLabel = new Label(shell, SWT.NONE);
    displayLabel.setText("Display text:");
    GridData displayLabelCell = new GridData();
    displayLabelCell.widthHint = LABEL_WIDTH;
    displayLabel.setLayoutData(displayLabelCell);
    
    // Display text textbox
    this.displayTextBox = new Text(shell, SWT.SINGLE | SWT.BORDER);
    // populate with selected text of MainWindow.contentTextArea
    this.displayTextBox.setText(this.contentField.getSelectionText());
    GridData displayCell = new GridData(GridData.FILL_HORIZONTAL);
    this.displayTextBox.setLayoutData(displayCell);
    
    // Label for new window checkbox (blank)
    Label blankLabel = new Label(shell, SWT.NONE);
    blankLabel.setText(" ");
    GridData blankLabelCell = new GridData();
    blankLabel.setLayoutData(blankLabelCell);
    
    // create a checkbox with label
    this.newWindowCheckbox = new Button(this.shell, SWT.CHECK);
    this.newWindowCheckbox.setSelection(false);
    this.newWindowCheckbox.setText("Open link in new window");
    GridData checkboxCell = new GridData();
    this.newWindowCheckbox.setLayoutData(checkboxCell);
    
    // button row - spans 2 columms
    Composite buttonContainer = this.createButtons();
    GridData buttonCell = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
    buttonCell.horizontalSpan = 2;
    buttonContainer.setLayoutData(buttonCell);  
    
    shell.setSize(450, 175);
    shell.open();
    
    Display display = parent.getDisplay();
    
    while (! shell.isDisposed()) {
      if (! display.readAndDispatch()) {
       display.sleep(); 
      }
     }
    
  }
  
  /**
   * Creates a Composite container that holds two buttons - OK and Cancel.
   * The buttons are laid out in a 2x1 GridLayout, and the entire composite
   * is returned. Keeps the code in the constructor clean.
   * @return Composite containing two buttons
   */
  private Composite createButtons() {
    // container to hold the Cancel and Apply buttons
    Composite buttonContainer = new Composite(this.shell, SWT.NONE);
    GridLayout buttonLayout = new GridLayout();
    buttonLayout.numColumns = 2;
    buttonContainer.setLayout(buttonLayout);
    
    // OK button
    Button okButton = new Button(this.shell, SWT.PUSH);
    okButton.setText("  OK  ");
    okButton.addSelectionListener(new SaveHyperlinkEventListener(this.shell, this.contentField, this.urlTextBox, this.displayTextBox, this.newWindowCheckbox));
    GridData okButtonCell = new GridData();
    okButton.setParent(buttonContainer);
    okButton.setLayoutData(okButtonCell);
    
    // cancel button
    Button cancelButton = new Button(this.shell, SWT.PUSH);
    cancelButton.setText("Cancel");
    cancelButton.addSelectionListener(new CloseWindowEventListener(this.shell));
    GridData cancelButtonCell = new GridData();
    cancelButton.setParent(buttonContainer);
    cancelButton.setLayoutData(cancelButtonCell);
    
    return buttonContainer;
  }

  
}