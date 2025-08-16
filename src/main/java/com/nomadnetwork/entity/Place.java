package com.nomadnetwork.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Place {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long placeID;
	
	private String name;        // e.g., "Jew Town"
    private String city;        // e.g., "Kochi"
    private String country;     // e.g., "India"
    private String description; // Optional info about the place
    private Double latitude;    // For map
    private Double longitude;	// For map

    @OneToMany(mappedBy = "place")
    @JsonManagedReference
    private List<Post> posts;
}
