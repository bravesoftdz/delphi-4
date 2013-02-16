package edu.mq.comp.delphi.model;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class DelphiUser {
  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  private Long id;

  @Persistent
  private String email;

  @Persistent
  private String nickName;

  @Persistent
  private UserPreference userPreference;
  
  @Persistent
  private List<HistoryLog> historyLogs = new ArrayList<HistoryLog>();
  
  @Persistent
  private UserContent userContent;

  public Long getId() {
	return id;
  }

  public String getEmail() {
	return email;
  }

  public void setEmail(String email) {
	this.email = email;
  }

  public String getNickName() {
	return nickName;
  }

  public void setNickName(String nickname) {
	this.nickName = nickname;
  }

  public UserPreference getUserPreference() {
	return userPreference;
  }

  public void setUserPreference(UserPreference userPreference) {
	this.userPreference = userPreference;
  }
  
  public List<HistoryLog> getHistoryLogs() {
    return historyLogs;
  }

  public void addHistoryLog(HistoryLog historyLog) {
	historyLogs.add(historyLog);
  }

  public UserContent getUserContent() {
    return userContent;
  }

  public void setUserContent(UserContent userContent) {
    this.userContent = userContent;
  }
  
  @Override
  public String toString() {
	return String.format("DelphiUser [nickname : %s, email : %s]", nickName, email);
  }
}
