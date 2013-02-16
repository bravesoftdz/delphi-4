package edu.mq.comp.delphi.spring.controller;

import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

import edu.mq.comp.delphi.model.DelphiUser;
import edu.mq.comp.delphi.model.UserPreference;

import edu.mq.comp.delphi.util.PMFService;

@Controller
@RequestMapping("/cron")
public class CronController {
  private static final String EMPTY_PAGE = "empty";
  private final Logger logger = Logger.getLogger(CronController.class.getName());

  @RequestMapping("/fetchContent/morning")
  public String fetchContentInMorning() {
	logger.info("Content fetch job called in the morning");
	fetch(1);
	return EMPTY_PAGE;
  }

  @RequestMapping("/fetchContent/noon")
  public String fetchContentAtNoon() {
	logger.info("Content fetch job called at noon");
	fetch(2);
	return EMPTY_PAGE;
  }

  @RequestMapping("/fetchContent/night")
  public String fetchContentAtNight() {
	logger.info("Content fetch job called at night");
	fetch(3);
	return EMPTY_PAGE;
  }

  @RequestMapping("/sendMessage/morning")
  public String sendMessageInMorning() {
	logger.info("message send job called in the morning");
	sendMessage(1);
	return EMPTY_PAGE;
  }

  @RequestMapping("/sendMessage/noon")
  public String sendMessageAtNoon() {
	logger.info("message send job called at noon");
	sendMessage(2);
	return EMPTY_PAGE;
  }

  @RequestMapping("/sendMessage/night")
  public String sendMessageAtNight() {
	logger.info("message send job called at night");
	sendMessage(3);
	return EMPTY_PAGE;
  }

  private void fetch(int whichTime) {
	PersistenceManagerFactory pmf = PMFService.getInstance().getPersistenceManagerFactory();
	PersistenceManager manager = pmf.getPersistenceManager();
	Query query = manager.newQuery(DelphiUser.class);
	Queue taskQueue = QueueFactory.getDefaultQueue();

	try {
	  @SuppressWarnings("unchecked")
	  List<DelphiUser> users = (List<DelphiUser>) query.execute();
	  
	  for (DelphiUser user : users) {
		UserPreference preference = user.getUserPreference();

		if (preference.isNotificationEnabled() && preference.getFrequency() >= whichTime) {
		  TaskOptions options = TaskOptions.Builder.withDefaults();
		  options = options.url("/task/fetchContentForUser").param("email", user.getEmail());
		  taskQueue.add(options);
		}
	  }
	} finally {
	  query.closeAll();
	  manager.close();
	}
  }

  private void sendMessage(int whichTime) {
	PersistenceManagerFactory pmf = PMFService.getInstance().getPersistenceManagerFactory();
	PersistenceManager manager = pmf.getPersistenceManager();
	Query query = manager.newQuery(DelphiUser.class);
	Queue queue = QueueFactory.getDefaultQueue();

	try {
	  @SuppressWarnings("unchecked")
	  List<DelphiUser> users = (List<DelphiUser>) query.execute();

	  for (DelphiUser user : users) {
		UserPreference preference = user.getUserPreference();

		if (preference.isNotificationEnabled() && preference.getFrequency() >= whichTime) {
		  TaskOptions options = TaskOptions.Builder.withDefaults();
		  queue.add(options.url("/task/sendMessageToUser").param("email", user.getEmail()));
		}
	  }
	} finally {
	  query.closeAll();
	  manager.close();
	}
  }
}
