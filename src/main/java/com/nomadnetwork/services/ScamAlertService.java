package com.nomadnetwork.services;


import java.util.List;

import com.nomadnetwork.entity.ScamAlert;

public interface ScamAlertService {

    ScamAlert createScamAlert(ScamAlert scamAlert);

    List<ScamAlert> getScamsByPlaceId(Long placeId);

    long countScamsByPlaceId(Long placeId);

}
