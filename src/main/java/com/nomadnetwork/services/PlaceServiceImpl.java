package com.nomadnetwork.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nomadnetwork.dto.PlaceDTO;
import com.nomadnetwork.entity.Place;
import com.nomadnetwork.repository.PlaceRepo;

@Service
public class PlaceServiceImpl implements PlaceService {
	
	@Autowired
    private PlaceRepo placeRepository;

	@Override
    public List<PlaceDTO> getAllPlaces() {
        return placeRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

	@Override
    public PlaceDTO getPlaceById(Long id) {
        return placeRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null); // or throw exception
    }
	
	 private PlaceDTO convertToDTO(Place place) {
	        PlaceDTO dto = new PlaceDTO();
	        dto.setPlaceId(place.getPlaceID());
	        dto.setName(place.getName());
	        dto.setCity(place.getCity());
	        dto.setCountry(place.getCountry());
	        dto.setDescription(place.getDescription());
	        dto.setLatitude(place.getLatitude());
	        dto.setLongitude(place.getLongitude());
	        return dto;
	    }
	 @Override
	 public PlaceDTO createPlace(PlaceDTO placeDTO) {
	     Place place = new Place();
	     place.setName(placeDTO.getName());
	     place.setCity(placeDTO.getCity());
	     place.setCountry(placeDTO.getCountry());
	     place.setLatitude(placeDTO.getLatitude());
	     place.setLongitude(placeDTO.getLongitude());
	     place.setDescription(placeDTO.getDescription());
	     Place saved = placeRepository.save(place);

	     return convertToDTO(saved);
	 }
	 
	 @Override
	    public List<PlaceDTO> searchPlaces(String keyword) {
	        return placeRepository.findByNameContainingIgnoreCase(keyword)
	                              .stream()
	                              .map(this::convertToDTO)
	                              .toList();
	    }


}
