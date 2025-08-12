package com.nomadnetwork.services;

import com.nomadnetwork.dto.PostDTO;
import com.nomadnetwork.entity.Post;
import com.nomadnetwork.exception.PostNotFoundException;
import com.nomadnetwork.repository.Postrepos;
import com.nomadnetwork.entity.User;
import com.nomadnetwork.userRepo.UserRepos;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
//import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private Postrepos postRep;
    
    @Autowired
    private UserRepos userRepository;

    @Override
    public List<PostDTO> getAllPost() {
        return postRep.findAll().stream()
                .map(post -> {
                    PostDTO dto = new PostDTO();
                    dto.setPostID(post.getPostID());
                    dto.setPostUrl(post.getPostUrl());
                    dto.setTitle(post.getTitle());
                    dto.setContent(post.getContent());
                    dto.setCreatedAt(post.getCreatedAt());
                    if (post.getUser() != null) {
                        dto.setUserId(post.getUser().getUserID());
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }


    @Override
    public PostDTO getPostById(Long id) {
        Post post = postRep.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        
        PostDTO dto = new PostDTO();
        dto.setPostID(post.getPostID());
        dto.setPostUrl(post.getPostUrl());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUserId(post.getUser().getUserID()); // if you want userId too
        
        return dto;
    }


    @Override
    public PostDTO savePost(PostDTO postDTO) {
        Post post = new Post();
        post.setPostUrl(postDTO.getPostUrl());
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());

        // ✅ Get the user before saving
        User user = userRepository.findById(postDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        post.setUser(user);

        post.setCreatedAt(LocalDateTime.now());

        Post savedPost = postRep.save(post);

        postDTO.setPostID(savedPost.getPostID());
        return postDTO;
    }
    
   
    
    @Override
    @Transactional
    public void deletePost(Long id) {
    	 if (!postRep.existsById(id)) {
    	        throw new PostNotFoundException(id);
    	    }
    	postRep.deleteById(id);
    	
    }
    
    @Override
    @Transactional
    public PostDTO updatePost(Long id, PostDTO postDTO) {
	    Post post = postRep.findById(id)
	            .orElseThrow(() -> new PostNotFoundException(id));

	    // Update fields
	    post.setPostUrl(postDTO.getPostUrl());
	    post.setTitle(postDTO.getTitle());
	    post.setContent(postDTO.getContent());

	    // Save and return updated DTO
	    Post updatedPost = postRep.save(post);
    	
    	return new PostDTO(
                updatedPost.getPostID(),
                updatedPost.getPostUrl(),
                updatedPost.getTitle(),
                updatedPost.getContent(),
                updatedPost.getCreatedAt(),
                updatedPost.getUser().getUserID()
        );
    }
}
