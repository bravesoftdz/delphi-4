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

public class BingNewsSearchManager extends SearchManager {
  private static final BingNewsSearchManager INSTANCE = new BingNewsSearchManager();
  private static final String HTTP_PROTOCOL = "http";
  private static final String BING_SERVER_NAME = "api.bing.com";
  private static final String BING_SERVER_PATH = "/rss.aspx";
  private final Logger logger = Logger.getLogger(BingNewsSearchManager.class.getName());

  public static BingNewsSearchManager getInstance() {
	return INSTANCE;
  }

  @Override
  protected URI buildUri(String keyword, int maxPerTag) {
	URI uri = null;

	if (StringUtils.isBlank(keyword) || maxPerTag <= 0) {
	  return uri;
	}

	Map<String, String> params = new HashMap<String, String>();
	params.put("Source", "News");
	params.put("Market", "en-AU");
	params.put("Version", "2.0");
	params.put("Query", keyword);

	try {
	  uri = new URI(HTTP_PROTOCOL, null, BING_SERVER_NAME, -1, BING_SERVER_PATH, getQueryString(params), null);
	} catch (URISyntaxException ex) {
	  logger.severe(ex.toString());
	}

	return uri;
  }

  @Override
  protected List<Content> getContents(String xmlString) {
	return getContentsFromRss(xmlString, DefaultSource.BING);
  }

  // private constructor to prevent instantiation
  private BingNewsSearchManager() {
  }
}
