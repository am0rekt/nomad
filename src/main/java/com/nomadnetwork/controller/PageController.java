package com.nomadnetwork.controller;

import com.nomadnetwork.dto.PlaceDTO;

import com.nomadnetwork.services.MediaService;
import com.nomadnetwork.services.PlaceService;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {

    private final MediaService mediaService;
    private final PlaceService placeService;

    public PageController(MediaService mediaService,PlaceService placeService) {
        this.mediaService = mediaService;
        this.placeService = placeService;
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
    
    @GetMapping("/places/search")
    public String searchPlaces(@RequestParam("keyword") String keyword, Model model) {
        List<PlaceDTO> results = placeService.searchPlaces(keyword);
        model.addAttribute("results", results);
        model.addAttribute("keyword", keyword);
        return "places/search";  // You’ll create this HTML
    }

}

