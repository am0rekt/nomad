package com.nomadnetwork.services;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nomadnetwork.entity.PasswordResetToken;
import com.nomadnetwork.entity.User;
import com.nomadnetwork.repository.PasswordResetTokenRepository;

import jakarta.transaction.Transactional;

@Service
public class PasswordResetService {

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Transactional
    public PasswordResetToken createResetTokenForUser(User user) {
    	tokenRepository.deleteByUser(user);  // delete old token
        tokenRepository.flush();   

        // Generate new token
        PasswordResetToken token = new PasswordResetToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUser(user);
        token.setExpiryTime(LocalDateTime.now().plusMinutes(10));

        return tokenRepository.save(token);
    }
}
