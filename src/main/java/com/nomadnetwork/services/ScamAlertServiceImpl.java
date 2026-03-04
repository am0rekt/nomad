package com.nomadnetwork.services;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nomadnetwork.entity.ScamAlert;
import com.nomadnetwork.repository.ScamAlertRepository;
import com.nomadnetwork.services.ScamAlertService;

@Service
public class ScamAlertServiceImpl implements ScamAlertService {

    @Autowired
    private ScamAlertRepository scamAlertRepository;

    @Override
    public ScamAlert createScamAlert(ScamAlert scamAlert) {
        scamAlert.setCreatedAt(LocalDateTime.now());
        return scamAlertRepository.save(scamAlert);
    }

    @Override
    public List<ScamAlert> getScamsByPlaceId(Long placeId) {
        return scamAlertRepository.findByPlace_PlaceIDOrderByCreatedAtDesc(placeId);
    }

    @Override
    public long countScamsByPlaceId(Long placeId) {
        return scamAlertRepository.countByPlace_PlaceID(placeId);
    }
}
