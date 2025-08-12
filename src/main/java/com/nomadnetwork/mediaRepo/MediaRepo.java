package com.nomadnetwork.mediaRepo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nomadnetwork.entity.Media;

public interface MediaRepo extends JpaRepository<Media,Long>{
	

}
