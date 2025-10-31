package com.nomadnetwork.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nomadnetwork.entity.Place;

public interface PlaceRepo extends JpaRepository<Place,Long> {
	
	List<Place> findByNameContainingIgnoreCase(String keyword);


}
