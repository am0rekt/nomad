package com.nomadnetwork.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.nomadnetwork.entity.Post;
import com.nomadnetwork.entity.User;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDTO {
    private Long postID;
    private Long placeId;
    private String placeName;  
    private String country; 
    private List<String> mediaUrls;

	private String postUrl;
	private String userName;
    
    @NotBlank(message = "Title is required")
    private String title;
    private String Description;
    
    @NotBlank(message = "Content is required")
    private String content;
    
    private LocalDateTime createdAt;
    
    private Long userID;
    private String imageName;
    private String imagePath;
    private String imageType;

    

    
    private User user;
    
    public PostDTO() {}
    
    public PostDTO(Long postID, String postUrl, String title, String content, LocalDateTime createdAt, Long userID) {
        this.postID = postID;
        this.postUrl = postUrl;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.userID = userID;
        
    }
    
    public PostDTO convertToDTO(Post post) {
        PostDTO dto = new PostDTO();
        dto.setPostID(post.getPostID());
        dto.setTitle(post.getTitle());
        dto.setDescription(post.getDescription());
        dto.setPostUrl(post.getPostUrl());       

        // safely set userName
        dto.setUserName(post.getUser() != null ? post.getUser().getUserName() : "Anonymous");

        dto.setPlaceId(post.getPlace().getPlaceID());
        return dto;
    }

   
}
