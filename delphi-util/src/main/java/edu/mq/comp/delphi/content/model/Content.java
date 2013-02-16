package edu.mq.comp.delphi.content.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Content implements Serializable {
  private static final long serialVersionUID = 1L;
  private final String title;
  private String url;

  public Content(String title, String url) {
	this.title = title;
	this.url = url;
  }

  public String getTitle() {
	return title;
  }

  public String getUrl() {
	return url;
  }

  public void setUrl(String url) {
	this.url = url;
  }

  @Override
  public String toString() {
	ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
	builder.append("title", title);
	builder.append("url", url);
	return builder.toString();
  }
}
