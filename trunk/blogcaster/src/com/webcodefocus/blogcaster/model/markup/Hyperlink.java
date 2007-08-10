/*
 * $RCSfile: Hyperlink.java,v $    $Revision: 1.2 $  $Date: 2005/06/04 21:06:56 $ - $Author: mikemking $
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
 
package com.webcodefocus.blogcaster.model.markup;

import com.webcodefocus.blogcaster.common.SmartObject;

/**
 * Represents a Hyperlink object of the content of a post. 
 * @author Mike King
 */
public class Hyperlink extends SmartObject
{
  private String url = new String("");
  private String displayText = new String("");
  private boolean newWindow = false;
  
  /**
   * Creates a new Hyperlink object with the given parameters.
   * @param url String representing the URL (href) of the hyperlink
   * @param displayText String displayed between the opening and closing
   *   tags of the hyperlink. Also used in the "title" attribute.
   * @param newWindow If <code>true</code>, then adds <code>target="_blank"</code>
   *   to the tag.
   */
  public Hyperlink(String url, String displayText, boolean newWindow) {
    super();
    this.url = url;
    this.displayText = displayText;
    this.newWindow = newWindow;
  }
  
  /**
   * Generates an HTML &lt;a&gt; tag based on the object's properties.
   * @return String representation of the HTML tag
   */
  public String getHyperlinkTag() {
    StringBuffer tag = new StringBuffer("<a ");
    tag.append("href=\"" + this.url + "\" ");
    tag.append("title=\"" + this.displayText + "\" ");
    if (this.newWindow) {
      tag.append("target=\"_blank\" ");
    }
    tag.append(">" + this.displayText + "</a>");
    return tag.toString();
  }
  
  /**
   * Returns a string representation of the object.
   * @return String representation of the object.
   */
  public String toString() {
    return this.getHyperlinkTag();
  }
  
  
  /**
   * @return Returns the displayText.
   */
  public String getDisplayText() {
    return displayText;
  }
  
  /**
   * @param displayText The displayText to set.
   */
  public void setDisplayText(String displayText) {
    this.displayText = displayText;
  }
  
  /**
   * @return Returns the newWindow.
   */
  public boolean getNewWindow() {
    return newWindow;
  }
  
  /**
   * @param newWindow The newWindow to set.
   */
  public void setNewWindow(boolean newWindow) {
    this.newWindow = newWindow;
  }
  
  /**
   * @return Returns the url.
   */
  public String getUrl() {
    return url;
  }
  
  /**
   * @param url The url to set.
   */
  public void setUrl(String url) {
    this.url = url;
  }
}
