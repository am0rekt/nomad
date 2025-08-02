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
import jakarta.validation.*;
import org.springframework.validation.BindingResult;


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
        model.addAttribute("roles", Role.values()); // use enums values
        return "create_user";
    }

    
    @PostMapping("/create-user")
    public String handleCreateUser(@Valid @ModelAttribute("user") User user,BindingResult result,Model model) {
    	if(result.hasErrors()){
    		return "create_user";
    	}
        userService.saveUser(user);
        return "redirect:/user-page"; // After form submit, go to list
    }


}
