package edu.mq.comp.delphi.content.model;

public class RssNews extends Content {
  private static final long serialVersionUID = 1L;
  private String description;
  private String publishDate;
  
  public RssNews(String title, String url) {
	super(title, url);
  }
  
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getPublishDate() {
    return publishDate;
  }

  public void setPublishDate(String publishDate) {
    this.publishDate = publishDate;
  }
}
