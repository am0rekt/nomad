/*package com.nomadnetwork.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Scam {
	 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long scamId;
	
	private String titile;
	
	private String description;
	
	 @ManyToOne
	 @JoinColumn(name = "user_id", nullable = false) 
	 private User user;
	
	@ManyToOne
    @JoinColumn(name = "place_id")
    @JsonBackReference
    private Place place;

	private User reportedBy;
	
}	
*/