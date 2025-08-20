package com.nomadnetwork.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postID;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // optional: sets the DB column name
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "place_id")
    @JsonBackReference
    private Place place;


    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Media> mediaList = new ArrayList<>();
    
    @Column(name = "post_url")
    private String postUrl;
    
    private String title;
    private String content;
    
    private LocalDateTime createdAt;
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }


   
}
