/*
 * $RCSfile: BlogListEventListener.java,v $    $Revision: 1.2 $  $Date: 2005/05/23 19:18:19 $ - $Author: mcallist $
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

import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Combo;

import com.webcodefocus.blogcaster.common.SmartObject;
import com.webcodefocus.blogcaster.model.Settings;
import com.webcodefocus.blogcaster.view.SettingsWindow;

/**
 * Instanciated when the user clicks the "Get Blogs" button on the
 * Settings screen.
 * @author Mike King
 */

public class BlogListEventListener extends SmartObject implements SelectionListener
{

  private Combo blogsDropdown;
    
  public BlogListEventListener(Combo blogsDropdown) {
    super();
    this.blogsDropdown = blogsDropdown;
  }
    
  /**
   * When the button is clicked or the menu is selected.
   */
  public void widgetSelected(SelectionEvent event) {
    // TODO: Fix bug in blogs dropdown
    // There is an obvious bug here. The list of blogs returned may
    // not be in numerical order, and some numeric values may not exist.
    // We must figure out how to assign each option of the dropdown
    // an associated int value that corresponds to the blogId

    // ugily calling saveSettings() on the View object because it hasn't
    // been factored into the model yet.
    SettingsWindow.saveSettings();
    Collection blogs = (Collection)Settings.getAvailableBlogs();
    if (blogs != null && blogs.size() > 0) {
      Iterator iter = blogs.iterator();
      int i = 1;
      while (iter.hasNext()) {
        Hashtable blog = (Hashtable)iter.next();
        int blogId = Integer.parseInt((String)blog.get("blogid"));
        this.blogsDropdown.add((String)blog.get("blogName"));
        
        // Stick the ID's into a hashmap, the key being the index of the
        // order we placed the blogs in the dropdown, and the value being
        // the actual blogID. Then when we save later, we can lookup the
        // ID relative to the dropdown index.
        SettingsWindow.blogIdMap.put(new Integer(i), new Integer(blogId));
        i++;
      }
      this.blogsDropdown.select(this.blogsDropdown.getItemCount() - 1);
    }
  }
    
    // I wonder what this method is for.
    public void widgetDefaultSelected(SelectionEvent event) {}
  }