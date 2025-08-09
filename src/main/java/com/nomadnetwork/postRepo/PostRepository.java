package com.nomadnetwork.postRepo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nomadnetwork.entity.Post;

public interface PostRepository extends JpaRepository <Post,Long> {

}
