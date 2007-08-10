/*
 * $RCSfile: Blog.java,v $    $Revision: 1.1.1.1 $  $Date: 2005/05/21 23:50:57 $ - $Author: mikemking $
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

package com.webcodefocus.blogcaster.model;

import com.webcodefocus.blogcaster.common.SmartObject;

/**
 * Blog: This Class defines the blog object that will be posted to.
 * @author Kevin McAllister
 */
public class Blog extends SmartObject {
  /** 
   * application key needed only for blogger 
   */
  private static String applicationKey = "0123456789ABCDEF";  // see: http://www.blogger.com/developers/api/1_docs/
  // default to 1, but this is required
  private static String blogId = "1";
  private static String url;
  private static String user;
  private static String pass;
  
  /**
   * @return Returns the pass.
   */
  static public String getPass() {
    return pass;
  }
  /**
   * @param pass The pass to set.
   */
  static public void setPass(String pass) {
    Blog.pass = pass;
  }
  /**
   * @return Returns the url.
   */
  static public String getUrl() {
    return url;
  }
  /**
   * @param url The url to set.
   */
  static public void setUrl(String url) {
    Blog.url = url;
  }
  /**
   * @return Returns the user.
   */
  static public String getUser() {
    return user;
  }
  /**
   * @param user The user to set.
   */
  static public void setUser(String user) {
    Blog.user = user;
  }
  /**
   * @return Returns the applicationKey.
   */
  static public String getApplicationKey() {
    return applicationKey;
  }
  /**
   * @param applicationKey The applicationKey to set.
   */
  static public void setApplicationKey(String applicationKey) {
    Blog.applicationKey = applicationKey;
  }
  /**
   * @return Returns the blogId.
   */
  static public String getBlogId() {
    return blogId;
  }
  /**
   * @param blogId The blogId to set.
   */
  static public void setBlogId(String blogId) {
    Blog.blogId = blogId;
  }
}
