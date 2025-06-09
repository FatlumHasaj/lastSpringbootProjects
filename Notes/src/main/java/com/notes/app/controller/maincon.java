package com.notes.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class maincon {
	
	@GetMapping("/index")
	public String getMethodName() {
		return "index";
	}
	
	@GetMapping("/login")
	public String getMethodName1() {
		return "login";
	}
}
