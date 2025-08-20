package com.nomadnetwork.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nomadnetwork.dto.PlaceDTO;
import com.nomadnetwork.services.PlaceService;

@RestController
@RequestMapping("/api/places")
public class PlaceController {

	private final PlaceService placeService;

	public PlaceController(PlaceService placeService) {
		this.placeService = placeService;
	}

	@GetMapping
	public List<PlaceDTO> getAllPlaces() {
		return placeService.getAllPlaces();
	}

	@GetMapping("/{id}")
	public PlaceDTO getPlaceById(@PathVariable Long id) {
		return placeService.getPlaceById(id);
	}

	@PostMapping
	public PlaceDTO createPlace(@RequestBody PlaceDTO placeDTO) {
		return placeService.createPlace(placeDTO);
	}

}
