package com.nomadnetwork.controller;

import com.nomadnetwork.dto.PlaceDTO;

import com.nomadnetwork.services.MediaService;
import com.nomadnetwork.services.PlaceService;
import com.nomadnetwork.services.PostService;
import com.nomadnetwork.services.UserService;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {

    private final MediaService mediaService;
    private final PlaceService placeService;
    private final PostService postService;
    private final UserService userService;

    public PageController(MediaService mediaService,
    		PlaceService placeService,
    		PostService postService,UserService userService) {
        this.mediaService = mediaService;
        this.placeService = placeService;
        this.postService = postService;
        this.userService = userService;
    }

    // Home Page
    @GetMapping("/")
    public String viewHomePage(Model model) {
    	model.addAttribute("posts", postService.getAllPost());
    	model.addAttribute("currentUser", userService.getCurrentUser());   

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
        return "places/search";  //search html
    }

    @GetMapping("/places/{placeId}")
    public String viewPlaceDetails(@PathVariable Long placeId, Model model) {

        PlaceDTO place = placeService.getPlaceById(placeId);
        model.addAttribute("place", place);

        model.addAttribute("posts",
                postService.getPostsByPlaceId(placeId));

        return "places/place-details";
    }
}

