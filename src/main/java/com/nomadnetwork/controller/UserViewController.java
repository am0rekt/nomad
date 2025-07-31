package com.nomadnetwork.controller;

import com.nomadnetwork.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {

    @Autowired
    private UserService userService;

    @GetMapping("/user-page")
    public String showUserPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users"; // Refers to users.html
    }
}
