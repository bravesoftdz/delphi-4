package edu.mq.comp.delphi.model.presentation;

import java.io.Serializable;

import edu.mq.comp.delphi.model.Tag;

public class PresentationTag implements Serializable {
  private static final long serialVersionUID = 1L;
  private final String keyword;

  public PresentationTag(Tag tag) {
	if (tag == null) {
	  throw new IllegalArgumentException("tag cannot be null");
	}
	
	keyword = tag.getKeyword();
  }
  
  public String getKeyword() {
    return keyword;
  }
  
  @Override
  public String toString() {
	return String.format("Tag [label : %s]", keyword);
  }
  
  @Override
  public boolean equals(Object obj) {
	if (!(obj instanceof PresentationTag)) {
	  return false;
	}
	
	PresentationTag otherTag = (PresentationTag) obj;
	return keyword.equals(otherTag.keyword);
  }
  
  @Override
  public int hashCode() {
	return keyword.hashCode();
  }
}
