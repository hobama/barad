/*
 * $RCSfile: SimpleTagEventListener.java,v $    $Revision: 1.2 $  $Date: 2005/06/07 21:14:06 $ - $Author: mikemking $
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
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolItem;

import com.webcodefocus.blogcaster.model.markup.SimpleTag;

/**
 * 
 * Handles the event when the user clicks one of the HTML widget buttons
 * for a "Simple" tag. ie: Bold, Italic, Underline. A simple tag is simply
 * a tag that just wraps the highlighted text in an open close tag and
 * requires no attributes.
 * @author Mike King
 */
public class SimpleTagEventListener implements SelectionListener
{
  private ToolItem button;
  private String tagType;
  private Text contentField;
  
  public SimpleTagEventListener(ToolItem button, String tagType, Text contentField) {
    this.button = button;
    this.tagType = tagType;
    this.contentField = contentField;
  }
  
  public void widgetSelected(SelectionEvent event) {   
    // get the selection from the content field and save it (not the selected text, the selection Point)
    Point selection = contentField.getSelection();
    
    // generate the HTML tag
    SimpleTag tag = new SimpleTag(this.tagType, this.contentField.getSelectionText()); 
    
    // figure out if the user has highlighted anything
    int charsSelected = this.contentField.getSelectionCount();
    
    if (charsSelected > 0) { 
      // The user has highlighted something in the content box,
      // just wrap it with the tag...
      
      // re-select the text in the contet field
      contentField.setSelection(selection);
      // delete the selected text (it gets re-inserted next...)
      contentField.cut();

      // insert the tag into the content Field
      this.contentField.insert(tag.getTag());
    } else {
      // There's nothing highlighted in the content box,
      // just insert an opening or closing tag. Make sure
      // to check if the tag was already opened.
      if (this.button.getSelection()) {   // NOTE: this is evaluated AFTER the button was clicked...
        // insert the OPEN tag into the content Field
        this.contentField.insert(tag.getOpenTag());
      } else {
        // insert the CLOSE tag into the content Field
        this.contentField.insert(tag.getCloseTag());
      }
    }
  }

  /**
   * Required callback method.
   */
  public void widgetDefaultSelected(SelectionEvent event) {}
}
