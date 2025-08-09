package com.nomadnetwork.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity

public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postID;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // optional: sets the DB column name
    private User user;

    private String postUrl;
    private String title;
    private String content;
    private LocalDateTime createdAt;

    // Getters and Setters
    public Long getPostID() {
        return postID;
    }

    public void setPostID(Long postID) {
        this.postID = postID;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
