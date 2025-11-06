package com.nomadnetwork.services;

import com.nomadnetwork.dto.PostDTO;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface PostService {
    List<PostDTO> getAllPost();
    PostDTO getPostById(Long id);
    PostDTO savePost(PostDTO postDTO,MultipartFile image);
    void deletePost(Long id);
    public PostDTO updatePost(Long id, PostDTO postDTO);

}

