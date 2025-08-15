package com.nomadnetwork.controller;

import com.nomadnetwork.services.MediaService;
import com.nomadnetwork.services.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    private final PostService postService;
    private final MediaService mediaService;

    public PageController(PostService postService, MediaService mediaService) {
        this.postService = postService;
        this.mediaService = mediaService;
    }

    // Index / Home page
    @GetMapping("/")
    public String viewHomePage() {
        return "index"; // templates/index.html
    }

    // Show all posts
    @GetMapping("/posts")
    public String viewPostPage(Model model) {
        model.addAttribute("postList", postService.getAllPost()); // Should return DTOs
        return "posts";
    }

    // Show all media
    @GetMapping("/media")
    public String viewMediaPage(Model model) {
        model.addAttribute("mediaList", mediaService.getAllMedia()); // Should return DTOs
        return "media"; // templates/media-page.html
    }
}


