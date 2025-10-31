package com.nomadnetwork.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.nomadnetwork.dto.UserRegistrationDTO;
import com.nomadnetwork.services.UserService;

@Controller
public class RegisterUserController {
	@Autowired
    private UserService userService;
	
	 @GetMapping("/register")
	 public String showRegistrationForm(Model model) {
	        model.addAttribute("userDTO", new UserRegistrationDTO());
	        return "user/register";
	 }
	 
	 @PostMapping("/register")
	    public String registerUser(@ModelAttribute("userDTO") UserRegistrationDTO dto, Model model) {
	        try {
	            userService.registerUser(dto);
	            return "redirect:/login";
	        } catch (RuntimeException e) {
	            model.addAttribute("error", e.getMessage());
	            return "user/register";
	        }
	    }

}
