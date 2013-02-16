package edu.mq.comp.delphi.search.manager;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import edu.mq.comp.delphi.content.model.Content;
import edu.mq.comp.delphi.util.DefaultSource;

public class GoogleNewsSearchManager extends SearchManager {
  private static final GoogleNewsSearchManager INSTANCE = new GoogleNewsSearchManager();
  private static final String HTTP_PROTOCOL = "http";
  private static final String GOOGLE_NEWS_SERVER = "news.google.com";
  private static final String GOOGLE_NEWS_PATH = "/news/feeds";
  private final Logger logger = Logger.getLogger(GoogleNewsSearchManager.class.getName());
  
  public static GoogleNewsSearchManager getInstance() {
	return INSTANCE;
  }
  
  @Override
  protected URI buildUri(String keyword, int maxPerTag) {
	URI uri = null;
	
	if (StringUtils.isBlank(keyword) || maxPerTag <= 0) {
	  return uri;
	}
	
	Map<String, String> params = new HashMap<String, String>();
	params.put("q", keyword);
	params.put("output", "rss");
	
	try {
	  uri = new URI(HTTP_PROTOCOL, null, GOOGLE_NEWS_SERVER, -1, GOOGLE_NEWS_PATH, getQueryString(params), null);
	} catch (URISyntaxException ex) {
	  logger.severe(ex.toString());
	}
	
	return uri;
  }

  @Override
  protected List<Content> getContents(String xmlString) {
	List<Content> contents = getContentsFromRss(xmlString, DefaultSource.GOOGLE_NEWS);
	
	for (Content content : contents) {
	  content.setUrl(StringEscapeUtils.unescapeHtml4(content.getUrl()));
	}
	
	return contents;
  }

  // private constructor to prevent instantiation
  private GoogleNewsSearchManager() {
  }
}
