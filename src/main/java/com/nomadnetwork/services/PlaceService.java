package com.nomadnetwork.services;

import java.util.List;

import com.nomadnetwork.dto.PlaceDTO;
import com.nomadnetwork.entity.Place;

public interface PlaceService {
	
	 public List<PlaceDTO> getAllPlaces();
	 public PlaceDTO getPlaceById(Long id);
	 PlaceDTO createPlace(PlaceDTO placeDTO);
	 public List<PlaceDTO> searchPlaces(String keyword);
	 public Place findOrCreatePlace(String name,String country);  
		


}
