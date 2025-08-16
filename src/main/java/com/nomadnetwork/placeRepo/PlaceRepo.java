package com.nomadnetwork.placeRepo;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nomadnetwork.entity.Place;

public interface PlaceRepo extends JpaRepository<Place,Long> {

}
