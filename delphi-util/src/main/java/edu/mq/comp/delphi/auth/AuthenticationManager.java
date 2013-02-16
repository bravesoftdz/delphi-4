package edu.mq.comp.delphi.auth;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class AuthenticationManager {
  private static final AuthenticationManager INSTANCE = new AuthenticationManager();
  private UserService userService = UserServiceFactory.getUserService();
  
  public static AuthenticationManager getInstance() {
	return INSTANCE;
  }
  
  public User getLoggedInUser() {
	return userService.getCurrentUser();
  }
  
  public String getLoginUrl(String destinationUrl) {
	return userService.createLoginURL(destinationUrl);
  }
  
  public String getLogoutUrl(String destinationUrl) {
	return userService.createLogoutURL(destinationUrl);
  }
  
  // private constructor to prevent instantiation
  private AuthenticationManager() {
  }
}
