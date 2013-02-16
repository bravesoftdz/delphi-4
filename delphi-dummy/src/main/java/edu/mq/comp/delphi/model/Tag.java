package edu.mq.comp.delphi.model;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.apache.commons.lang3.StringUtils;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Tag {
  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  private Key key;

  @Persistent
  private String keyword;

  public Key getKey() {
	return key;
  }

  public String getKeyword() {
	return keyword;
  }

  public void setKeyword(String keyword) {
	if (StringUtils.isBlank(keyword)) {
	  throw new IllegalArgumentException("keyword cannot be blank");
	}

	this.keyword = keyword;
  }

  @Override
  public String toString() {
	return String.format("Tag [label : %s]", keyword);
  }
  
  @Override
  public boolean equals(Object obj) {
	if (!(obj instanceof Tag)) {
	  return false;
	}
	
	Tag otherTag = (Tag) obj;
	return keyword.equals(otherTag.keyword);
  }
  
  @Override
  public int hashCode() {
	return keyword.hashCode();
  }
}
