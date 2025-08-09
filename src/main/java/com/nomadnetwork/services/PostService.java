package com.nomadnetwork.services;

import com.nomadnetwork.dto.PostDTO;

import java.util.List;

public interface PostService {
    List<PostDTO> getAllPost();
    PostDTO getPostById(Long id);
    PostDTO savePost(PostDTO postDTO);
    void deletePost(Long id);
}

