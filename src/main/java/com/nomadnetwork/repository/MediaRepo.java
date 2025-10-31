package com.nomadnetwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nomadnetwork.entity.Media;

public interface MediaRepo extends JpaRepository<Media, Long> {
    // No need for extra methods unless you want custom queries
}

