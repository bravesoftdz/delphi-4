package edu.mq.comp.delphi.mail;

import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang3.StringUtils;

public class EmailSender {
  private static final String EMAIL_SENDER_NAME = "Delphi";
  private static final String FROM_ADDRESS = "shamim.buet.99@gmail.com";
  private static final Logger logger = Logger.getLogger(EmailSender.class.getName());

  public static boolean sendEmail(String recipientAddress, String recipientName, String subject, String body) {
	if (StringUtils.isBlank(recipientAddress) || StringUtils.isBlank(recipientName) 
		|| StringUtils.isBlank(subject) || StringUtils.isBlank(body)) {
	  return false;
	}

	boolean result = false;
	Properties props = new Properties();
	Session session = Session.getDefaultInstance(props, null);

	try {
	  Message message = new MimeMessage(session);
	  message.setFrom(new InternetAddress(FROM_ADDRESS, EMAIL_SENDER_NAME));
	  message.addRecipient(RecipientType.TO, new InternetAddress(recipientAddress, recipientName));
	  message.setSubject(subject);
	  
	  Multipart multiPart = new MimeMultipart();
	  BodyPart bodyPart = new MimeBodyPart();
	  bodyPart.setContent(body, "text/html");
	  multiPart.addBodyPart(bodyPart);
	  message.setContent(multiPart);
	  
	  Transport.send(message);
	  result = true;
	} catch (Exception ex) {
	  logger.severe(ex.toString());
	}
	
	return result;
  }

  // private constructor to prevent instantiation
  private EmailSender() {
  }
}
