package com.nomadnetwork.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
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
                .orElseThrow(() -> new RuntimeException("Place not found"));
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
	     place.setDescription(placeDTO.getDescription());
	     
	     String searchText = Stream.of(placeDTO.getName(), placeDTO.getCity(), placeDTO.getCountry())
	             .filter(s -> s != null && !s.isBlank())
	             .collect(Collectors.joining(", "));

	     // Fetch coordinates
	     double[] coords = fetchCoordinatesFromOSM(searchText);
	     if (coords != null) {
	         place.setLatitude(coords[0]);
	         place.setLongitude(coords[1]);
	     }
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


	 @Override
	 public Place findOrCreatePlace(String name, String country) {

		    String normalizedName = name.trim();
		    String normalizedCountry = country.trim();

		    // 1️⃣ Check if place already exists
		    Optional<Place> existingPlace =
		            placeRepository.findByNameIgnoreCaseAndCountryIgnoreCase(
		                    normalizedName, normalizedCountry
		            );

		    if (existingPlace.isPresent()) {
		        return existingPlace.get();
		    }

		    // 2️⃣ Create new place
		    Place newPlace = new Place();
		    newPlace.setName(normalizedName);
		    newPlace.setCountry(normalizedCountry);

		    // 3️⃣ Try geocoding: "City, Country"
		    String searchText = normalizedName + ", " + normalizedCountry;
		    double[] coords = fetchCoordinatesFromOSM(searchText);

		    // 4️⃣ Fallback: try just city name (OSM often resolves this)
		    if (coords == null) {
		        coords = fetchCoordinatesFromOSM(normalizedName);
		    }

		    // 5️⃣ Apply coordinates if found
		    if (coords != null) {
		        newPlace.setLatitude(coords[0]);
		        newPlace.setLongitude(coords[1]);
		    } else {
		        System.out.println("⚠️ Geocoding failed for place: " + normalizedName);
		    }

		    // 6️⃣ Save and return
		    return placeRepository.save(newPlace);
		}

	 private double[] fetchCoordinatesFromOSM(String searchText) {
		    try {
		        String url = UriComponentsBuilder
		                .fromHttpUrl("https://nominatim.openstreetmap.org/search")
		                .queryParam("q", searchText)
		                .queryParam("format", "json")
		                .queryParam("limit", 1)
		                .toUriString();

		        RestTemplate restTemplate = new RestTemplate();
		        HttpHeaders headers = new HttpHeaders();
		        headers.set(
		            "User-Agent",
		            "NomadNetwork/1.0 (contact: arjunmn@gmail.com)"
		        );
		        HttpEntity<String> entity = new HttpEntity<>(headers);

		        ResponseEntity<JsonNode[]> response = restTemplate.exchange(
		                url,
		                HttpMethod.GET,
		                entity,
		                JsonNode[].class
		        );

		        JsonNode[] results = response.getBody();
		        if (results != null && results.length > 0) {
		            double lat = results[0].get("lat").asDouble();
		            double lon = results[0].get("lon").asDouble();
		            return new double[]{lat, lon};
		        } else {
		            return null; // Could throw exception if you prefer
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		        return null;
		    }
		}
	 
}
