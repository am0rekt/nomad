package com.nomadnetwork.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.nomadnetwork.entity.User;
import com.nomadnetwork.services.UserService;

@Controller
public class ProfileController {

	 @Autowired
	    private UserService userService;

	    @GetMapping("/profile")
	    public String profile(Model model) {
	        User user = userService.getCurrentUser();
	        model.addAttribute("user", user);
	        return "user/profile";
	    }

	    @PostMapping("/profile")
	    public String updateProfile(@ModelAttribute User userForm) {
	        userService.updateProfile(userForm);
	        return "redirect:/profile?updated";
	    }
}
