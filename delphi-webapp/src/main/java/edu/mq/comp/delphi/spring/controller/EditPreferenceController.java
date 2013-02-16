package edu.mq.comp.delphi.spring.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.mq.comp.delphi.model.DelphiUser;
import edu.mq.comp.delphi.model.Source;
import edu.mq.comp.delphi.model.Tag;
import edu.mq.comp.delphi.model.UserPreference;
import edu.mq.comp.delphi.model.presentation.PresentationDelphiUser;
import edu.mq.comp.delphi.model.presentation.PresentationSource;
import edu.mq.comp.delphi.model.presentation.PresentationTag;
import edu.mq.comp.delphi.util.PMFService;

@Controller
@RequestMapping("/preference/edit")
public class EditPreferenceController {
  private static final String PREDEFINED_SOURCE_NAMES = "bing flickr googleNews guardian twitter yahooNews youtube";

  private final Logger logger = Logger.getLogger(EditPreferenceController.class.getName());

  @RequestMapping(value = "form", method = RequestMethod.GET)
  public String showEditForm() {
	return "editPreference";
  }

  @RequestMapping(value = "save", method = RequestMethod.POST)
  public String save(@RequestParam("frequency") int frequency, @RequestParam("maxPerTag") int maxPerTag,
	  @RequestParam(value = "notificationEnabled", defaultValue = "false") boolean notificationEnabled,
	  @RequestParam(value = "mobileNumber", defaultValue = "") String mobileNumber,
	  @RequestParam("notificationType") int notificationType, HttpSession session) {
	PresentationDelphiUser presentationUser = (PresentationDelphiUser) session.getAttribute("presentationUser");
	
	if (presentationUser == null) {
	  return "editTagListFailure";
	}
	
	String email = presentationUser.getEmail();
	PersistenceManagerFactory pmf = PMFService.getInstance().getPersistenceManagerFactory();
	PersistenceManager manager = pmf.getPersistenceManager();
	Query query = manager.newQuery(DelphiUser.class, String.format("this.email == '%s'", email));

	try {
	  @SuppressWarnings("unchecked")
	  List<DelphiUser> users = (List<DelphiUser>) query.execute();

	  if (users.size() > 0) {
		DelphiUser user = users.get(0);
		UserPreference preference = user.getUserPreference();
		preference.setFrequency(frequency);
		preference.setMaxPerTag(maxPerTag);
		preference.setMobileNumber(mobileNumber);
		preference.setNotificationEnabled(notificationEnabled);
		preference.setNotificationType(notificationType);

		manager.makePersistent(preference);
		logger.info("preferences have been saved");

		// don't forget to replace the session attribute !!
		PresentationDelphiUser pdUser = new PresentationDelphiUser(user);
		session.setAttribute("presentationUser", pdUser);
		logger.info("session attribute has been replaced");
	  }
	} finally {
	  query.closeAll();
	  manager.close();
	}

	return "editSuccess";
  }

  @RequestMapping(value = "/tags/form", method = RequestMethod.GET)
  public String showTagListEditForm(HttpServletRequest request, HttpSession session) {
	PresentationDelphiUser presentationUser = (PresentationDelphiUser) session.getAttribute("presentationUser");

	if (presentationUser != null) {
	  Set<PresentationTag> tagSet = presentationUser.getUserPreference().getTags();
	  StringBuilder sb = new StringBuilder();

	  for (PresentationTag tag : tagSet) {
		sb.append(tag.getKeyword()).append("|");
	  }

	  String selectedTagNames = sb.toString();

	  if (selectedTagNames.length() > 0) {
		selectedTagNames = selectedTagNames.substring(0, selectedTagNames.length() - 1);
	  }

	  request.setAttribute("selectedTagNames", selectedTagNames);
	}

	return "editTagListForm";
  }

  @RequestMapping(value = "/tags/add", method = RequestMethod.POST)
  public String addTag(@RequestParam("tag") String tagName, HttpServletRequest request, HttpSession session) {
	if (StringUtils.isBlank(tagName)) {
	  request.setAttribute("errorMessage", "tag cannot be empty");
	  return "editTagListFailure";
	}

	tagName = tagName.toLowerCase().trim();
	PresentationDelphiUser presentationUser = (PresentationDelphiUser) session.getAttribute("presentationUser");
	
	if (presentationUser == null) {
	  return "editTagListFailure";
	}
	
	String email = presentationUser.getEmail();
	PersistenceManagerFactory pmf = PMFService.getInstance().getPersistenceManagerFactory();
	PersistenceManager manager = pmf.getPersistenceManager();
	Query query = manager.newQuery(DelphiUser.class, String.format("this.email == '%s'", email));

	try {
	  @SuppressWarnings("unchecked")
	  List<DelphiUser> users = (List<DelphiUser>) query.execute();

	  if (users.size() > 0) {
		DelphiUser user = users.get(0);
		UserPreference preference = user.getUserPreference();
		Set<Tag> tags = preference.getTags();

		for (Tag t : tags) {
		  logger.info("\n" + t.getKeyword());
		  if (t.getKeyword().equalsIgnoreCase(tagName)) {
			request.setAttribute("errorMessage", String.format("tag %s is already in your tag list", tagName));
			return "editTagListFailure";
		  }
		}

		Tag tag = new Tag();
		tag.setKeyword(tagName);
		preference.addTag(tag);
		
		manager.makePersistent(preference);
		logger.info("preferences have been saved");

		// don't forget to replace the session attribute !!
		PresentationDelphiUser pdUser = new PresentationDelphiUser(user);
		session.setAttribute("presentationUser", pdUser);
		logger.info("session attribute has been replaced");
	  }
	} finally {
	  query.closeAll();
	  manager.close();
	}

	return "editTagListSuccess";
  }

