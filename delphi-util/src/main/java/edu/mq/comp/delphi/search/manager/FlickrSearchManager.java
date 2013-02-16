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
import edu.mq.comp.delphi.xml.XmlUtil;

public class FlickrSearchManager extends SearchManager {
  private static final FlickrSearchManager INSTANCE = new FlickrSearchManager();
  private static final String HTTP_PROTOCOL = "http";
  private static final String FLICKR_SERVER_NAME = "api.flickr.com";
  private static final String FLICKR_RELATIVE_PATH = "/services/rest";
  private static final String FLICKR_API_KEY = "9f47d14d2bd1e3e9141d7f2948f4a189";
  private final Logger logger = Logger.getLogger(FlickrSearchManager.class.getName());
  
  public static FlickrSearchManager getInstance() {
	return INSTANCE;
  }
  
  @Override
  protected URI buildUri(String keyword, int maxPerTag) {
	URI uri = null;

    Map<String, String> params = new HashMap<String, String>();
	params.put("format", "rest");
	params.put("method", "flickr.photos.search");
	params.put("tags", keyword);
	params.put("api_key", FLICKR_API_KEY);
	params.put("per_page", Integer.toString(maxPerTag));

	try {
	  uri = new URI(HTTP_PROTOCOL, null, FLICKR_SERVER_NAME, -1, FLICKR_RELATIVE_PATH, getQueryString(params), null);
	} catch (URISyntaxException ex) {
	  System.err.println(ex);
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
	  List<Element> photos = XmlUtil.getValueAsList("/rsp/photos/photo", doc);
	  
	  for (Element photo : photos) {
		String title = XmlUtil.getValue("@title", photo);
		String farm = XmlUtil.getValue("@farm", photo);
		String server = XmlUtil.getValue("@server", photo);
		String id = XmlUtil.getValue("@id", photo);
		String secret = XmlUtil.getValue("@secret", photo);
		
		String url = String.format("http://farm%s.staticflickr.com/%s/%s_%s.jpg", farm, server, id, secret);
		Content content = new Content(title, url);
		contents.add(content);
	  }
	} catch (JDOMException ex) {
	  logger.severe(ex.toString());
	}
	
	return contents;
  }
  
  private FlickrSearchManager() {
  }
}
