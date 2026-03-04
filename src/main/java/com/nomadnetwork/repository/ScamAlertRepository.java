package com.nomadnetwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nomadnetwork.entity.ScamAlert;

import java.util.List;

public interface ScamAlertRepository extends JpaRepository<ScamAlert, Long> {
    List<ScamAlert> findByPlace_PlaceID(Long placeID);
    long count();
    long countByPlace_PlaceID(Long placeID);
    List<ScamAlert> findByPlace_PlaceIDOrderByCreatedAtDesc(Long placeID);
    
}
