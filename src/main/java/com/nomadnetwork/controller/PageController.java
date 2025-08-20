package com.nomadnetwork.controller;

import com.nomadnetwork.services.MediaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    private final MediaService mediaService;

    public PageController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    // Home Page
    @GetMapping("/")
    public String viewHomePage() {
        return "index"; // templates/index.html
    }

    // Show all media
    @GetMapping("/media")
    public String viewMediaPage(Model model) {
        model.addAttribute("mediaList", mediaService.getAllMedia());
        return "media"; // templates/media.html
    }
}

