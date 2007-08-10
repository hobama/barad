/*
 * $RCSfile: SettingsWindow.java,v $    $Revision: 1.3 $  $Date: 2005/06/01 19:51:54 $ - $Author: mikemking $
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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.webcodefocus.blogcaster.Blogcaster;
import com.webcodefocus.blogcaster.control.BlogListEventListener;
import com.webcodefocus.blogcaster.control.CloseWindowEventListener;
import com.webcodefocus.blogcaster.model.Blog;
import com.webcodefocus.blogcaster.model.Settings;


/**
 * Class for displaying the Settings window and handling its events.
 * @author Mike King
 */
public class SettingsWindow
{
  private final static int LABEL_WIDTH = 65;
  private static Text url = null;
  private static Text username = null;
  private static Text password = null;
  private static Combo blogId = null;
  private static Shell shell = null;
  private static Button blogListButton = null;
  public static HashMap blogIdMap = new HashMap();
  
  public SettingsWindow (Shell parent) {
    SettingsWindow.shell = new Shell(parent.getDisplay(), SWT.CLOSE |SWT.BORDER | SWT.RESIZE);
    
    shell.setText("Settings");
    
    GridLayout layout = new GridLayout();
    layout.numColumns = 3;
    shell.setLayout(layout);
    
    // URL Label
    Label urlLabel = new Label(shell, SWT.NONE);
    urlLabel.setText("URL:");
    
    // URL Text Field
    url = new Text(shell, SWT.BORDER | SWT.SINGLE);
    url.setText(Blog.getUrl());
    
    // Username Label
    Label usernameLabel = new Label(shell, SWT.NONE);
    usernameLabel.setText("Username:");
    
    // Username Text Field
    username = new Text(shell, SWT.BORDER | SWT.SINGLE);
    username.setText(Blog.getUser());
    
    // Password Label
    Label passwordLabel = new Label(shell, SWT.NONE);
    passwordLabel.setText("Password:");
    
    // Password Text Field
    password = new Text(shell, SWT.BORDER | SWT.SINGLE);
    password.setText(Blog.getPass());
    
    // Blog ID Label
    Label blogIdLabel = new Label(shell, SWT.NONE);
    blogIdLabel.setText("Blog ID:");

    // Blog ID Dropdown
    blogId = new Combo(shell, SWT.READ_ONLY);
    blogId.add("-- Select --");
    blogId.select(0);
 
    // Get Blog List Button
    blogListButton = new Button(shell, SWT.PUSH);
    blogListButton.setText("Get Blogs");
    blogListButton.addSelectionListener(new BlogListEventListener(blogId));
    
    
    // blank label for the close after post checkbox (label is part of Button)
    Label closeAfterLabel = new Label(shell, SWT.NONE);
    closeAfterLabel.setText(" ");
    
    // close after successful post checkbox
    Button closeAfterCheckbox = new Button(shell, SWT.CHECK);
    closeAfterCheckbox.setText("Close after successful post");
    closeAfterCheckbox.setToolTipText("Should the application close after a successful post?");
    closeAfterCheckbox.setSelection(Blogcaster.isCloseAfterPost());

    
    // container to hold the Cancel and Apply buttons
    Composite buttonContainer = new Composite(shell, SWT.NONE);
    GridLayout buttonLayout = new GridLayout();
    buttonLayout.numColumns = 2;
    buttonContainer.setLayout(buttonLayout);
    
    // Cancel Button
    Button cancel = new Button(shell, SWT.PUSH);
    cancel.setText("Cancel");
    cancel.addSelectionListener(new CloseWindowEventListener(shell));
    cancel.setParent(buttonContainer);
    cancel.setLayoutData(new GridData());
    
    // Apply Button
    Button apply = new Button(shell, SWT.PUSH);
    apply.setText("Apply");
    // TODO: refactor this code out into the control layer
    apply.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent event) {
        if (SettingsWindow.saveSettings()) {    // save settings...
          shell.dispose();                      // close window
        }
      }
    });
    apply.setParent(buttonContainer);
    apply.setLayoutData(new GridData());
    
    // Position everything...
    
    // Row 1 - URL (label, field)
    GridData urlLabelCell = new GridData();
    urlLabelCell.widthHint = LABEL_WIDTH;
    urlLabel.setLayoutData(urlLabelCell);
    
    GridData urlFieldCell = new GridData(GridData.FILL_HORIZONTAL);
    urlFieldCell.horizontalSpan = 2;
    url.setLayoutData(urlFieldCell);
    
    // Row 2 - Username (label, field)
    GridData usernameLabelCell = new GridData();
    usernameLabelCell.widthHint = LABEL_WIDTH;
    usernameLabel.setLayoutData(usernameLabelCell);
    
    GridData usernameFieldCell = new GridData(GridData.FILL_HORIZONTAL);
    usernameFieldCell.horizontalSpan = 2;
    username.setLayoutData(usernameFieldCell);
    
    // Row 3 - password (label, field)
    GridData passwordLabelCell = new GridData();
    passwordLabelCell.widthHint = LABEL_WIDTH;
    passwordLabel.setLayoutData(passwordLabelCell);
    
    GridData passwordFieldCell = new GridData(GridData.FILL_HORIZONTAL);
    passwordFieldCell.horizontalSpan = 2;
    password.setLayoutData(passwordFieldCell);

    // Row 4 - Blog ID (label, field, button)
    GridData blogLabelCell = new GridData();
    blogLabelCell.widthHint = LABEL_WIDTH;
    blogIdLabel.setLayoutData(blogLabelCell);
    
    GridData blogFieldCell = new GridData(GridData.FILL_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL);
    blogFieldCell.grabExcessHorizontalSpace = true;
    blogId.setLayoutData(blogFieldCell);
    
    GridData blogButtonCell = new GridData(GridData.HORIZONTAL_ALIGN_END | GridData.HORIZONTAL_ALIGN_FILL);
    blogListButton.setLayoutData(blogButtonCell);
    
    // Row 5 - Close after posting
    GridData closeAfterLabelCell = new GridData();
    closeAfterLabelCell.widthHint = LABEL_WIDTH;
    closeAfterLabel.setLayoutData(closeAfterLabelCell);
    
    GridData closeAfterCheckboxCell = new GridData();
    closeAfterCheckbox.setLayoutData(closeAfterCheckboxCell);
    
    // Row 6 - Buttons (Cancel and Apply)
    GridData buttonCell = new GridData(GridData.HORIZONTAL_ALIGN_END | GridData.HORIZONTAL_ALIGN_FILL);
    buttonCell.horizontalSpan = 3;
    buttonContainer.setLayoutData(buttonCell);
    

    shell.setSize(375, 200);
    shell.open();
    
    Display display = parent.getDisplay();
    
    while (! shell.isDisposed()) {
      if (! display.readAndDispatch()) {
        display.sleep(); 
      }
     }
  }
  
  /**
   * Accepts user input from the Text fields and saves them to the
   * static Blog object and the settings.properties file on disk.
   * Only the URL field is validated at this time.
   * @author Mike King
   * @return boolean True for success, false for invalid input.
   */
  public static boolean saveSettings() {
    // TODO: refactor this out into the model layer (Settings class)
    // validate the URL...
    String urlValue = (String)url.getText();
    try {
      URL urlObject = new URL(urlValue);
    } catch (MalformedURLException mue) {
      // display an error in a modal dialog
      MessageBox msgBox = new MessageBox(shell, SWT.APPLICATION_MODAL | SWT.OK);
      msgBox.setText("Error");
      msgBox.setMessage("Invalid URL specified.");   
      msgBox.open();
      return false;
    }
    
    // save the settings to the static Blog object in memory
    Blog.setUrl(urlValue);
    Blog.setUser(username.getText());
    Blog.setPass(password.getText());
    
    Integer blogDropdownSelectedIndex = new Integer(blogId.getSelectionIndex());
    Integer blogIdValue = (Integer)SettingsWindow.blogIdMap.get(blogDropdownSelectedIndex);
    if (blogIdValue == null) {
      blogIdValue = new Integer(0);  // make sure we don't crash
    }
    Blog.setBlogId(blogIdValue.toString());
    
    // save the settings to disk
    Settings.saveProperties();
    return true;
  }

}

