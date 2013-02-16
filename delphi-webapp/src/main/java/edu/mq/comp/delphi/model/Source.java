package edu.mq.comp.delphi.model;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.apache.commons.lang3.StringUtils;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Source {
  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  private Key key;

  @Persistent
  private String name;

  public Key getKey() {
	return key;
  }

  public String getName() {
	return name;
  }

  public void setName(String name) {
	if (StringUtils.isBlank(name)) {
	  throw new IllegalArgumentException("name cannot be blank");
	}

	this.name = name;
  }

  @Override
  public String toString() {
	return String.format("Source [name : %s]", name);
  }
  
  @Override
  public boolean equals(Object obj) {
	if (!(obj instanceof Source)) {
	  return false;
	}
	
	Source otherSource = (Source) obj;
	return name.equals(otherSource.name);
  }
  
  @Override
  public int hashCode() {
	return name.hashCode();
  }
}
