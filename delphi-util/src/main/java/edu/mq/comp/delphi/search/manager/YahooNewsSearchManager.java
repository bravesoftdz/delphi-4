package edu.mq.comp.delphi.search.manager;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;

import edu.mq.comp.delphi.content.model.Content;
import edu.mq.comp.delphi.util.DefaultSource;

public class YahooNewsSearchManager extends SearchManager {
  private static final YahooNewsSearchManager INSTANCE = new YahooNewsSearchManager();
  private static final String HTTP_PROTOCOL = "http";
  private static final String YAHOO_SERVER_NAME = "news.search.yahoo.com";
  private static final String YAHOO_RELATIVE_PATH = "/news/rss";
  private final Logger logger = Logger.getLogger(YahooNewsSearchManager.class.getName());

  public static YahooNewsSearchManager getInstance() {
	return INSTANCE;
  }

  @Override
  protected URI buildUri(String keyword, int maxPerTag) {
	URI uri = null;

	if (StringUtils.isBlank(keyword) || maxPerTag <= 0) {
	  return uri;
	}

	Map<String, String> params = new HashMap<String, String>();
	params.put("p", keyword);

	try {
	  uri = new URI(HTTP_PROTOCOL, null, YAHOO_SERVER_NAME, -1, YAHOO_RELATIVE_PATH, getQueryString(params), null);
	} catch (URISyntaxException ex) {
	  logger.severe(ex.toString());
	}

	return uri;
  }

  @Override
  protected List<Content> getContents(String xmlString) {
	return getContentsFromRss(xmlString, DefaultSource.YAHOO);
  }

  // private constructor to prevent instantiation
  private YahooNewsSearchManager() {
  }
}
