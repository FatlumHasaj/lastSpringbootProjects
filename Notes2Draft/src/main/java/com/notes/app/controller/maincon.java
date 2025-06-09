package com.notes.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class maincon {
	
	@GetMapping("/")
	public String root(HttpSession session) {
		// If user is already logged in (session exists), go to index, else go to login
		if (session.getAttribute("userId") != null) {
			return "redirect:/index";
		}
		return "redirect:/login";
	}
	
	@GetMapping("/index")
	public String getIndexPage(HttpSession session) {
		// Protect the index page: if not logged in, redirect to login
		if (session.getAttribute("userId") == null) {
			return "redirect:/login";
		}
		return "index";
	}
	
	@GetMapping("/login")
	public String getLoginPage() {
		return "login";
	}
	
	@GetMapping("/register")
	public String getRegisterPage() {
		return "register";
	}

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Clear the session
        return "redirect:/login";
    }
}