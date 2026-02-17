package com.nomadnetwork.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nomadnetwork.dto.PostDTO;
import com.nomadnetwork.dto.UserDTO;
import com.nomadnetwork.entity.Media;
import com.nomadnetwork.entity.Post;
import com.nomadnetwork.entity.User;
import com.nomadnetwork.enums.Role;
import com.nomadnetwork.repository.Postrepos;
import com.nomadnetwork.repository.UserRepos;

@Service
public class AdminServiceImpl implements AdminService {
	
	

	@Autowired
    private UserRepos userRepo;

    @Autowired
    private Postrepos postRepo;
    
    @Override
    public List<UserDTO> getAllUsers() {
        return userRepo.findAll()
        		.stream()
        		.map(user -> new UserDTO(
        				user.getUserID(),
                        user.getUserName(),
                        user.getBio(),
                        user.getEmail(),
                        user.getPhone(),
                        user.getRole().name(),
                        user.isEnabled()
                ))
                .toList();
    }
    
    @Override
    public void changeUserRole(Long id, String role) {
        User user = userRepo.findById(id).orElseThrow();
        user.setRole(Role.valueOf(role));
     
        userRepo.save(user);
    }
    
    @Override
    public void deleteUser(Long userId) {
    	User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setDeleted(true);
        user.setEnabled(false);   // prevent login

        userRepo.save(user);
    }

    @Override
    public List<PostDTO> getAllPosts() {
        return postRepo.findAllWithMedia()
                .stream()
                .map(post -> {
                    PostDTO dto = new PostDTO();
                    dto.setPostID(post.getPostID());
                    dto.setTitle(post.getTitle());
                    dto.setContent(post.getContent());
                    dto.setCreatedAt(post.getCreatedAt());
                    return dto;
                }).toList();
    }

    @Override
    public void deletePost(Long postId) {

        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // Delete files from disk
        for (Media media : post.getMediaList()) {
            try {
                Path path = Paths.get(media.getUrl().replaceFirst("/", ""));
                Files.deleteIfExists(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Delete DB record (media removed via cascade)
        postRepo.delete(post);
    }
    
    public List<Post> getRecentPosts() {
        return postRepo.findTop5ByOrderByCreatedAtDesc();
    }
}
