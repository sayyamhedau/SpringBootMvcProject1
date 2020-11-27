package com.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	@GetMapping(value = "/")
	public String getHomePage() {
		return "Home";
	}

	@GetMapping(value = "/login")
	public String viewloginPage() {
		return "LoginPage";
	}

	@GetMapping(value = "/error")
	public String viewErrorPage() {
		return "ErrorPage";
	}
}
