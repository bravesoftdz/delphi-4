package edu.mq.comp.delphi.tag;

import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang3.StringUtils;

import com.google.appengine.api.users.User;

import edu.mq.comp.delphi.auth.AuthenticationManager;

public class GetUserTag extends SimpleTagSupport {
  private String var;

  public String getVar() {
	return var;
  }

  public void setVar(String var) {
	if (StringUtils.isBlank(var)) {
	  throw new IllegalArgumentException("var cannot be empty");
	}

	this.var = var;
  }

  @Override
  public void doTag() {
	User user = AuthenticationManager.getInstance().getLoggedInUser();
	getJspContext().setAttribute(var, user);
  }
}
