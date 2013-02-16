package edu.mq.comp.delphi.util;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import edu.mq.comp.delphi.content.model.Content;
import edu.mq.comp.delphi.model.Source;
import edu.mq.comp.delphi.model.Tag;
import edu.mq.comp.delphi.model.UserPreference;
import edu.mq.comp.delphi.search.manager.BingNewsSearchManager;
import edu.mq.comp.delphi.search.manager.FlickrSearchManager;
import edu.mq.comp.delphi.search.manager.GoogleNewsSearchManager;
import edu.mq.comp.delphi.search.manager.GuardianSearchManager;
import edu.mq.comp.delphi.search.manager.TwitterSearchManager;
import edu.mq.comp.delphi.search.manager.YahooNewsSearchManager;
import edu.mq.comp.delphi.search.manager.YouTubeSearchManager;

public class ContentFetchUtil {
  public static Map<String, List<Content>> getContentListMap(UserPreference userPreference) {
	if (userPreference == null || !userPreference.isNotificationEnabled() || userPreference.getTags() == null
		|| userPreference.getSources() == null) {
	  return Collections.emptyMap();
	}

	int maxPerTag = userPreference.getMaxPerTag();
	Map<String, List<Content>> contentMap = new LinkedHashMap<String, List<Content>>();

	for (Tag tag : userPreference.getTags()) {
	  String keyword = tag.getKeyword();

	  for (Source source : userPreference.getSources()) {
		List<Content> list;

		if (source.getName().equalsIgnoreCase(DefaultSource.BING.getName())) {
		  list = BingNewsSearchManager.getInstance().search(keyword, maxPerTag);
		} else if (source.getName().equalsIgnoreCase(DefaultSource.FLICKR.getName())) {
		  list = FlickrSearchManager.getInstance().search(keyword, maxPerTag);
		} else if (source.getName().equalsIgnoreCase(DefaultSource.GOOGLE_NEWS.getName())) {
		  list = GoogleNewsSearchManager.getInstance().search(keyword, maxPerTag);
		} else if (source.getName().equalsIgnoreCase(DefaultSource.GUARDIAN.getName())) {
		  list = GuardianSearchManager.getInstance().search(keyword, maxPerTag);
		} else if (source.getName().equalsIgnoreCase(DefaultSource.TWITTER.getName())) {
		  list = TwitterSearchManager.getInstance().search(keyword, maxPerTag);
		} else if (source.getName().equalsIgnoreCase(DefaultSource.YAHOO.getName())) {
		  list = YahooNewsSearchManager.getInstance().search(keyword, maxPerTag);
		} else if (source.getName().equalsIgnoreCase(DefaultSource.YOUTUBE.getName())) {
		  list = YouTubeSearchManager.getInstance().search(keyword, maxPerTag);
		} else {
		  list = Collections.emptyList();
		}

		List<Content> previousList = contentMap.get(keyword);

		if (previousList == null) {
		  contentMap.put(keyword, list);
		} else {
		  previousList.addAll(list);
		  contentMap.put(keyword, previousList);
		}
	  }
	}

	return contentMap;
  }

  public static List<Content> getContents(String sourceName, String keyword, int max) {
	List<Content> list = Collections.emptyList();

	if (StringUtils.isBlank(sourceName) || StringUtils.isBlank(keyword)) {
	  return list;
	}

	if (sourceName.equalsIgnoreCase(DefaultSource.BING.getName())) {
	  list = BingNewsSearchManager.getInstance().search(keyword, max);
	} else if (sourceName.equalsIgnoreCase(DefaultSource.FLICKR.getName())) {
	  list = FlickrSearchManager.getInstance().search(keyword, max);
	} else if (sourceName.equalsIgnoreCase(DefaultSource.GOOGLE_NEWS.getName())) {
	  list = GoogleNewsSearchManager.getInstance().search(keyword, max);
	} else if (sourceName.equalsIgnoreCase(DefaultSource.GUARDIAN.getName())) {
	  list = GuardianSearchManager.getInstance().search(keyword, max);
	} else if (sourceName.equalsIgnoreCase(DefaultSource.TWITTER.getName())) {
	  list = TwitterSearchManager.getInstance().search(keyword, max);
	} else if (sourceName.equalsIgnoreCase(DefaultSource.YAHOO.getName())) {
	  list = YahooNewsSearchManager.getInstance().search(keyword, max);
	} else if (sourceName.equalsIgnoreCase(DefaultSource.YOUTUBE.getName())) {
	  list = YouTubeSearchManager.getInstance().search(keyword, max);
	}

	return list;
  }

  private ContentFetchUtil() {
  }
}
