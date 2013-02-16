package edu.mq.comp.delphi.search.manager;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;

import edu.mq.comp.delphi.content.model.Content;
import edu.mq.comp.delphi.content.model.Twitter;
import edu.mq.comp.delphi.util.DateConverter;
import edu.mq.comp.delphi.util.DefaultSource;
import edu.mq.comp.delphi.xml.XmlUtil;

public class TwitterSearchManager extends SearchManager {
  private static final TwitterSearchManager INSTANCE = new TwitterSearchManager();
  private static final String HTTP_PROTOCOL = "http";
  private static final String TWITTER_SERVER_NAME = "search.twitter.com";
  private static final String TWITTER_RELATIVE_PATH = "/search.atom";
  private static final Namespace ATOM_NAMESPACE = Namespace.getNamespace("a", "http://www.w3.org/2005/Atom");
  private final Logger logger = Logger.getLogger(TwitterSearchManager.class.toString());

  public static TwitterSearchManager getInstance() {
	return INSTANCE;
  }

  @Override
  protected URI buildUri(String keyword, int maxPerTag) {
	URI uri = null;
	
	if (StringUtils.isBlank(keyword) || maxPerTag <= 0) {
	  return null;
	}

	Map<String, String> params = new HashMap<String, String>();
	params.put("q", keyword);
	params.put("result-type", "mixed");
	params.put("rpp", Integer.toString(maxPerTag));
	params.put("lang", "en");

	try {
	  uri = new URI(HTTP_PROTOCOL, null, TWITTER_SERVER_NAME, -1, TWITTER_RELATIVE_PATH, getQueryString(params), null);
	  logger.info(uri.toString());
	} catch (URISyntaxException ex) {
	  logger.severe(ex.toString());
	}

	return uri;
  }

  @Override
  protected List<Content> getContents(String xmlString) {
	if (StringUtils.isBlank(xmlString)) {
	  return Collections.emptyList();
	}

	Document doc = parseXml(xmlString);

	if (doc == null) {
	  return Collections.emptyList();
	}

	List<Content> contentList = new ArrayList<Content>();

	try {
	  List<Element> entryList = XmlUtil.getValueAsList("/a:feed/a:entry", doc.getRootElement(), ATOM_NAMESPACE);

	  for (Element entry : entryList) {
		String title = XmlUtil.getValue("a:title", entry, ATOM_NAMESPACE);
		String url = XmlUtil.getValue("a:link[@rel='alternate']/@href", entry, ATOM_NAMESPACE);
		String publishDate = XmlUtil.getValue("a:published", entry, ATOM_NAMESPACE);
		String authorName = XmlUtil.getValue("a:author/a:name", entry, ATOM_NAMESPACE);
		String authorUrl = XmlUtil.getValue("a:author/a:uri", entry, ATOM_NAMESPACE);
		String authorPicUrl = XmlUtil.getValue("a:link[@rel='image']/@href", entry, ATOM_NAMESPACE);
		
		Twitter content = new Twitter(title, url);
		content.setPublishDate(DateConverter.convertDate(publishDate, DefaultSource.TWITTER));
		content.setAuthorName(authorName);
		content.setAuthorUrl(authorUrl);
		content.setAuthorPicUrl(authorPicUrl);
		
		contentList.add(content);
	  }

	} catch (JDOMException ex) {
	  logger.severe(ex.toString());
	}

	return contentList;
  }

  // private constructor to prevent instantiation
  private TwitterSearchManager() {
  }
}
