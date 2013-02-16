package edu.mq.comp.delphi.xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import edu.mq.comp.delphi.content.model.Content;
import edu.mq.comp.delphi.util.MessageType;

public class Converter {
  private static final Logger logger = Logger.getLogger(Converter.class.getName());

  public static String contentsToXml(Map<String, List<Content>> contentListMap) {
	String result = "";

	if (contentListMap == null) {
	  return result;
	}

	Document document = new Document();
	Element root = new Element("results");
	document.setRootElement(root);

	for (Map.Entry<String, List<Content>> entry : contentListMap.entrySet()) {
	  String keyword = entry.getKey();
	  Element group = new Element("group");
	  group.setAttribute("tag", keyword);
	  root.addContent(group);

	  List<Content> contents = entry.getValue();

	  for (Content content : contents) {
		Element contElement = new Element("content");

		Element title = new Element("title");
		title.addContent(content.getTitle());

		Element url = new Element("url");
		url.addContent(content.getUrl());

		contElement.addContent(title);
		contElement.addContent(url);
		group.addContent(contElement);
	  }
	}

	try {
	  ByteArrayOutputStream outStream = new ByteArrayOutputStream();
	  XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
	  outputter.output(document, outStream);
	  result = outStream.toString();
	} catch (IOException ex) {
	  logger.severe(ex.toString());
	}

	return result;
  }

  public static String xmlToHtml(String inputXml, MessageType type) {
	String result = "";

	switch (type) {
	  case EMAIL:
		result = getEmail(inputXml);
		break;
	  case SMS:
		result = getSms(inputXml);
		break;
	  default:
		throw new RuntimeException("unknown message type");
	}

	return result;
  }

  private static String getEmail(String inputXml) {
	StringBuilder htmlBuilder = new StringBuilder();

	if (StringUtils.isBlank(inputXml)) {
	  return htmlBuilder.toString();
	}

	Document doc = getDocument(inputXml);

	if (doc == null) {
	  return htmlBuilder.toString();
	}

	htmlBuilder.append("<html><body>");
	htmlBuilder.append("<h2>Content Links from Delphi</h2>");

	try {
	  List<Element> groupList = XmlUtil.getValueAsList("/results/group", doc);
	  
	  for (Element group : groupList) {
		String tag = group.getAttributeValue("tag");
		htmlBuilder.append(String.format("<div><h3>Keyword : %s</h3><ul>", tag));
		
		List<Element> contentList = XmlUtil.getValueAsList("content", group);
		
		for (Element content : contentList) {
		  String title = XmlUtil.getValue("title", content);
		  String url = XmlUtil.getValue("url", content);
		  htmlBuilder.append(String.format("<li><a href='%s'>%s</a></li>", url, title));
		}
		
		htmlBuilder.append("</ul></div>");
	  }
	} catch (JDOMException ex) {
	  logger.severe(ex.toString());
	}

	htmlBuilder.append("</body></html>");

	return htmlBuilder.toString();
  }

  private static String getSms(String inputXml) {
	StringBuilder smsBuilder = new StringBuilder();

	if (StringUtils.isBlank(inputXml)) {
	  return smsBuilder.toString();
	}

	Document doc = getDocument(inputXml);

	if (doc == null) {
	  return smsBuilder.toString();
	}
	
	try {
	  List<Element> groupList = XmlUtil.getValueAsList("/results/group", doc);
	  
	  for (Element group : groupList) {		
		List<Element> contentList = XmlUtil.getValueAsList("content", group);
		
		for (Element content : contentList) {
		  String title = XmlUtil.getValue("title", content);
		  smsBuilder.append(title).append("\n");
		}		
	  }
	} catch (JDOMException ex) {
	  logger.severe(ex.toString());
	}

	return smsBuilder.toString();
  }

  private static Document getDocument(String inputXml) {
	Document doc = null;

	if (StringUtils.isBlank(inputXml)) {
	  return doc;
	}

	try {
	  SAXBuilder builder = new SAXBuilder();
	  InputStream in = new ByteArrayInputStream(inputXml.getBytes());
	  doc = builder.build(in);
	} catch (JDOMException ex) {
	  logger.severe(ex.toString());
	} catch (IOException ex) {
	  logger.severe(ex.toString());
	}

	return doc;
  }

  private Converter() {
  }
}
