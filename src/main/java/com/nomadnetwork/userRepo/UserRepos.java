package com.nomadnetwork.userRepo;

import com.nomadnetwork.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepos extends JpaRepository<User, Long> {
}
