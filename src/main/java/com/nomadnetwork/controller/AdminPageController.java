package com.nomadnetwork.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nomadnetwork.services.AdminService;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminPageController {
	
	@Autowired
    private AdminService adminService;
	
	@GetMapping("/dashboard")
    public String adminDashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/users")
    public String users(Model model) {
    	model.addAttribute("users", adminService.getAllUsers());
        return "admin/users";
    }
    
    @PostMapping("/users/{id}/role")
    public String changeRole(@PathVariable Long id,
                             @RequestParam String role) {
        adminService.changeUserRole(id, role);
        return "redirect:/admin/users";
    }

    @GetMapping("/posts")
    public String posts(Model model) {
    	model.addAttribute("posts", adminService.getAllPosts());
        return "admin/posts";
    }
    
    @GetMapping("/posts/delete/{id}")
    public String deletePost(@PathVariable Long id) {
        adminService.deletePost(id);
        return "redirect:/admin/posts";
    }

    @GetMapping("/scams")
    public String manageScams() {
        return "admin/scams";
    }
}

