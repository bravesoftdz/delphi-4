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
import edu.mq.comp.delphi.content.model.YouTubeVideo;
import edu.mq.comp.delphi.util.DateConverter;
import edu.mq.comp.delphi.util.DefaultSource;
import edu.mq.comp.delphi.xml.XmlUtil;

public class YouTubeSearchManager extends SearchManager {
  private static final YouTubeSearchManager INSTANCE = new YouTubeSearchManager();
  private static final String HTTP_PROTOCOL = "http";
  private static final String YOUTUBE_SERVER_NAME = "gdata.youtube.com";
  private static final String YOUTUBE_RELATIVE_PATH = "/feeds/api/videos";
  private static final Namespace ATOM_NAMESPACE = Namespace.getNamespace("a", "http://www.w3.org/2005/Atom");
  private final Logger logger = Logger.getLogger(YouTubeSearchManager.class.getName());

  public static YouTubeSearchManager getInstance() {
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
	params.put("orderby", "published");
	params.put("start-index", Integer.toString(1));
	params.put("max-results", Integer.toString(maxPerTag));

	try {
	  uri = new URI(HTTP_PROTOCOL, null, YOUTUBE_SERVER_NAME, -1, YOUTUBE_RELATIVE_PATH, getQueryString(params), null);
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

	List<Content> contents = new ArrayList<Content>();

	try {
	  List<Element> entries = XmlUtil.getValueAsList("/a:feed/a:entry", doc, ATOM_NAMESPACE);

	  for (Element entry : entries) {
		String title = XmlUtil.getValue("a:title[@type='text']", entry, ATOM_NAMESPACE);
		String url = XmlUtil.getValue("a:link[@rel='alternate']/@href", entry, ATOM_NAMESPACE);
		String publishDate = XmlUtil.getValue("a:published", entry, ATOM_NAMESPACE);
		String idUrl = XmlUtil.getValue("a:id", entry, ATOM_NAMESPACE);
		String id = idUrl.substring(idUrl.lastIndexOf("/") + 1);
		String authorName = XmlUtil.getValue("a:author/a:name", entry, ATOM_NAMESPACE);
		String authorUrl = XmlUtil.getValue("a:author/a:uri", entry, ATOM_NAMESPACE);
		  
		YouTubeVideo content = new YouTubeVideo(title, url);
		content.setPublishDate(DateConverter.convertDate(publishDate, DefaultSource.YOUTUBE));
		content.setId(id);
		content.setAuthorName(authorName);
		content.setAuthorUrl(authorUrl);
		
		contents.add(content);
	  }
	} catch (JDOMException ex) {
	  logger.severe(ex.toString());
	}

	return contents;
  }

  // private constructor to prevent instantiation
  private YouTubeSearchManager() {
  }
}
