package edu.mq.comp.delphi.model.presentation;

import java.io.Serializable;

import edu.mq.comp.delphi.model.HistoryLog;

public class PresentationHistoryLog implements Serializable {
  private static final long serialVersionUID = 1L;
  private final String action;
  private final String dateTime;
  private final String description;
  
  public PresentationHistoryLog(HistoryLog log) {
	action = log.getAction();
	dateTime = log.getDateTime();
	description = log.getDescription();
  }

  public String getAction() {
	return action;
  }

  public String getDateTime() {
	return dateTime;
  }

  public String getDescription() {
	return description;
  }
}
