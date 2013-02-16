package edu.mq.comp.delphi.spring.controller;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.appengine.api.users.User;

import edu.mq.comp.delphi.auth.AuthenticationManager;
import edu.mq.comp.delphi.model.DelphiUser;
import edu.mq.comp.delphi.model.presentation.PresentationDelphiUser;
import edu.mq.comp.delphi.util.EntityUtil;
import edu.mq.comp.delphi.util.PMFService;

@Controller
public class MiscController {

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String homePage() {
	return "index";
  }

  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
	AuthenticationManager authenticationManager = AuthenticationManager.getInstance();
	User googleUser = authenticationManager.getLoggedInUser();

	if (googleUser != null) {
	  String email = googleUser.getEmail();
	  PersistenceManagerFactory pmf = PMFService.getInstance().getPersistenceManagerFactory();
	  PersistenceManager manager = pmf.getPersistenceManager();

	  Query query = manager.newQuery(DelphiUser.class, String.format("this.email == '%s'", email));
	  DelphiUser delphiUser = null;

	  try {
		@SuppressWarnings("unchecked")
		List<DelphiUser> users = (List<DelphiUser>) query.execute();

		if (users != null && users.size() > 0) {
		  delphiUser = users.get(0);
		}

		if (delphiUser == null) {
		  String nickName = googleUser.getNickname();
		  delphiUser = EntityUtil.createNewUser(email, nickName);
		  delphiUser = manager.makePersistent(delphiUser);
		}

		PresentationDelphiUser pdUser = new PresentationDelphiUser(delphiUser);
		request.getSession().setAttribute("presentationUser", pdUser);
	  } finally {
		query.closeAll();
		manager.close();
	  }

	  response.sendRedirect("/");
	} else {
	  response.sendRedirect(authenticationManager.getLoginUrl(request.getRequestURI()));
	}
  }
  
  @RequestMapping(value = "/logout", method = RequestMethod.GET)
  public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
	request.getSession().removeAttribute("presentationUser");
	response.sendRedirect("/");
  }
}
