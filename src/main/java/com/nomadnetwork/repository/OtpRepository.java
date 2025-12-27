package com.nomadnetwork.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nomadnetwork.entity.Otp;
import com.nomadnetwork.entity.User;

public interface OtpRepository extends JpaRepository<Otp, Long> {

    Optional<Otp> findByUser(User user);

    void deleteByUser(User user);
}
