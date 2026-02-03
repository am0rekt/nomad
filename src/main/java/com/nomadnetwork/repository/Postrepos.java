package com.nomadnetwork.repository;

import com.nomadnetwork.entity.Post;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface Postrepos extends JpaRepository<Post, Long> {
	 List<Post> findByPlace_PlaceID(Long placeID);
	 
	 List<Post> findTop5ByOrderByCreatedAtDesc();
	 
	 @Query("""
			 SELECT DISTINCT p FROM Post p
			 LEFT JOIN FETCH p.mediaList
			 ORDER BY p.createdAt DESC
			 """)
			 List<Post> findAllWithMedia();

}
