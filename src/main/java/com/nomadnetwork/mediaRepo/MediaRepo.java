package com.nomadnetwork.mediaRepo;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nomadnetwork.dto.MediaDTO;
import com.nomadnetwork.entity.Media;

public interface MediaRepo extends JpaRepository<Media,Long>{

	Collection<MediaDTO> getAllMedia();
	

}
