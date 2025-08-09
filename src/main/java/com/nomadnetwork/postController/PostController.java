package com.nomadnetwork.postController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
	private PostService postservice;
	
	@GetMapping("")
	public List<PostDTO> getAllPosts() {
	    return postservice.getAllPost();
	}
	
	@PostMapping("")
	public PostDTO createPost(@Valid @RequestBody PostDTO postDTO) {
        return postservice.savePost(postDTO);
    }


}
