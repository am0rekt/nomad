package com.nomadnetwork.entity;

import com.nomadnetwork.dto.MediaDTO;
import com.nomadnetwork.enums.MediaType;
import com.nomadnetwork.repository.Postrepos;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mediaID;

    private String url;

    

	@Enumerated(EnumType.STRING)
    private MediaType type; // VIDEO, IMAGE, etc.

    @ManyToOne
    @JoinColumn(name = "post_id") // Foreign key to Post
    private Post post;

    public static Media toEntity(MediaDTO dto, Postrepos postRepo) {
        Media media = new Media();
        media.setMediaID(dto.getMediaID());
        media.setUrl(dto.getUrl());
        media.setType(MediaType.valueOf(dto.getType())); // convert String to Enum

        // Fetch the Post entity from DB using postId from DTO
        Post post = postRepo.findById(dto.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found with ID: " + dto.getPostId()));

        media.setPost(post);
        return media;
    }
}
