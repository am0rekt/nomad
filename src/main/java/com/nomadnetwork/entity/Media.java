package com.nomadnetwork.entity;

import com.nomadnetwork.dto.MediaDTO;
import com.nomadnetwork.enums.MediaType;
import com.nomadnetwork.postRepo.PostRepository;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mediaID;

    private String url;

    public Long getMediaID() {
		return mediaID;
	}

	public void setMediaID(Long mediaID) {
		this.mediaID = mediaID;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public MediaType getType() {
		return type;
	}

	public void setType(MediaType type) {
		this.type = type;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	@Enumerated(EnumType.STRING)
    private MediaType type; // VIDEO, IMAGE, etc.

    @ManyToOne
    @JoinColumn(name = "post_id") // Foreign key to Post
    private Post post;

    public static Media toEntity(MediaDTO dto, PostRepository postRepo) {
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
