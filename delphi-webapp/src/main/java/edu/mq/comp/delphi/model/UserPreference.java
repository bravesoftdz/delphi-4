package edu.mq.comp.delphi.model;

import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class UserPreference {
  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  private Key key;

  @Persistent
  private Set<Tag> tags = new HashSet<Tag>();

  @Persistent
  private Set<Source> sources = new HashSet<Source>();

  @Persistent
  private int frequency;

  @Persistent
  private int maxPerTag;

  @Persistent
  private boolean notificationEnabled = true;

  @Persistent
  private String mobileNumber;

  @Persistent
  private int notificationType;

  public Key getKey() {
	return key;
  }

  public Set<Tag> getTags() {
	return tags;
  }

  public boolean addTag(Tag tag) {
	return tags.add(tag);
  }
  
  public boolean removeTag(Tag tag) {
	return tags.remove(tag);
  }

  public Set<Source> getSources() {
	return sources;
  }
  
  public void setSources(Set<Source> sources) {
	this.sources = sources;
  }

  public boolean addSource(Source source) {
	return sources.add(source);
  }
  
  public boolean removeSource(Source source) {
	return sources.remove(source);
  }

  public int getFrequency() {
	return frequency;
  }

  public void setFrequency(int frequency) {
	this.frequency = frequency;
  }

  public int getMaxPerTag() {
	return maxPerTag;
  }

  public void setMaxPerTag(int maxPerTag) {
	this.maxPerTag = maxPerTag;
  }

  public boolean isNotificationEnabled() {
	return notificationEnabled;
  }

  public void setNotificationEnabled(boolean notificationEnabled) {
	this.notificationEnabled = notificationEnabled;
  }

  public String getMobileNumber() {
	return mobileNumber;
  }

  public void setMobileNumber(String mobileNumber) {
	this.mobileNumber = mobileNumber;
  }

  public int getNotificationType() {
	return notificationType;
  }

  public void setNotificationType(int notificationType) {
	this.notificationType = notificationType;
  }
}
