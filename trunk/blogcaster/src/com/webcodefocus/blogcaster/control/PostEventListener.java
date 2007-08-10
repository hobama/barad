/*
 * $RCSfile: PostEventListener.java,v $    $Revision: 1.2 $  $Date: 2005/06/01 19:51:53 $ - $Author: mikemking $
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

package com.webcodefocus.blogcaster.control;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.webcodefocus.blogcaster.Blogcaster;
import com.webcodefocus.blogcaster.common.SmartObject;
import com.webcodefocus.blogcaster.model.Entry;
import com.webcodefocus.blogcaster.view.PostProgress;

/**
 * Used for the post entry to blog action.
 * @author Kevin McAllister
 */
public class PostEventListener extends SmartObject implements SelectionListener {
  private Shell shell;
  private Text title;
  private Text body;
  
  public PostEventListener(Shell shell, Text title, Text body) {
    super();
    this.shell = shell;
    this.title = title;
    this.body = body;
  }
  /**
   * when the button is clicked or the menu is selected
   * There you have it.
   */
  public void widgetSelected(SelectionEvent event) {
    Entry entry = new Entry(title.getText(), body.getText());
    PostProgress progressBar = new PostProgress(shell);
    entry.post();
    title.setText("");
    body.setText("");
    if (Blogcaster.isCloseAfterPost()) {
      System.exit(0);
    }
  }
  // I wonder what this method is for.
  public void widgetDefaultSelected(SelectionEvent event) {}
}