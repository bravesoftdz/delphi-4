package edu.mq.comp.delphi.spring.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

import edu.mq.comp.delphi.content.model.Content;
import edu.mq.comp.delphi.model.presentation.PresentationDelphiUser;
import edu.mq.comp.delphi.model.presentation.PresentationUserPreference;
import edu.mq.comp.delphi.util.ContentFetchUtil;

@Controller
@RequestMapping("/search")
public class SearchController {
  private static final int EXPIRATION_TIME_IN_SECONDS = 1800;
  
  @RequestMapping("/form")
  public String searchForm() {
	return "searchForm";
  }
  
  @RequestMapping(value="/result", method = RequestMethod.GET)
  public String searchResult(@RequestParam("sourceName") String sourceName, @RequestParam("keyword") String keyword,
	                         HttpServletRequest request, HttpSession session) {
	if (StringUtils.isNotBlank(sourceName) || StringUtils.isNotBlank(keyword)) {
	  PresentationDelphiUser presentationUser = (PresentationDelphiUser) session.getAttribute("presentationUser");
	  
	  if (presentationUser != null) {
		PresentationUserPreference userPreference = presentationUser.getUserPreference();
		int max = userPreference.getMaxPerTag();
		List<Content> contents = ContentFetchUtil.getContents(sourceName, keyword, max);
		
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
		String key = String.format("%s-%s-%s", presentationUser.getEmail(), sourceName, keyword);
		syncCache.put(key, contents, Expiration.byDeltaSeconds(EXPIRATION_TIME_IN_SECONDS));
		request.setAttribute("source", sourceName);
		request.setAttribute("keyword", keyword);
		request.setAttribute("contents", contents);
	  }
	}
	
	return "searchResult";
  }
}
