package com.nomadnetwork.postController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;


import com.nomadnetwork.dto.PostDTO;
//import com.nomadnetwork.dto.UserDTO;
//import com.nomadnetwork.entity.Post;
//import com.nomadnetwork.entity.User;
import com.nomadnetwork.services.PostService;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("")
    public List<PostDTO> getAllPosts() {
        return postService.getAllPost();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        PostDTO postDTO = postService.getPostById(id);
        return ResponseEntity.ok(postDTO);
    }

    @PostMapping("")
    public PostDTO createPost(@Valid @RequestBody PostDTO postDTO) {
        return postService.savePost(postDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok("Post deleted successfully");
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody PostDTO postDTO) {
        PostDTO updatedPost = postService.updatePost(id, postDTO);
        return ResponseEntity.ok(updatedPost);
    }
}
