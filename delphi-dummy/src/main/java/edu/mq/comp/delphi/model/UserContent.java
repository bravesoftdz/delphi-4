package edu.mq.comp.delphi.model;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;

@PersistenceCapable
public class UserContent {
  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  private Key key;

  @Persistent
  private Text content;

  @Persistent
  private String format;

  @Persistent
  private String dateTime;

  public Key getKey() {
	return key;
  }

  public Text getContent() {
	return content;
  }

  public void setContent(Text content) {
	this.content = content;
  }

  public String getFormat() {
	return format;
  }

  public void setFormat(String format) {
	this.format = format;
  }

  public String getDateTime() {
	return dateTime;
  }

  public void setDateTime(String dateTime) {
	this.dateTime = dateTime;
  }
  
  @Override
  public String toString() {
	return content != null ? content.getValue() : "";
  }
}
