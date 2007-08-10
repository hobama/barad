/*
 * $RCSfile: CloseWindowEventListener.java,v $    $Revision: 1.1 $  $Date: 2005/06/01 19:51:53 $ - $Author: mikemking $
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

import com.webcodefocus.blogcaster.common.SmartObject;

/**
 * Generic EvenListener for closing a window or dialog.
 * @author Mike King
 */
public class CloseWindowEventListener extends SmartObject implements SelectionListener {
  final Shell shell;
  
  /**
   * Contructor
   * @param shell The Shell which we want to dispose of.
   */
  public CloseWindowEventListener(Shell shell) {
    super();
    this.shell = shell;
  }
  
  /**
   * Calls dispose() on the shell we passed into the constructor,
   * effectively closing the window.
   */
  public void widgetSelected(SelectionEvent event) {
    this.shell.dispose();
  }

  public void widgetDefaultSelected(SelectionEvent event) {}
}
