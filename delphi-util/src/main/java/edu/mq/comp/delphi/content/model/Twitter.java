package edu.mq.comp.delphi.content.model;

public class Twitter extends Content {
  private static final long serialVersionUID = 1L;
  private String publishDate;
  private String authorName;
  private String authorUrl;
  private String authorPicUrl;

  public Twitter(String title, String url) {
	super(title, url);
  }

  public String getPublishDate() {
	return publishDate;
  }

  public void setPublishDate(String publishDate) {
	this.publishDate = publishDate;
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

  public String getAuthorPicUrl() {
	return authorPicUrl;
  }

  public void setAuthorPicUrl(String authorPicUrl) {
	this.authorPicUrl = authorPicUrl;
  }
}
