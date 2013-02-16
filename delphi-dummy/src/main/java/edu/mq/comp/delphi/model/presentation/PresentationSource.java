package edu.mq.comp.delphi.model.presentation;

import java.io.Serializable;

import edu.mq.comp.delphi.model.Source;

public class PresentationSource implements Serializable {
  private static final long serialVersionUID = 1L;
  private final String name;

  public PresentationSource(Source source) {
	if (source == null) {
	  throw new IllegalArgumentException("source cannot be null");
	}
	
	name = source.getName();
  }
  
  public String getName() {
	return name;
  }

  
  @Override
  public String toString() {
	return String.format("Source [name : %s]", name);
  }
  
  @Override
  public boolean equals(Object obj) {
	if (!(obj instanceof PresentationSource)) {
	  return false;
	}
	
	PresentationSource otherSource = (PresentationSource) obj;
	return name.equals(otherSource.name);
  }
  
  @Override
  public int hashCode() {
	return name.hashCode();
  }
}
