package edu.mq.comp.delphi.spring.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.appengine.api.datastore.Text;

import edu.mq.comp.delphi.content.model.Content;
import edu.mq.comp.delphi.mail.EmailSender;
import edu.mq.comp.delphi.model.DelphiUser;
import edu.mq.comp.delphi.model.NotificationType;
import edu.mq.comp.delphi.model.UserContent;
import edu.mq.comp.delphi.model.UserPreference;
import edu.mq.comp.delphi.sms.SmsSender;
import edu.mq.comp.delphi.util.ContentFetchUtil;
import edu.mq.comp.delphi.util.MessageType;
import edu.mq.comp.delphi.util.PMFService;
import edu.mq.comp.delphi.xml.Converter;

@Controller
@RequestMapping("/task")
public class TaskController {
  private static final String EMPTY_PAGE = "empty";
  private static final int SMS_MAX_LENGTH = 160;
  private final Logger logger = Logger.getLogger(TaskController.class.getName());

  @RequestMapping(value = "/fetchContentForUser", method = RequestMethod.POST)
  public String fetchContentForUser(@RequestParam("email") String email) {
	if (StringUtils.isNotBlank(email)) {
	  PersistenceManagerFactory pmf = PMFService.getInstance().getPersistenceManagerFactory();
	  PersistenceManager manager = pmf.getPersistenceManager();

	  Query query = manager.newQuery(DelphiUser.class, String.format("this.email == '%s'", email));
	  DelphiUser user = null;

	  try {
		@SuppressWarnings("unchecked")
		List<DelphiUser> users = (List<DelphiUser>) query.execute();

		if (users != null && users.size() > 0) {
		  user = users.get(0);
		}

		if (user != null) {
		  UserPreference preference = user.getUserPreference();
		  Map<String, List<Content>> contentMap = ContentFetchUtil.getContentListMap(preference);
		  String xml = Converter.contentsToXml(contentMap);
		  Text text = new Text(xml);

		  UserContent userContent = user.getUserContent();
		  userContent.setContent(text);
		  userContent.setFormat("xml");

		  DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		  dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		  userContent.setDateTime(dateFormat.format(new Date()));

		  user.setUserContent(userContent);
		  manager.makePersistent(user);
		}
	  } finally {
		query.closeAll();
		manager.close();
	  }
	}

	return EMPTY_PAGE;
  }

  @RequestMapping(value = "/sendMessageToUser", method = RequestMethod.POST)
  public String sendMessageToUser(@RequestParam("email") String email) {
	if (StringUtils.isNotBlank(email)) {
	  PersistenceManagerFactory pmf = PMFService.getInstance().getPersistenceManagerFactory();
	  PersistenceManager manager = pmf.getPersistenceManager();

	  Query query = manager.newQuery(DelphiUser.class, String.format("this.email == '%s'", email));
	  DelphiUser user = null;

	  try {
		@SuppressWarnings("unchecked")
		List<DelphiUser> users = (List<DelphiUser>) query.execute();

		if (users != null && users.size() > 0) {
		  user = users.get(0);
		}

		if (user != null) {
		  UserContent userContent = user.getUserContent();

		  if (userContent != null) {
			String xmlContent = userContent.getContent().getValue();
			
			String emailContent = Converter.xmlToHtml(xmlContent, MessageType.EMAIL);

			if (StringUtils.isNotBlank(emailContent)) {
			  logger.info(String.format("The email message to be sent to user is %s", emailContent));
			  
			  UserPreference pref = user.getUserPreference();
			  int notificationType = pref.getNotificationType();
			  String mobileNumber = pref.getMobileNumber();
			  String smsContent = Converter.xmlToHtml(xmlContent, MessageType.SMS);
			  
			  if (smsContent.length() > SMS_MAX_LENGTH) {
				smsContent = smsContent.substring(0, SMS_MAX_LENGTH);
			  }
			  
			  switch (notificationType) {
				case NotificationType.EMAIL_ONLY:
				  logger.info("Email is being sent......");
				  
				  EmailSender.sendEmail(user.getEmail(), user.getNickName(), "Latest content from Delphi", emailContent);
				  break;
	
				case NotificationType.MOBILE_ONLY:
				  logger.info("SMS is being sent......");

				  if (StringUtils.isNotBlank(mobileNumber)) {
					SmsSender.sendSms(mobileNumber, smsContent);
				  }
				  break;
				  
				case NotificationType.BOTH:
				  logger.info("Email and SMS are being sent......");

				  EmailSender.sendEmail(user.getEmail(), user.getNickName(), "Latest content from Delphi", emailContent);
				  
				  if (StringUtils.isNotBlank(mobileNumber)) {
					SmsSender.sendSms(mobileNumber, smsContent);
				  }
				  break;
				  
				default:
				  throw new RuntimeException(String.format("invalid value for notificationType : %d", notificationType));
			  }
			}
		  }
		}
	  } finally {
		query.closeAll();
		manager.close();
	  }
	}

	return EMPTY_PAGE;
  }
}
