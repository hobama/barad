/*
 * $RCSfile: Settings.java,v $    $Revision: 1.4 $  $Date: 2005/06/01 19:51:45 $ - $Author: mikemking $
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.Properties;
import java.util.Vector;

import org.apache.xmlrpc.XmlRpcClient;

import com.webcodefocus.blogcaster.Blogcaster;
import com.webcodefocus.blogcaster.common.SmartObject;

public class Settings extends SmartObject
{
  /**
   * PROPS_PATH must be set as a system property and it is expected
   * to have the right file separator on there if it isn't, then the
   * cwd will store the Blogcaster.properties file.
   */
  private final static String PROPS_PATH = System.getProperty("blogcaster.settingspath", "");
  public final static String PROPS_FILE = Blogcaster.NAME+".properties";
  private final static String PROPS = PROPS_PATH + PROPS_FILE;
  private static String LIST_BLOGS_METHOD = "blogger.getUsersBlogs";
  
  /**
   * Read in the settings.properties file from disk, load into a
   * Properties object, and load into the static Blog object in memory.
   * If the file does not already exist, create and initialize it.
   * @return false if it had to create a new properties file, true
   *  if the settings alread existed.
   * @author Mike King
   */
  public static boolean loadProperties() {    
    Properties props = new Properties();     
    boolean propertiesExist = false;
    try {
      // Open the props file
      File propsFile = new File(PROPS);
      
      if (propsFile.exists()) {
        propertiesExist = true;
      } else {
        propsFile.createNewFile();
        
        // set defaults
        props.setProperty("url", "");
        props.setProperty("username", "");
        props.setProperty("password", "");
        props.setProperty("blogId", "");
        props.setProperty("closeAfterPost", "true");   // true by default 
        
        // store the default file
        props.store(new FileOutputStream(propsFile), Blogcaster.NAME);
      }
      
      // Read in the stored properties
      props.load(new FileInputStream(propsFile));        
    } catch (Exception e) {
      e.printStackTrace();
    }
            
    Blog.setUrl(props.getProperty("url", ""));
    Blog.setUser(props.getProperty("username", ""));
    Blog.setPass(props.getProperty("password", ""));
    Blog.setBlogId(props.getProperty("blogId", ""));
    
    String closeAfterPost = (String)props.getProperty("closeAfterPost", "true");   
    if (closeAfterPost == null || closeAfterPost.equals("")) {      
      closeAfterPost = "true";
    }
    
    if (closeAfterPost.equals("true")) {      
      Blogcaster.setCloseAfterPost(true);
    } else {            
      Blogcaster.setCloseAfterPost(false);
    }

    return propertiesExist;
  }
  
  /**
   * Saves the application properties settings to the settings.properties
   * file on disk.
   * @author Mike King
   */
  public static void saveProperties() {
    Properties props = new Properties();
    
    props.setProperty("url", Blog.getUrl());
    props.setProperty("username", Blog.getUser());
    props.setProperty("password", Blog.getPass());
    props.setProperty("blogId", Blog.getBlogId());
    
    if (Blogcaster.isCloseAfterPost()) {
      props.setProperty("closeAfterPost", "true");
    } else {
      props.setProperty("closeAfterPost", "false");
    }
    
    //Save the properties defined in props to a disk file
    try {
      File propsFile = new File(PROPS);
      
      //Write out the list of properties to the file
      //under the section PropertiesDemo
      props.store(new FileOutputStream(propsFile), Blogcaster.NAME);
    } catch (Exception e) {
      // laziness... I'm sure this will come back to haunt me.
    }
  }
  
  public static Collection getAvailableBlogs() {
    Collection blogs = new Vector();
    try {
      XmlRpcClient xmlrpc = new XmlRpcClient(Blog.getUrl());  
      Vector params = new Vector();
      params.addElement(Blog.getApplicationKey());  // appkey
      params.addElement(Blog.getUser());
      params.addElement(Blog.getPass());
            
      // this method returns a 
      blogs = (Collection)xmlrpc.execute(Settings.LIST_BLOGS_METHOD, params);
      
      // TODO: replace this debug statement - log.debug("blogs list = [" + blogs + "]");

    } catch (MalformedURLException mfu) {
      //log.error("Cannot create XmlRpcClient", mfu);
      System.out.println("Cannot create XmlRpcClient" + mfu);
    } catch (Exception ioe) {
      //log.error("Failed executing xml-rpc", ioe);
      System.out.println("Failed executing xml-rpc" + ioe);
      ioe.printStackTrace();
    }
    return blogs;
  }
  
}
