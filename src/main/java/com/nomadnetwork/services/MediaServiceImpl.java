package com.nomadnetwork.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nomadnetwork.dto.MediaDTO;
import com.nomadnetwork.entity.Media;
import com.nomadnetwork.repository.MediaRepo;
import com.nomadnetwork.repository.Postrepos;

import jakarta.transaction.Transactional;

@Service
public class MediaServiceImpl implements MediaService {

	private final MediaRepo mediaRepo;
	private final Postrepos postRepo;

	public MediaServiceImpl(MediaRepo mediaRepo, Postrepos postRepo) {
		this.mediaRepo = mediaRepo;
		this.postRepo = postRepo;
	}

	@Override
    @Transactional
    public MediaDTO saveMedia(MediaDTO dto) {
        Media media = Media.toEntity(dto, postRepo);
        Media savedMedia = mediaRepo.save(media);
        return MediaDTO.fromEntity(savedMedia);
    }

    @Override
    public List<MediaDTO> getAllMedia() {
        return mediaRepo.findAll()
                .stream()
                .map(MediaDTO::fromEntity)
                .toList();
    }

    @Override
    public MediaDTO getMediaById(Long id) {
        return mediaRepo.findById(id)
                .map(MediaDTO::fromEntity)
                .orElseThrow(() -> new RuntimeException("Media not found with ID: " + id));
    }

    @Override
    @Transactional
    public void deleteMedia(Long id) {
        mediaRepo.deleteById(id);
    }
}
