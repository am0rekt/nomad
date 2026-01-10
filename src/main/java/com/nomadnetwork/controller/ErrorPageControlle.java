package com.nomadnetwork.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class ErrorPageControlle {

	@GetMapping("/access-denied")
	    public String accessDenied() {
	        return "error/access-denied";
	    }
}
