package com.nomadnetwork.repository;

import com.nomadnetwork.entity.Post;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Postrepos extends JpaRepository<Post, Long> {
	 List<Post> findByPlace_PlaceID(Long placeID);
}
