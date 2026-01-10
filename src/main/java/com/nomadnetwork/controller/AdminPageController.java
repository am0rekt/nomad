package com.nomadnetwork.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminPageController {
	
	@GetMapping("/dashboard")
    public String adminDashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/users")
    public String manageUsers() {
        return "admin/users";
    }

    @GetMapping("/posts")
    public String managePosts() {
        return "admin/posts";
    }

    @GetMapping("/scams")
    public String manageScams() {
        return "admin/scams";
    }
}

