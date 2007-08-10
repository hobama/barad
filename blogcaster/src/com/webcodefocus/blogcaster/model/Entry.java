/*
 * $RCSfile: Entry.java,v $    $Revision: 1.2 $  $Date: 2005/06/21 03:30:55 $ - $Author: mcallist $
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

import java.net.MalformedURLException;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.xmlrpc.XmlRpcClientLite;

import com.webcodefocus.blogcaster.common.SmartObject;

/**
 * Entry: This class represents an individual posting
 * on the blog.  It is intended to be an intermediate class
 * used to hold the entry to be posted.
 * @author Kevin McAllister
 */
public class Entry extends SmartObject {
  private String title;
  private String body;
  /** the stuff that appears on the post page but not the main page */
  private String extended;
  /** the excerpt sent in trackback pings */
  private String excerpt; // TODO add code to automagically guess a good excerpt (WP sucks at it)
  // TODO these should both be arrays of some kind
  private String trackback;
  private String category;
  
  /** the xml method name: reference http://www.sixapart.com/movabletype/docs/mtmanual_programmatic#xmlrpc%20api */
  private static String POST_METHOD = "metaWeblog.newPost";
  // TODO add support for the blogger API, since google owns it and it has a huge user base, and all.
  
  /**
   * Constructor for the minimum post.
   * @param title
   * @param body
   */
  public Entry(String title, String body) {
    super();
    this.title = title;
    this.body = body;
  }
  
  /**
   * @return Returns the body.
   */
  public String getBody() {
    return body;
  }
  /**
   * @param body The body to set.
   */
  public void setBody(String body) {
    this.body = body;
  }
  /**
   * @return Returns the category.
   */
  public String getCategory() {
    return category;
  }
  /**
   * @param category The category to set.
   */
  public void setCategory(String category) {
    this.category = category;
  }
  /**
   * @return Returns the extended.
   */
  public String getExtended() {
    return extended;
  }
  /**
   * @param extended The extended to set.
   */
  public void setExtended(String extended) {
    this.extended = extended;
  }
  /**
   * @return Returns the title.
   */
  public String getTitle() {
    return title;
  }
  /**
   * @param title The title to set.
   */
  public void setTitle(String title) {
    this.title = title;
  }
  /**
   * @return Returns the trackback.
   */
  public String getTrackback() {
    return trackback;
  }
  /**
   * @param trackback The trackback to set.
   */
  public void setTrackback(String trackback) {
    this.trackback = trackback;
  }
  
  public String toString() {
    StringBuffer buffer = new StringBuffer();
    buffer.append("{title:");
    buffer.append(this.title);
    buffer.append(", body:");
    buffer.append(this.body);
    buffer.append("}");
    return buffer.toString();
  }
  
  /**
   * uses the static entries in the Blog class to
   * and posts to it.
   */
  public boolean post() {
    // TODO use non blocking call and the progress widget
    boolean success = false;
    try {
      XmlRpcClientLite xmlrpc = new XmlRpcClientLite(Blog.getUrl());
      Vector params = this.buildParamsMetaWeblog();
      // this method returns a string
      String result = (String)xmlrpc.execute(Entry.POST_METHOD, params);
      //log.info("post result: "+result);
      try {
        int postId = Integer.parseInt(result);
        if (postId > 0) {
          success = true;
        }
      } catch (NumberFormatException nfe) {
        //log.debug("result was not an integer.", nfe);
      }
      // TODO If the result is a postId, then set the category, using mt.setPostCategories
    } catch (MalformedURLException mfu) {
      //log.error("Cannot create XmlRpcClientLite", mfu);
    } catch (Exception ioe) {
      //log.error("Failed executing xml-rpc", ioe);
    }
    return success;
  }
  /**
   * Builds a vector of the parameters for the posting using the
   * metaWeblog.newPost with MT sprinkles.
   * @return a vector filled with parameters
   */
  private Vector buildParamsMetaWeblog() {
    // parameters need to be added to the vector in the right order.
    // Parameters: String blogid, String username, String password, struct content, boolean publish
    Vector params = new Vector();
    params.addElement(String.valueOf(Blog.getBlogId()));
    params.addElement(Blog.getUser());
    params.addElement(Blog.getPass());
    Hashtable content = this.buildContent();
    params.addElement(content);
    params.addElement(new Boolean(true));  // publish by default
    // TODO add support for setting this, so you can upload an entry without publishing
    return params;
  }
  /**
   * builds up a hashtable of the content for the metaweblog api.
   * @return the HashTable
   */
  private Hashtable buildContent() {
    // TODO add preferences and stuff for a bunch of the other supported mt stuff
    // metaWeblog supports a struct as the content:
    // the struct content can contain the following standard keys: title, for the
    // title of the entry; description, for the body of the entry; and
    // dateCreated, to set the created-on date of the entry. In addition, Movable
    // Type's implementation allows you to pass in values for five other keys: int
    // mt_allow_comments, the value for the allow_comments field; int
    // mt_allow_pings, the value for the allow_pings field; String
    // mt_convert_breaks, the value for the convert_breaks field; String
    // mt_text_more, the value for the additional entry text; String mt_excerpt,
    // the value for the excerpt field; String mt_keywords, the value for the
    // keywords field; and array mt_tb_ping_urls, the list of TrackBack ping URLs
    // for this entry. If specified, dateCreated should be in ISO.8601 format.
    Hashtable content = new Hashtable();
    this.addField("title", this.title, content);
    this.addField("description", this.body, content);
    this.addField("mt_text_more", this.extended, content);
    this.addField("mt_excerpt", this.excerpt, content);
    return content;
  }
  
  /**
   * Simple utility method separated out to simplify the code.  Takes a key name
   * a field and the reference to the content hashtable and decides if they should be added or not.
   * @param key The key (a String) according to the movabletype api. 
   * @param field the actual content of that field.
   * @param content The hashtable to potentially have an entry added to it.
   */
  private void addField(String key, String field, Hashtable content) {
    if (field != null && !field.equals("")) {
      content.put(key, field);
    }
  }
}
