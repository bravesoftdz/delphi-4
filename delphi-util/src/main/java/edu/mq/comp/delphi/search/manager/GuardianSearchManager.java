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

import edu.mq.comp.delphi.content.model.Content;
import edu.mq.comp.delphi.content.model.GuardianNews;
import edu.mq.comp.delphi.util.DateConverter;
import edu.mq.comp.delphi.util.DefaultSource;
import edu.mq.comp.delphi.xml.XmlUtil;

public class GuardianSearchManager extends SearchManager {
  private static final GuardianSearchManager INSTANCE = new GuardianSearchManager();
  private static final String GUARDIAN_SERVER_NAME = "content.guardianapis.com";
  private static final String GUARDIAN_RELATIVE_PATH = "/search";
  private static final String HTTP_PROTOCOL = "http";
  private final Logger logger = Logger.getLogger(GuardianSearchManager.class.getName());

  public static GuardianSearchManager getInstance() {
	return INSTANCE;
  }

  @Override
  protected URI buildUri(String keyword, int maxPerTag) {
	URI uri = null;

	Map<String, String> params = new HashMap<String, String>();
	params.put("q", keyword);
	params.put("format", "xml");
	params.put("page-size", Integer.toString(maxPerTag));
	
	try {
	  uri = new URI(HTTP_PROTOCOL, null, GUARDIAN_SERVER_NAME, -1, GUARDIAN_RELATIVE_PATH, getQueryString(params),
		  null);
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
	  List<Element> elementList = XmlUtil.getValueAsList("/response/results/content", doc.getRootElement());
	  
	  for (Element element : elementList) {
		String title = element.getAttributeValue("web-title");
		String url = element.getAttributeValue("web-url");
		String publishDate = element.getAttributeValue("web-publication-date");
		GuardianNews content = new GuardianNews(title, url);
		content.setPublishDate(DateConverter.convertDate(publishDate, DefaultSource.GUARDIAN));
		
		contentList.add(content);
	  }
	} catch (JDOMException ex) {
	  logger.severe(ex.toString());
	}
	
   return contentList;
  }

  // private constructor to prevent instantiation
  private GuardianSearchManager() {
  }
}
