package edu.mq.comp.delphi.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/preference/view")
public class ViewPreferenceController {
  @RequestMapping(method = RequestMethod.GET)
  public String viewPreference() {
	return "viewPreference";
  }
}
