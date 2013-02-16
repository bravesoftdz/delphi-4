package edu.mq.comp.delphi.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;

/**
 * This class performs date conversion. This is necessary because different
 * external sources represent date in different ways. This class converts them
 * to a standard format that is understood by clients
 * 
 * @author Shamim Ahmed
 * 
 */
public class DateConverter {
  private static final String TWEET_DATE_SOURCE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
  private static final String YOUTUBE_DATE_SOURCE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
  private static final String GUARDIAN_DATE_SOURCE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
  private static final String YAHOO_DATE_SOURCE_FORMAT = "EEE, d MMM yyyy HH:mm:ss Z";
  private static final String GOOGLE_NEWS_DATE_SOURCE_FORMAT = "EEE, d MMM yyyy HH:mm:ss Z";
  private static final String BING_NEWS_DATE_SOURCE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
  private static final String TARGET_FORMAT = "dd-MM-yyyy HH:mm";
  private static final String GMT = "GMT";
  
  private static final Logger logger = Logger.getLogger(DateConverter.class.getName());

  /**
   * 
   * @param sourceDateStr
   *          the input string representing the date
   * @param contentType
   *          the type of content
   * @return the converted date
   */
  public static String convertDate(String sourceDateStr, DefaultSource source) {
	String result = "";

	if (StringUtils.isBlank(sourceDateStr) || source == null) {
	  return result;
	}

	TimeZone gmtTimeZone = TimeZone.getTimeZone(GMT);
	DateFormat sourceFormat = null;

	if (source == DefaultSource.TWITTER) {
	  sourceFormat = new SimpleDateFormat(TWEET_DATE_SOURCE_FORMAT);
	} else if (source == DefaultSource.GOOGLE_NEWS) {
	  sourceFormat = new SimpleDateFormat(GOOGLE_NEWS_DATE_SOURCE_FORMAT);
	} else if (source == DefaultSource.GUARDIAN) {
	  sourceFormat = new SimpleDateFormat(GUARDIAN_DATE_SOURCE_FORMAT);
	} else if (source == DefaultSource.BING) {
	  sourceFormat = new SimpleDateFormat(BING_NEWS_DATE_SOURCE_FORMAT);
	} else if (source == DefaultSource.YAHOO) {
	  sourceFormat = new SimpleDateFormat(YAHOO_DATE_SOURCE_FORMAT);
	} else if (source == DefaultSource.YOUTUBE) {
	  sourceFormat = new SimpleDateFormat(YOUTUBE_DATE_SOURCE_FORMAT);
	} else {
	  throw new IllegalArgumentException(String.format("Unsupported source : %s", source.getName()));
	}

	sourceFormat.setTimeZone(gmtTimeZone);
	Date date = null;

	try {
	  date = sourceFormat.parse(sourceDateStr);
	  DateFormat targetFormat = new SimpleDateFormat(TARGET_FORMAT);
	  result = targetFormat.format(date);
	} catch (ParseException ex) {
	  logger.severe(ex.toString());
	}

	return result;
  }

  // private constructor to prevent instantiation
  private DateConverter() {
  }
}
