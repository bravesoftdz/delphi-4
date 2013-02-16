package edu.mq.comp.delphi.tag;

import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang3.StringUtils;

import edu.mq.comp.delphi.auth.AuthenticationManager;

public class GetUrlTag extends SimpleTagSupport {
  private static final String LOGIN_TYPE = "login";
  private static final String LOGOUT_TYPE = "logout";
  private String var;
  private String type;
  private String destinationUrl;

  public String getVar() {
	return var;
  }

  public void setVar(String var) {
	if (StringUtils.isBlank(var)) {
	  throw new IllegalArgumentException("var cannot be blank");
	}

	this.var = var;
  }

  public String getType() {
	return type;
  }

  public void setType(String type) {
	if (StringUtils.isBlank(type)) {
	  throw new IllegalArgumentException("type cannot be blank");
	}

	this.type = type;
  }

  public String getDestinationUrl() {
	return destinationUrl;
  }

  public void setDestinationUrl(String destinationUrl) {
	if (StringUtils.isBlank(destinationUrl)) {
	  throw new IllegalArgumentException("destinationUrl cannot be blank");
	}

	this.destinationUrl = destinationUrl;
  }

  @Override
  public void doTag() {
	String url = "";

	if (type.equals(LOGIN_TYPE)) {
	  url = AuthenticationManager.getInstance().getLoginUrl(destinationUrl);
	} else if (type.equals(LOGOUT_TYPE)) {
	  url = AuthenticationManager.getInstance().getLogoutUrl(destinationUrl);
	} else {
	  throw new RuntimeException(String.format("unknown type : %s", type));
	}

	getJspContext().setAttribute(var, url);
  }
}
