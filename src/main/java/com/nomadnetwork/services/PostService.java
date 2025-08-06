package com.nomadnetwork.services;

import com.nomadnetwork.dto.PostDTO;
import com.nomadnetwork.entity.Post;

import java.util.List;

public interface PostService {
    List<Post> getAllPost();
    PostDTO getPostById(Long id);
    PostDTO savePost(PostDTO postDTO);
}

