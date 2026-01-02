package com.nomadnetwork.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Otp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    
    private LocalDateTime createdAt;

    private LocalDateTime expiryTime;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.expiryTime = createdAt.plusMinutes(5); // OTP valid for 5 mins
    }
    
    public boolean isExpired() {
        return expiryTime.isBefore(LocalDateTime.now());
    }

    public boolean canResend() {
        return createdAt.plusSeconds(30).isBefore(LocalDateTime.now());
    }
}
