package com.nomadnetwork.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.nomadnetwork.dto.PostDTO;
import com.nomadnetwork.entity.User;
import com.nomadnetwork.entity.Post;
import com.nomadnetwork.services.PlaceService;
import com.nomadnetwork.services.PostService;
import com.nomadnetwork.services.ReportServiceImpl;
import com.nomadnetwork.services.UserService;

@Controller
@RequestMapping("/posts")
public class PostPageController {

    @Autowired
    private PostService postService;
    @Autowired
    private PlaceService placeService;
    @Autowired
    private UserService userService;
    @Autowired
    private ReportServiceImpl reportService;
    
    
    @GetMapping
    public String listPosts(Model model) {
        List<PostDTO> posts = postService.getAllPost();
        model.addAttribute("posts", posts);
        return "posts/list";  // templates/posts/list.html
    }

    @GetMapping("/{id}")
    public String viewPost(@PathVariable Long id, Model model) {
        PostDTO post = (PostDTO) postService.getPostById(id);
        User currentUser = userService.getCurrentUser();

        
        model.addAttribute("post", post);
        model.addAttribute("currentUser", currentUser); 
        return "posts/view";  // templates/posts/view.html
    }

    // ✅ Show create post form
    @GetMapping("/create")
    public String showCreatePostForm(Model model) {
        model.addAttribute("post", new PostDTO());
        model.addAttribute("places", placeService.getAllPlaces());
        return "posts/create-post";  // templates/posts/create-post.html
    }

    @PostMapping("/create")
    public String createPost(
            @ModelAttribute("post") PostDTO postDTO,
            @RequestParam(value = "image", required = false) MultipartFile image) {
    	
    	System.out.println("IMAGE NULL? " + (image == null));
        System.out.println("IMAGE EMPTY? " + (image != null && image.isEmpty()));
        System.out.println("IMAGE NAME: " + 
            (image != null ? image.getOriginalFilename() : "NO IMAGE"));

    	Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
    	
    	String username = auth.getName();

    	postService.savePost(postDTO, image, username);

        return "redirect:/profile";
    }


    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return "redirect:/profile";
    }
    
    @PostMapping("/{id}/report")
    public String reportPost(@PathVariable Long id,
            @RequestParam String reason,
            Principal principal) {

    			Post post = postService.getPostEntityById(id);
    			User user = userService.findByEmail(principal.getName());

    			reportService.reportPost(post, user, reason);

    			return "redirect:/";
    }
}

