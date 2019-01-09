package com.chq.ssmshop.web.local;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/account")
public class LocalController {

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "/local/login";
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register() {
		return "/local/register";
	}

	@RequestMapping(value = "/modifypassword", method = RequestMethod.GET)
	public String modifyPassword() {
		return "/local/modifypassword";
	}
}
