package mediaController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nomadnetwork.dto.MediaDTO;
import com.nomadnetwork.services.MediaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/media")
public class MediaController {

    @Autowired
    private MediaService mediaService;

    @PostMapping("")
    public ResponseEntity<MediaDTO> saveMedia(@Valid @RequestBody MediaDTO mediaDTO) {
        MediaDTO savedMediaDTO = mediaService.saveMedia(mediaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMediaDTO);
    }

    @GetMapping("")
    public List<MediaDTO> getAllMedia() {
        return mediaService.getAllMedia();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MediaDTO> getMediaById(@PathVariable Long id) {
        return ResponseEntity.ok(mediaService.getMediaById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedia(@PathVariable Long id) {
        mediaService.deleteMedia(id);
        return ResponseEntity.noContent().build();
    }
}