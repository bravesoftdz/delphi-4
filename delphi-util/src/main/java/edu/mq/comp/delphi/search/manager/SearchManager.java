package edu.mq.comp.delphi.search.manager;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import edu.mq.comp.delphi.content.model.Content;
import edu.mq.comp.delphi.content.model.RssNews;
import edu.mq.comp.delphi.http.HttpClient;
import edu.mq.comp.delphi.util.DateConverter;
import edu.mq.comp.delphi.util.DefaultSource;
import edu.mq.comp.delphi.xml.XmlUtil;

public abstract class SearchManager {
  private final Logger logger = Logger.getLogger(SearchManager.class.getName());

  public List<Content> search(String keyword, int maxPerTag) {
	List<Content> resultList = Collections.emptyList();

	if (StringUtils.isBlank(keyword) || maxPerTag <= 0) {
	  return resultList;
	}

	URI uri = buildUri(keyword, maxPerTag);
	logger.info("The uri is " + uri);

	if (uri == null) {
	  return resultList;
	}

	String resultXml = HttpClient.getInstance().doGet(uri.toString());
	logger.info(resultXml);
	return getFilteredContents(resultXml, maxPerTag);
  }

  protected Document parseXml(String xmlString) {
	Document result = null;

	if (StringUtils.isBlank(xmlString)) {
	  return result;
	}

	try {
	  SAXBuilder builder = new SAXBuilder();
	  InputStream in = new ByteArrayInputStream(xmlString.getBytes());
	  result = builder.build(in);
	} catch (JDOMException ex) {
	  logger.severe(ex.toString());
	} catch (IOException ex) {
	  logger.severe(ex.toString());
	}

	return result;
  }

  protected String getQueryString(Map<String, String> params) {
	StringBuilder builder = new StringBuilder();

	for (Iterator<Map.Entry<String, String>> iter = params.entrySet().iterator(); iter.hasNext();) {
	  Map.Entry<String, String> entry = iter.next();

	  builder.append(entry.getKey()).append("=").append(entry.getValue());

	  if (iter.hasNext()) {
		builder.append("&");
	  }
	}

	return builder.toString();
  }

  protected abstract URI buildUri(String keyword, int maxPerTag);

  protected abstract List<Content> getContents(String xmlString);
  
  protected  List<Content> getFilteredContents(String xmlString, int maxPerTag) {
	List<Content> contents = getContents(xmlString);

	if (maxPerTag < 0 || maxPerTag >= contents.size()) {
	  return contents;
	}
	
	return new ArrayList<Content>(contents.subList(0, maxPerTag));
  }
  
  protected final List<Content> getContentsFromRss(String xmlString, DefaultSource source) {
	if (StringUtils.isBlank(xmlString)) {
	  return Collections.emptyList();
	}
	
	Document doc = parseXml(xmlString);
	
	if (doc == null) {
	  return Collections.emptyList();
	}
	
	List<Content> contents = new ArrayList<Content>();
	
	try {
	  List<Element> items = XmlUtil.getValueAsList("/rss/channel/item", doc);
	  
	  for (Element item : items) {
		String title = StringEscapeUtils.unescapeHtml4(XmlUtil.getValue("title/text()", item));
		String url = XmlUtil.getValue("link/text()", item);
		String publishDate = XmlUtil.getValue("pubDate/text()", item);
		String description = XmlUtil.getValue("description/text()", item);
		
		RssNews content = new RssNews(title, url);
		content.setPublishDate(DateConverter.convertDate(publishDate, source));
		content.setDescription(description);
		
		contents.add(content);
	  }
	} catch (JDOMException ex) {
	  logger.severe(ex.toString());
	}
	
	return contents;
  }
}
