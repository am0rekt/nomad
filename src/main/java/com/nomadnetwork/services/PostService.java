package com.nomadnetwork.services;

import com.nomadnetwork.dto.PostDTO;
import com.nomadnetwork.entity.User;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface PostService {
    List<PostDTO> getAllPost();
    PostDTO getPostById(Long id);
    PostDTO savePost(PostDTO postDTO,MultipartFile image,String username);
    void deletePost(Long postId);
    public PostDTO updatePost(Long id, PostDTO postDTO);
    public List<PostDTO> getPostsByPlaceId(Long placeID);
    List<PostDTO> getPostsByUserId(Long userId);
}

