/*
 * $RCSfile: Blogcaster.java,v $    $Revision: 1.6 $  $Date: 2005/06/08 14:53:32 $ - $Author: mikemking $
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

package com.webcodefocus.blogcaster;

import com.webcodefocus.blogcaster.model.Settings;
import com.webcodefocus.blogcaster.view.MainWindow;


/**
 * Main class for the Blogcaster applciation. Creates and displays the
 * main window and handles its events.
 * @author Mike King
 * @author Kevin McAllister
 */
public class Blogcaster
{
  public final static String VERSION = "0.5";
  public final static String NAME = "Blogcaster";
  private static boolean closeAfterPost = true;

  public static void main(String[] args)
  {   
    // load properties from settings file
    boolean setup = Settings.loadProperties();
    MainWindow window = new MainWindow(setup);
  }
  /**
   * @return Returns the closeAfterPost.
   */
  public static boolean isCloseAfterPost() {
    return closeAfterPost;
  }
  /**
   * @param closeAfterPost The closeAfterPost to set.
   */
  public static void setCloseAfterPost(boolean closeAfterPost) {
    Blogcaster.closeAfterPost = closeAfterPost;
  }
}
