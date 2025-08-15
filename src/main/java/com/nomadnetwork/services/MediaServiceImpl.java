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
