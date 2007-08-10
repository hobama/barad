/*
 * $RCSfile: SimpleTag.java,v $    $Revision: 1.2 $  $Date: 2005/06/07 21:14:06 $ - $Author: mikemking $
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

/**
 * Represents a Simple HTML tag object -- a tag that does not require
 * any attributes, but simply wraps some text in open/close tags.
 * @author mking
 */
public class SimpleTag
{
  public final static String STRONG_TAG = "strong";
  public final static String EMPHASIS_TAG = "em";
  public final static String UNDERLINE_TAG = "u";
  
  private String tagType;
  private String displayText;
  
  public SimpleTag(String tagType, String displayText) {
    this.setTagType(tagType);
    this.setDisplayText(displayText);
  }
  
  public String getTag() {
    StringBuffer tag = new StringBuffer(this.getOpenTag());
    tag.append(this.getDisplayText());
    tag.append(this.getCloseTag());
    return tag.toString();
  }
  
  public String getOpenTag() {
    return "<" + this.getTagType() + ">";
  }
  
  public String getCloseTag() {
    return "</" + this.getTagType() + ">";
  }
  
  public String toString() {
    return this.getTag();
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
   * @return Returns the tagType.
   */
  public String getTagType() {
    return tagType;
  }
  
  /**
   * @param tagType The tagType to set.
   */
  public void setTagType(String tagType) {
    this.tagType = tagType;
  }
  
}
