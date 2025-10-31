package com.nomadnetwork.controller;

import com.nomadnetwork.entity.User;
import com.nomadnetwork.enums.Role;
import com.nomadnetwork.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserViewController {

	@Autowired
	private UserService userService;

	@GetMapping("/user-page")
	public String showUserPage(Model model) {
		model.addAttribute("users", userService.getAllUsers());
		return "users"; // Refers to users.html
	}

	@GetMapping("/create-user")
	public String showCreateUserForm(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("roles", Role.values()); // use enum values
		return "create_user";
	}

	@PostMapping("/create-user")
	public String handleCreateUser(@ModelAttribute("user") User user) {
		userService.saveUser(user);
		return "redirect:/users"; // After form submit, go to list
	}

}
