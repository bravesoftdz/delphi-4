package edu.mq.comp.delphi.content.model;

public class YouTubeVideo extends Content {
  private static final long serialVersionUID = 1L;
  private String publishDate;
  private String id;
  private String authorName;
  private String authorUrl;

  public YouTubeVideo(String title, String url) {
	super(title, url);
  }

  public String getPublishDate() {
	return publishDate;
  }

  public void setPublishDate(String publishDate) {
	this.publishDate = publishDate;
  }

  public String getId() {
	return id;
  }

  public void setId(String id) {
	this.id = id;
  }

  public String getAuthorName() {
	return authorName;
  }

  public void setAuthorName(String authorName) {
	this.authorName = authorName;
  }
  
  public String getAuthorUrl() {
    return authorUrl;
  }

  public void setAuthorUrl(String authorUrl) {
    this.authorUrl = authorUrl;
  }
}
