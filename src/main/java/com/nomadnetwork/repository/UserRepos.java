package com.nomadnetwork.repository;

import com.nomadnetwork.entity.Post;
import com.nomadnetwork.entity.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepos extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
}
