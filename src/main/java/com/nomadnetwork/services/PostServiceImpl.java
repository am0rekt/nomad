package com.nomadnetwork.services;

import com.nomadnetwork.dto.PostDTO;
import com.nomadnetwork.entity.Post;
import com.nomadnetwork.postRepo.PostRepository;
import com.nomadnetwork.repository.Postrepos;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private Postrepos postRep;

    @Override
    public List<PostDTO> getAllPost() {
        return postRep.findAll()
        .stream()
        .map(post ->{
            PostDTO dto = new PostDTO();
            dto.setPostID(post.getPostID());
            dto.setPostUrl(post.getPostUrl());
            dto.setTitle(post.getTitle());
            dto.setContent(post.getContent());
            dto.setCreatedAt(post.getCreatedAt());
            return dto;
        })
        .collect(Collectors.toList());
    }

    @Override
    public PostDTO getPostById(Long id) {
        Optional<Post> optionalPost = postRep.findById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            PostDTO dto = new PostDTO();
            dto.setPostID(post.getPostID());
            dto.setPostUrl(post.getPostUrl());
            dto.setTitle(post.getTitle());
            dto.setContent(post.getContent());
            dto.setCreatedAt(post.getCreatedAt());
            return dto;
        }
        return null;
    }

    @Override
    public PostDTO savePost(PostDTO postDTO) {
        Post post = new Post();
        post.setPostID(postDTO.getPostID()); // optional
        post.setPostUrl(postDTO.getPostUrl());
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setCreatedAt(postDTO.getCreatedAt());

        Post savedPost = postRep.save(post);

        postDTO.setPostID(savedPost.getPostID());
        return postDTO;
    }
    
    @Autowired
    private PostRepository postRepository;
    
    @Override
    @Transactional
    public void deletePost(Long id) {
    	postRepository.deleteById(id);
    	
    	
    }
}
