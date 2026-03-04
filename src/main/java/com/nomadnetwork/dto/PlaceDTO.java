package com.nomadnetwork.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceDTO {

    private Long placeId;
    private String name;
    private String city;
    private String country;
    private Double latitude;
    private Double longitude;
    private String description;
    private long scamCount;
    
}
