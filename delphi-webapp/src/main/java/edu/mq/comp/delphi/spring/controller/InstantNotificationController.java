package edu.mq.comp.delphi.spring.controller;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

import edu.mq.comp.delphi.content.model.Content;
import edu.mq.comp.delphi.mail.EmailSender;
import edu.mq.comp.delphi.model.presentation.PresentationDelphiUser;
import edu.mq.comp.delphi.sms.SmsSender;
import edu.mq.comp.delphi.util.MessageType;
import edu.mq.comp.delphi.xml.Converter;

@Controller
@RequestMapping("/instantNotification")
public class InstantNotificationController {

  @RequestMapping(method = RequestMethod.POST)
  public String sendInstantNotification(@RequestParam("source") String source,
	  @RequestParam("keyword") String keyword, @RequestParam("notificationType") int notificationType,
	  HttpSession session) {
	String viewName = "instantNotificationFailure";
	PresentationDelphiUser presentationUser = (PresentationDelphiUser) session.getAttribute("presentationUser");
	boolean result = false;

	MessageType messageType;

	if (notificationType == MessageType.SMS.ordinal()) {
	  messageType = MessageType.SMS;
	} else {
	  messageType = MessageType.EMAIL;
	}

	if (presentationUser != null) {
	  MemcacheService cacheService = MemcacheServiceFactory.getMemcacheService();
	  String key = String.format("%s-%s-%s", presentationUser.getEmail(), source, keyword);

	  @SuppressWarnings("unchecked")
	  List<Content> contents = (List<Content>) cacheService.get(key);

	  if (contents != null) {
		String xmlString = Converter.contentsToXml(Collections.singletonMap(keyword, contents));
		String resultString = Converter.xmlToHtml(xmlString, messageType);

		switch (messageType) {
		  case EMAIL:
			result = EmailSender.sendEmail(presentationUser.getEmail(), presentationUser.getNickName(),
				                           "Search Result from Delphi", resultString);
			break;
		  case SMS:
			String mobileNumber = presentationUser.getUserPreference().getMobileNumber();
			result = SmsSender.sendSms(mobileNumber, resultString);
			break;
		  default:
			throw new RuntimeException("unrecognized message type");
		}
	  }
	}

	if (result) {
	  viewName = "instantNotificationSuccess";
	}

	return viewName;
  }
}
