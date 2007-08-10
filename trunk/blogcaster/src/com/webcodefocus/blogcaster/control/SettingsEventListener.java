/*
 * $RCSfile: SettingsEventListener.java,v $    $Revision: 1.1 $  $Date: 2005/05/23 19:04:38 $ - $Author: mcallist $
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
import com.webcodefocus.blogcaster.view.SettingsWindow;

/**
 * Used to create and show a Setting window.
 * @author Kevin McAllister
 */
public class SettingsEventListener extends SmartObject implements SelectionListener {
  private Shell shell;
  
  public SettingsEventListener(Shell shell) {
    super();
    this.shell = shell;
  }
  /**
   * Open a new SettingsWindow
   */
  public void widgetSelected(SelectionEvent event) {
    new SettingsWindow(this.shell);
  }
  // I wonder what this method is for.
  public void widgetDefaultSelected(SelectionEvent event) {}
}