  @RequestMapping(value = "/tags/remove", method = RequestMethod.POST)
  public String removeTag(@RequestParam("tagName") String tagName, HttpServletRequest request, HttpSession session) {
	if (StringUtils.isBlank(tagName)) {
	  request.setAttribute("errorMessage", "no tag name specified");
	  return "editTagListFailure";
	}
	
	tagName = tagName.toLowerCase().trim();
	
	PresentationDelphiUser presentationUser = (PresentationDelphiUser) session.getAttribute("presentationUser");
	
	if (presentationUser == null) {
	  return "editTagListFailure";
	}
	
	String email = presentationUser.getEmail();
	PersistenceManagerFactory pmf = PMFService.getInstance().getPersistenceManagerFactory();
	PersistenceManager manager = pmf.getPersistenceManager();
	Query query = manager.newQuery(DelphiUser.class, String.format("this.email == '%s'", email));

	try {
	  @SuppressWarnings("unchecked")
	  List<DelphiUser> users = (List<DelphiUser>) query.execute();

	  if (users.size() > 0) {
		DelphiUser user = users.get(0);
		UserPreference preference = user.getUserPreference();
		
		Set<Tag> tagSet = preference.getTags();
		
		for (Tag tag : tagSet) {
		  if (tag.getKeyword().equals(tagName)) {
			tagSet.remove(tag);
			break;
		  }
		}
		
		manager.makePersistent(preference);
		logger.info("preferences have been saved");

		// don't forget to replace the session attribute !!
		PresentationDelphiUser pdUser = new PresentationDelphiUser(user);
		session.setAttribute("presentationUser", pdUser);
		logger.info("session attribute has been replaced");
	  }
	} finally {
	  query.closeAll();
	  manager.close();
	}
	
	return "editTagListSuccess";
  }

  @RequestMapping(value = "/sources/form", method = RequestMethod.GET)
  public String showSourceListEditForm(HttpServletRequest request, HttpSession session) {
	PresentationDelphiUser presentationUser = (PresentationDelphiUser) session.getAttribute("presentationUser");

	request.setAttribute("predefinedSourceNames", PREDEFINED_SOURCE_NAMES);

	if (presentationUser != null) {
	  Set<PresentationSource> sources = presentationUser.getUserPreference().getSources();

	  StringBuilder sb = new StringBuilder();

	  for (PresentationSource source : sources) {
		sb.append(source.getName()).append(" ");
	  }

	  String selectedSourceNames = sb.toString();

	  if (selectedSourceNames.length() > 0) {
		selectedSourceNames = selectedSourceNames.substring(0, selectedSourceNames.length() - 1);
	  }

	  request.setAttribute("selectedSourceNames", selectedSourceNames);
	}

	return "editSourceListForm";
  }

  @RequestMapping(value = "/sources/save", method = RequestMethod.POST)
  public String saveSourceList(HttpServletRequest request, HttpSession session) {
	String[] sourceNames = request.getParameterValues("sourceName");
	
	if (sourceNames == null) {
	  sourceNames = new String[0];
	}
	
	List<String> sourceNameList = new ArrayList<String>();
	
	for (String s : sourceNames) {
	  sourceNameList.add(s);
	}
	
	PresentationDelphiUser presentationUser = (PresentationDelphiUser) session.getAttribute("presentationUser");
	String email = presentationUser.getEmail();
	PersistenceManagerFactory pmf = PMFService.getInstance().getPersistenceManagerFactory();
	PersistenceManager manager = pmf.getPersistenceManager();
	Query query = manager.newQuery(DelphiUser.class, String.format("this.email == '%s'", email));

	try {
	  @SuppressWarnings("unchecked")
	  List<DelphiUser> users = (List<DelphiUser>) query.execute();

	  if (users.size() > 0) {
		DelphiUser user = users.get(0);
		UserPreference preference = user.getUserPreference();
		
		// phase 1 
		Set<Source> currentSourceSet = preference.getSources();
		
		for (Source s : currentSourceSet) {		  
		  if (!sourceNameList.contains(s)) {
			currentSourceSet.remove(s);
		  } else {
			sourceNameList.remove(s);
		  }
		}
		
		// add the new sources
		for (String s : sourceNameList) {
		  Source source = new Source();
		  source.setName(s);
		  currentSourceSet.add(source);
		}
		
		logger.info("the final source set : " + currentSourceSet);
		
		manager.makePersistent(preference);
		logger.info("preferences have been saved");

		// don't forget to replace the session attribute !!
		PresentationDelphiUser pdUser = new PresentationDelphiUser(user);
		session.setAttribute("presentationUser", pdUser);
		logger.info("session attribute has been replaced");
	  }
	} finally {
	  query.closeAll();
	  manager.close();
	}

	return "editSourceListSuccess";
  }
}
