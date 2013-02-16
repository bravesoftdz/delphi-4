package edu.mq.comp.delphi.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.google.appengine.api.datastore.Text;

import edu.mq.comp.delphi.model.DelphiUser;
import edu.mq.comp.delphi.model.NotificationType;
import edu.mq.comp.delphi.model.UserContent;
import edu.mq.comp.delphi.model.UserPreference;

public class EntityUtil {
  public static UserPreference createDefaultPreference() {
	UserPreference preference = new UserPreference();
	preference.setFrequency(1);
	preference.setMaxPerTag(5);
	preference.setNotificationEnabled(true);
	preference.setNotificationType(NotificationType.EMAIL_ONLY);
	return preference;
  }

  public static DelphiUser createNewUser(String email, String nickName) {
	DelphiUser user = new DelphiUser();
	user.setEmail(email);
	user.setNickName(nickName);
	user.setUserPreference(createDefaultPreference());
	
	UserContent userContent = new UserContent();
	userContent.setContent(new Text(""));
	userContent.setFormat("xml");
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
	userContent.setDateTime(dateFormat.format(new Date()));
	user.setUserContent(userContent);
	
	return user;
  }
  
  // private constructor to prevent instantiation
  private EntityUtil() {
  }
}
