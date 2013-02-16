package edu.mq.comp.delphi.sms;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;

import edu.mq.comp.delphi.http.HttpClient;

public class SmsSender {
  private static final Logger logger = Logger.getLogger(SmsSender.class.getName());
  private static final String SMS_URL = "http://www.smsglobal.com/http-api.php";
  
  public static boolean sendSms(String mobileNumber, String smsContent) {
	if (StringUtils.isBlank(mobileNumber) || StringUtils.isBlank(smsContent)) {
	  return false;
	}
		
	logger.info(String.format("sms content : %s", smsContent));
	
	Map<String, String> paramMap = new LinkedHashMap<String, String>();
	paramMap.put("action", "sendsms");
	paramMap.put("user", "shamim07");
	paramMap.put("password", "shimu30");
	paramMap.put("from", "611450289581");
	paramMap.put("to", mobileNumber);
	paramMap.put("text", smsContent);
	return HttpClient.getInstance().doPost(SMS_URL, paramMap);
  }
  
  // private constructor to prevent instantiation
  private SmsSender() {
  }
}
