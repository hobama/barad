/*
 * $RCSfile: PostProgress.java,v $    $Revision: 1.2 $  $Date: 2005/05/22 19:28:23 $ - $Author: mikemking $
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

package com.webcodefocus.blogcaster.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

import com.webcodefocus.blogcaster.common.SmartObject;

/**
 * Displays a very small window with a progress bar. Currently, it's
 * just smoke and mirrors, for a whiz-bang effect.
 * @author Mike King
 */
public class PostProgress extends SmartObject
{
  // TODO: Make this track the progress of a post operation, updating
  // the status as the posting progresses.
  public PostProgress (Shell parent) {
    final Shell shell = new Shell(parent, SWT.CLOSE | SWT.BORDER);
    shell.setText("Posting to Blog...");
    shell.setLayout(new RowLayout());
    
    Label label = new Label(shell, SWT.NONE);
    label.setText("Posting to blog...");
    
    ProgressBar progBar = new ProgressBar(shell, SWT.HORIZONTAL);
    progBar.setMinimum(0);
    progBar.setMaximum(100);
    progBar.setBounds(5, 5, 175, 20);
    
    shell.setSize(175, 75);
    shell.open();
    
    int progress = 0;
    for (int i = 0; i < 10; i++) {
      try {
        Thread.sleep(500);
      } catch (Exception e) {
        // whatever, this is just smoke and mirrors anyway
      }

      progress += 10;
      progBar.setSelection(progress);
    }
    
    shell.dispose();
    
    Display display = parent.getDisplay();
    
    while (! shell.isDisposed()) {
      if (! display.readAndDispatch()) {
         display.sleep(); 
      }
    }
  }
}