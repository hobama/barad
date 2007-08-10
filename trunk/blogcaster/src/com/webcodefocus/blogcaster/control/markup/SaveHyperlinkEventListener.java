/*
 * $RCSfile: SaveHyperlinkEventListener.java,v $    $Revision: 1.1 $  $Date: 2005/06/01 19:51:54 $ - $Author: mikemking $
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
 
package com.webcodefocus.blogcaster.control.markup;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.webcodefocus.blogcaster.common.SmartObject;
import com.webcodefocus.blogcaster.model.markup.Hyperlink;

/**
 * 
 * Executes when the "OK" button is clicked in the HyperlinkDialog window.
 * Generates an &lt;a href..&gt; tag based on the content of the fields
 * in the HyperlinkDialog window, and places that tag into the content
 * field of the MainWindow 
 * @author mking
 */
public class SaveHyperlinkEventListener extends SmartObject implements SelectionListener
{
  private Text contentField;
  private Shell shell;
  private Text url;
  private Text displayText;
  private Button newWindow;
  
  public SaveHyperlinkEventListener(Shell shell, Text contentField, Text url, Text displayText, Button newWindow) {
    this.shell = shell;
    this.contentField = contentField;
    this.url = url;
    this.displayText = displayText;
    this.newWindow = newWindow;
  }
  
  /**
   * Handles the event when the user clicks the "OK" button on the Hyperlink
   * Dialog screen. Creates a new Hyperlink object, and writes the output
   * back into the MainWindow contentTextArea, replacing the highlighted
   * text; then closes the HyperlinkDialog.
   */
  public void widgetSelected(SelectionEvent event) {
    Hyperlink link = new Hyperlink(this.url.getText(), this.displayText.getText(), this.newWindow.getSelection());
    this.contentField.insert(link.getHyperlinkTag());
    this.shell.dispose();
  }

  /**
   * Required callback method.
   */
  public void widgetDefaultSelected(SelectionEvent event) {}
  
}
