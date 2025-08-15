package com.nomadnetwork.services;

import java.util.List;
import com.nomadnetwork.dto.MediaDTO;

public interface MediaService {
    MediaDTO saveMedia(MediaDTO dto);          // return DTO, not entity
    List<MediaDTO> getAllMedia();               // return DTO list
    MediaDTO getMediaById(Long id);             // return DTO
    void deleteMedia(Long id);
}
