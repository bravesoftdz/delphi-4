package edu.mq.comp.delphi.util;

public enum DefaultSource {
  BING("bing"), FLICKR("flickr"), GOOGLE_NEWS("googleNews"), GUARDIAN("guardian"), 
  TWITTER("twitter"), YAHOO("yahoo"), YOUTUBE("youtube");
  private String name;

  private DefaultSource(String name) {
	this.name = name;
  }
  
  public String getName() {
	return name;
  }
}
