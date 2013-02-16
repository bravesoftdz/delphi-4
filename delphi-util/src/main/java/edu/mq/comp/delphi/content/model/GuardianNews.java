package edu.mq.comp.delphi.content.model;

public class GuardianNews extends Content {
  private static final long serialVersionUID = 1L;
  private String publishDate;
  
  public GuardianNews(String title, String url) {
	super(title, url);
  }
  
  public String getPublishDate() {
    return publishDate;
  }

  public void setPublishDate(String publishDate) {
    this.publishDate = publishDate;
  }
}
