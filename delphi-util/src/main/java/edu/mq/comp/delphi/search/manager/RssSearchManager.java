package edu.mq.comp.delphi.search.manager;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;

import com.sun.syndication.feed.synd.SyndCategory;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import edu.mq.comp.delphi.content.model.Content;

public class RssSearchManager {
  private static final RssSearchManager INSTANCE = new RssSearchManager();
  private final Logger logger = Logger.getLogger(RssSearchManager.class.getName());

  public static RssSearchManager getInstance() {
	return INSTANCE;
  }

  public List<Content> search(String urlString, String keyword, int maxPerTag) {
	if (StringUtils.isBlank(urlString) || StringUtils.isBlank(keyword) || maxPerTag <= 0) {
	  return Collections.emptyList();
	}
	
	List<Content> contents = new ArrayList<Content>();

	try {
	  URL feedUrl = new URL(urlString);
	  SyndFeedInput input = new SyndFeedInput();
	  SyndFeed feed = input.build(new XmlReader(feedUrl));

	  @SuppressWarnings("unchecked")
	  List<SyndEntry> entries = feed.getEntries();
	  int count = 0;

	  for (Iterator<SyndEntry> iter = entries.iterator(); iter.hasNext() && count < maxPerTag;) {
		SyndEntry entry = iter.next();

		@SuppressWarnings("unchecked")
		List<? extends SyndCategory> categories = entry.getCategories();

		for (SyndCategory category : categories) {
		  String categoryName = category.getName();

		  if (StringUtils.isNotBlank(categoryName) && StringUtils.containsIgnoreCase(categoryName, keyword)) {
			Content content = new Content(entry.getTitle(), entry.getUri());
			contents.add(content);
			count++;
			break;
		  }
		}
	  }
	} catch (IOException ex) {
	  logger.severe(ex.toString());
	} catch (FeedException ex) {
	  logger.severe(ex.toString());
	}

	return contents;
  }

  private RssSearchManager() {
  }
}
