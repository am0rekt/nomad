package com.nomadnetwork.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nomadnetwork.entity.Place;

public interface PlaceRepo extends JpaRepository<Place,Long> {
	
	List<Place> findByNameContainingIgnoreCase(String keyword);
	Optional<Place> findByNameIgnoreCaseAndCountryIgnoreCase(String name,String country);

}
