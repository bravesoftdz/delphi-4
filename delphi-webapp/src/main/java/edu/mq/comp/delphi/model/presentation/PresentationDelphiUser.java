package edu.mq.comp.delphi.model.presentation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import edu.mq.comp.delphi.model.DelphiUser;
import edu.mq.comp.delphi.model.HistoryLog;

public class PresentationDelphiUser implements Serializable {
  private static final long serialVersionUID = 1L;
  private final String email;
  private final String nickName;
  private final PresentationUserPreference userPreference;
  private final List<PresentationHistoryLog> historyLogs;
  private final PresentationUserContent userContent;

  public PresentationDelphiUser(DelphiUser delphiUser) {
	if (delphiUser == null) {
	  throw new IllegalArgumentException("delphiUser cannot be null");
	}

	email = delphiUser.getEmail();
	nickName = delphiUser.getNickName();
	userPreference = new PresentationUserPreference(delphiUser.getUserPreference());
	userContent = new PresentationUserContent(delphiUser.getUserContent());

	historyLogs = new ArrayList<PresentationHistoryLog>();

	for (HistoryLog log : delphiUser.getHistoryLogs()) {
	  historyLogs.add(new PresentationHistoryLog(log));
	}
  }

  public String getEmail() {
	return email;
  }

  public String getNickName() {
	return nickName;
  }

  public PresentationUserPreference getUserPreference() {
	return userPreference;
  }

  public List<PresentationHistoryLog> getHistoryLogs() {
	return historyLogs;
  }

  public PresentationUserContent getUserContent() {
	return userContent;
  }
}
