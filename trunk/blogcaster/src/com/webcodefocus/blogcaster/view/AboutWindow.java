/*
 * $RCSfile: AboutWindow.java,v $    $Revision: 1.3 $  $Date: 2005/06/01 19:51:54 $ - $Author: mikemking $
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
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.webcodefocus.blogcaster.Blogcaster;
import com.webcodefocus.blogcaster.control.CloseWindowEventListener;

public class AboutWindow
{
  public AboutWindow(Shell parent) {
    final Shell shell = new Shell(parent, SWT.CLOSE | SWT.RESIZE);
    shell.setSize(250, 150);
    shell.setText("About");

    
    Label label = new Label(shell, SWT.CENTER);
    label.setText(Blogcaster.NAME+"\nVersion: " + Blogcaster.VERSION +"\n(c) 2005 Michael King, Kevin McAllister");
    label.setBounds(5, 5, 240, 75);
    
    Button close = new Button(shell, SWT.PUSH);
    close.setText(" OK ");
    close.setBounds(100, 85, 45, 25);
    close.addSelectionListener(new CloseWindowEventListener(shell));
    
    shell.open();
    
    Display display = parent.getDisplay();
    
    while (! shell.isDisposed()) {
      if (! display.readAndDispatch()) {
       display.sleep(); 
      }
     }
  }
}