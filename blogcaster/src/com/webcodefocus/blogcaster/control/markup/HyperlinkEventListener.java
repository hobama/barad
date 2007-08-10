/*
 * $RCSfile: HyperlinkEventListener.java,v $    $Revision: 1.1 $  $Date: 2005/06/04 21:03:10 $ - $Author: mikemking $
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
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.webcodefocus.blogcaster.common.SmartObject;
import com.webcodefocus.blogcaster.view.HyperlinkDialog;

/**
 * Handles the event when the user clicks the "Link" button on the main
 * window screen. Opens the HyperlinkDialog window.
 * @author Mike King
 */
public class HyperlinkEventListener extends SmartObject implements SelectionListener
{
  private Shell parent;
  private Text contentField;
  
  public HyperlinkEventListener(Shell parent, Text contentField) {
    super();
    this.parent = parent;
    this.contentField = contentField;   
  }

  /**
   * Opens the HyperlinkDialog window.
   */
  public void widgetSelected(SelectionEvent event) {
    // get the selection from the content field and save it (not the selected text, the selection Point)
    Point selection = contentField.getSelection();
    // open hyperlink dialog window (content field selection loses focus)
    new HyperlinkDialog(this.parent, this.contentField);
    // re-select the text in the contet field
    contentField.setSelection(selection);
    // delete the selected text
    contentField.cut();
  }

  /**
   * Required callback method.
   */
  public void widgetDefaultSelected(SelectionEvent event) {}
}
