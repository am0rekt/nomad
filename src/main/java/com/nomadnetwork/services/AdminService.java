package com.nomadnetwork.services;

import java.util.List;

import com.nomadnetwork.dto.PostDTO;
import com.nomadnetwork.dto.UserDTO;

public interface AdminService {
	
	List<UserDTO> getAllUsers();
    void changeUserRole(Long userId, String role);
    void deleteUser(Long userId);

    List<PostDTO> getAllPosts();
    void deletePost(Long postId);
}
