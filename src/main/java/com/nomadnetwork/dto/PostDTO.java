package com.nomadnetwork.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.nomadnetwork.entity.User;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDTO {
    private Long postID;
    private Long placeId;
    private List<String> mediaUrls;

	private String postUrl;
    
    @NotBlank(message = "Title is required")
    private String title;
    
    @NotBlank(message = "Content is required")
    private String content;
    
    private LocalDateTime createdAt;
    
    private Long userId;
    
    private User user;
    
    public PostDTO() {}
    
    public PostDTO(Long postID, String postUrl, String title, String content, LocalDateTime createdAt, Long userId) {
        this.postID = postID;
        this.postUrl = postUrl;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.userId = userId;
    }

   
}
