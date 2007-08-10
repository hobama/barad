/*
 * $RCSfile: ExceptionWrapper.java,v $    $Revision: 1.2 $  $Date: 2005/05/22 22:52:41 $ - $Author: mcallist $
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

package com.webcodefocus.blogcaster.common;

/**
 * ExceptionWrapper a common exception class to promote unified handling
 * of errors and exceptions.
 * @author Kevin McAllister
 */
public class ExceptionWrapper extends Exception {
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public ExceptionWrapper() {
    super();
  }

  /**
   * Takes an error message.
   * @param arg0
   */
  public ExceptionWrapper(String arg0) {
    super(arg0);
  }

  /**
   * Takes an error message ana Throwable.
   * @param arg0
   * @param arg1
   */
  public ExceptionWrapper(String arg0, Throwable arg1) {
    super(arg0, arg1);
  }

  /**
   * Just encapsulates a Throwable.
   * @param arg0
   */
  public ExceptionWrapper(Throwable arg0) {
    super(arg0);
  }
}
