package com.nomadnetwork.repository;

import com.nomadnetwork.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Postrepos extends JpaRepository<Post, Long> {
}
