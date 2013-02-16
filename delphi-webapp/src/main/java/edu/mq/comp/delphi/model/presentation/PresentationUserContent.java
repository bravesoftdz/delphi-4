package edu.mq.comp.delphi.model.presentation;

import java.io.Serializable;

import com.google.appengine.api.datastore.Text;

import edu.mq.comp.delphi.model.UserContent;

public class PresentationUserContent implements Serializable {
  private static final long serialVersionUID = 1L;
  private final Text content;
  private final String format;
  private final String dateTime;

  public PresentationUserContent(UserContent userContent) {
	if (userContent == null) {
	  throw new IllegalArgumentException("userContent cannot be null");
	}
	
	content = userContent.getContent();
	format = userContent.getFormat();
	dateTime = userContent.getDateTime();
  }
  
  public Text getContent() {
	return content;
  }

  public String getFormat() {
	return format;
  }

  public String getDateTime() {
	return dateTime;
  }
}
