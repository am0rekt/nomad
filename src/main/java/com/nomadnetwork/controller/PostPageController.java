package com.nomadnetwork.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
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

import com.nomadnetwork.dto.PostDTO;
import com.nomadnetwork.services.PlaceService;
import com.nomadnetwork.services.PostService;

@Controller
@RequestMapping("/posts")
public class PostPageController {

    @Autowired
    private PostService postService;
    @Autowired
    private PlaceService placeService;
    
    @GetMapping
    public String listPosts(Model model) {
        List<PostDTO> posts = postService.getAllPost();
        model.addAttribute("posts", posts);
        return "posts/list";  // templates/posts/list.html
    }

    @GetMapping("/{id}")
    public String viewPost(@PathVariable Long id, Model model) {
        PostDTO post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "posts/view";  // templates/posts/view.html
    }

    // ✅ Show create post form
    @GetMapping("/create")
    public String showCreatePostForm(Model model) {
        model.addAttribute("post", new PostDTO());
        model.addAttribute("places", placeService.getAllPlaces());// Using PostDTO, not Post entity
        return "posts/create-post";  // templates/posts/create-post.html
    }

    @PostMapping("/create")
    public String createPost(
            @ModelAttribute("post") PostDTO postDTO,
            @RequestParam(value = "image", required = false) MultipartFile image) {

        // ✅ Temporarily set default user
        postDTO.setUserId(1L);

        // ✅ Handle image upload (only if image is provided)
        if (image != null && !image.isEmpty()) {
            try {
                // Create uploads directory if not exists
                Path uploadDir = Paths.get("uploads/places").toAbsolutePath().normalize();
                Files.createDirectories(uploadDir);

                // Generate unique filename
                String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
                Path targetLocation = uploadDir.resolve(fileName);

                // Copy the file to the uploads directory
                Files.copy(image.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

                // ✅ Store both the filesystem path and web-accessible path
                postDTO.setImageName(fileName);
                postDTO.setImagePath("uploads/places/" + fileName);
                postDTO.setPostUrl("/uploads/places/" + fileName); // 🔗 public URL to access the image
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // ✅ Save post (with or without image)
        postService.savePost(postDTO, image);

        return "redirect:/posts";
    }


    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return "redirect:/posts";
    }
}

