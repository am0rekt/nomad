package com.nomadnetwork.services;

import java.util.List;
import com.nomadnetwork.dto.MediaDTO;
import com.nomadnetwork.entity.Media;

public interface MediaService {
    Media saveMedia(MediaDTO dto);
    List<Media> getAllMedia();
    Media getMediaById(Long id);
    void deleteMedia(Long id);
}
