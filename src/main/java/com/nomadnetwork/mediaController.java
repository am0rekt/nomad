package com.nomadnetwork;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nomadnetwork.entity.Media;
import com.nomadnetwork.services.MediaService;

@RestController
@RequestMapping("/media/")
public class mediaController {

	@Autowired
	private MediaService mediaservice;
	
	@GetMapping("")
	public List<Media> getAllMedia() {
		return mediaservice.getAllMedia();
	}
	
	
}
