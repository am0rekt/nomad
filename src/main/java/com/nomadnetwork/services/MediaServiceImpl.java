package com.nomadnetwork.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nomadnetwork.dto.MediaDTO;
import com.nomadnetwork.entity.Media;
import com.nomadnetwork.mediaRepo.MediaRepo;
import com.nomadnetwork.postRepo.PostRepository;

import jakarta.transaction.Transactional;

@Service
public class MediaServiceImpl implements MediaService {

	private final MediaRepo mediaRepo;
	private final PostRepository postRepo;

	public MediaServiceImpl(MediaRepo mediaRepo, PostRepository postRepo) {
		this.mediaRepo = mediaRepo;
		this.postRepo = postRepo;
	}

	@Override
	@Transactional
	public Media saveMedia(MediaDTO dto) {
		Media media = Media.toEntity(dto, postRepo);
		return mediaRepo.save(media);
	}

	@Override
	public List<Media> getAllMedia() {
		return mediaRepo.findAll();
	}

	@Override
	public Media getMediaById(Long id) {
		return mediaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Media not found with ID: " + id));
	}

	@Override
	@Transactional
	public void deleteMedia(Long id) {
		mediaRepo.deleteById(id);

	}
}
