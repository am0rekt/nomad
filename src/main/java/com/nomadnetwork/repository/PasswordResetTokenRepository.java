package com.nomadnetwork.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nomadnetwork.entity.PasswordResetToken;
import com.nomadnetwork.entity.User;

public interface PasswordResetTokenRepository 
extends JpaRepository<PasswordResetToken, Long> {

Optional<PasswordResetToken> findByToken(String token);

void deleteByUser(User user);
}
