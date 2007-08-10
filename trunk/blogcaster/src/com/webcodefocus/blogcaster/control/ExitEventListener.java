/*
 * $RCSfile: ExitEventListener.java,v $    $Revision: 1.2 $  $Date: 2005/06/01 19:51:53 $ - $Author: mikemking $
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

import com.webcodefocus.blogcaster.common.SmartObject;

/**
 * Used to exit the application.
 * @author Kevin McAllister
 */
public class ExitEventListener extends SmartObject implements SelectionListener {
  
  public ExitEventListener() {
    super();
  }
  
  /**
   * Calls System.exit(0) to kill the application. In the future, we
   * will be able to put code here that saves state information, etc.
   */
  public void widgetSelected(SelectionEvent event) {
    System.exit(0);
  }

  public void widgetDefaultSelected(SelectionEvent event) {}
}
