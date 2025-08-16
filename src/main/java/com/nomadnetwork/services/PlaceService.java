package com.nomadnetwork.services;

import java.util.List;

import com.nomadnetwork.dto.PlaceDTO;

public interface PlaceService {
	
	 public List<PlaceDTO> getAllPlaces();
	 public PlaceDTO getPlaceById(Long id);
	 PlaceDTO createPlace(PlaceDTO placeDTO);

}
