/*
 * $RCSfile: SmartObject.java,v $    $Revision: 1.1.1.1 $  $Date: 2005/05/21 23:50:57 $ - $Author: mikemking $
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * SmartObject is simply an object with some logging properties
 * and methods, Most objects that would simply extend java.lang.Object
 * should extend this.
 * @author Kevin McAllister
 */
public class SmartObject {
  // TODO make sure we are doing this right.
  // I don't want 8 million instances of Log filling up the VM.
  //protected Log log;
  //public SmartObject() {
  //  log = LogFactory.getLog(this.getClass());
  //}
}
