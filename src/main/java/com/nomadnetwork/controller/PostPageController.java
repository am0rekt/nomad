package com.nomadnetwork.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.nomadnetwork.dto.PostDTO;
import com.nomadnetwork.services.PostService;

@Controller
@RequestMapping("/posts")
public class PostPageController {

    @Autowired
    private PostService postService;
    
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
        model.addAttribute("post", new PostDTO()); // Using PostDTO, not Post entity
        return "posts/create-post";  // templates/posts/create-post.html
    }

    @PostMapping("/create")
    public String createPost(@ModelAttribute("post") PostDTO postDTO) {
        // ✅ Set default user ID temporarily
        postDTO.setUserId(1L);

        postService.savePost(postDTO);

        return "redirect:/posts";
    }


    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return "redirect:/posts";
    }
}

