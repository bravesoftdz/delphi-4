package edu.mq.comp.delphi.model;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class HistoryLog {
  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  private Key key;

  @Persistent
  private String action;

  @Persistent
  private String dateTime;

  @Persistent
  private String description;

  public Key getKey() {
	return key;
  }

  public String getAction() {
	return action;
  }

  public void setAction(String action) {
	this.action = action;
  }

  public String getDateTime() {
	return dateTime;
  }

  public void setDateTime(String dateTime) {
	this.dateTime = dateTime;
  }

  public String getDescription() {
	return description;
  }

  public void setDescription(String description) {
	this.description = description;
  }
}
