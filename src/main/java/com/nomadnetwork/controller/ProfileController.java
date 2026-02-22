package com.nomadnetwork.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.nomadnetwork.dto.PostDTO;
import com.nomadnetwork.entity.User;
import com.nomadnetwork.repository.UserRepos;
import com.nomadnetwork.services.PostService;
import com.nomadnetwork.services.UserService;

@Controller
public class ProfileController {

	 @Autowired
	    private UserService userService;
	 @Autowired
	 	private PostService postService;
	 @Autowired
	    private UserRepos userRepos;

	    @GetMapping("/profile")
	    public String profile(Model model) {
	        User user = userService.getCurrentUser();
	        
	        List<PostDTO> posts = postService.getPostsByUserId(user.getUserID());

	        model.addAttribute("user", user);
	        model.addAttribute("posts", posts);
	    

	        return "user/profile";
	    }

	    @PostMapping("/profile")
	    public String updateProfile(@ModelAttribute User userForm) {
	        userService.updateProfile(userForm);
	        return "redirect:/profile?updated";
	    }
	    
	    @PostMapping("/delete-account")
	    public String deleteAccount(Authentication authentication) {

	        String email = authentication.getName();

	        User user = userRepos.findByEmail(email)
	                .orElseThrow(() -> new RuntimeException("User not found"));

	        userRepos.delete(user);

	        return "redirect:/login";
	    }
}
