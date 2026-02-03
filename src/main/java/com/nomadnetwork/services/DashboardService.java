package com.nomadnetwork.services;

import org.springframework.stereotype.Service;

import com.nomadnetwork.repository.PlaceRepo;
import com.nomadnetwork.repository.Postrepos;
import com.nomadnetwork.repository.UserRepos;

@Service
public class DashboardService {
	
	private final UserRepos userRepo;
    private final Postrepos postRepo;
    private final PlaceRepo placeRepo;

    public DashboardService(UserRepos userRepo,
                            Postrepos postRepo,
                            PlaceRepo placeRepo) {
        this.userRepo = userRepo;
        this.postRepo = postRepo;
        this.placeRepo = placeRepo;
    }
    
    public long getUserCount() {
    	return userRepo.count();
    }
    
    public long getPostCount() {
    	return postRepo.count();
    }
    
    public long getPlaceCount() {
    	return placeRepo.count();
    }
}
