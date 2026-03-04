package com.nomadnetwork.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nomadnetwork.dto.PostDTO;
import com.nomadnetwork.entity.Media;
import com.nomadnetwork.entity.Place;
import com.nomadnetwork.entity.Post;
import com.nomadnetwork.entity.ScamAlert;
import com.nomadnetwork.entity.User;
import com.nomadnetwork.enums.MediaType;
import com.nomadnetwork.exception.PostNotFoundException;
import com.nomadnetwork.repository.MediaRepo;
import com.nomadnetwork.repository.Postrepos;
import com.nomadnetwork.repository.ScamAlertRepository;
import com.nomadnetwork.repository.UserRepos;

import jakarta.transaction.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private Postrepos postRep;
    
    @Autowired
    private UserRepos userRepo;
    
    @Autowired
    private MediaRepo mediaRepo;
    
    @Autowired
    ScamAlertRepository scamAlertRepository;
    
    @Autowired
    private PlaceServiceImpl placeService;
    
    @Autowired
    private UserServiceim userService;
    
    @Autowired
    private FileStorageServiceImpl fileStorageService;
    
    private PostDTO convertToDTO(Post post) {
        PostDTO dto = new PostDTO();
        dto.setPostID(post.getPostID());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setCreatedAt(post.getCreatedAt());
        return dto;
    }
    
    @Override
    public List<PostDTO> getAllPost() {
        List<Post> posts = postRep.findAllWithMedia();

        return posts.stream().map(post -> {
            PostDTO dto = new PostDTO();
            dto.setPostID(post.getPostID());
            dto.setPostUrl(post.getPostUrl());
            dto.setTitle(post.getTitle());
            dto.setContent(post.getContent());
            dto.setCreatedAt(post.getCreatedAt());

            if (post.getUser() != null) {
                dto.setUserID(post.getUser().getUserID());
                dto.setUserName(post.getUser().getUserName());
            }

            if (post.getPlace() != null) {
                dto.setPlaceId(post.getPlace().getPlaceID());
                dto.setPlaceName(post.getPlace().getName()); 
            }
            
            

            // ✅ SAFE now — media is loaded
            dto.setMediaUrls(
                post.getMediaList()
                    .stream()
                    .map(Media::getUrl)
                    .toList()
            );

            return dto;
        }).toList();
    }



    @Override
    public PostDTO getPostById(Long id) {
        Post post = postRep.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        PostDTO dto = new PostDTO();
        dto.setPostID(post.getPostID());
        dto.setPostUrl(post.getPostUrl());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setCreatedAt(post.getCreatedAt());

        if (post.getUser() != null) {
            dto.setUserID(post.getUser().getUserID());
            dto.setUserName(post.getUser().getUserName());
        }

        if (post.getPlace() != null) {
            dto.setPlaceId(post.getPlace().getPlaceID());
            dto.setPlaceName(post.getPlace().getName()); 
        }

        // ✅ Convert media list to URLs
        List<String> mediaUrls = post.getMediaList()
                .stream()
                .map(Media::getUrl)
                .toList();
        dto.setMediaUrls(mediaUrls);

        return dto;
    }




    @Override
    public PostDTO savePost(PostDTO postDTO, MultipartFile image, String username) {

        Post post = new Post();

        // ---------------- IMAGE UPLOAD ----------------
        if (image != null && !image.isEmpty()) {
            try {
                Path uploadDir = Paths.get("uploads/places").toAbsolutePath().normalize();
                Files.createDirectories(uploadDir);

                String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
                Path targetLocation = uploadDir.resolve(fileName);

                Files.copy(image.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

                postDTO.setImageName(fileName);
                postDTO.setImagePath("uploads/places/" + fileName);
                postDTO.setPostUrl("/uploads/places/" + fileName);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // ---------------- BASIC POST DATA ----------------
        post.setPostUrl(postDTO.getPostUrl());
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());
        post.setCreatedAt(LocalDateTime.now());

        // ---------------- USER ----------------
        User user = userRepo.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        post.setUser(user);

        // ---------------- PLACE ----------------
        Place place = null;

        if (postDTO.getPlaceName() != null &&
            !postDTO.getPlaceName().isBlank() &&
            postDTO.getCountry() != null &&
            !postDTO.getCountry().isBlank()) {

            place = placeService.findOrCreatePlace(
                    postDTO.getPlaceName(),
                    postDTO.getCountry()
            );

            post.setPlace(place);
        }

        // ---------------- SAVE POST ----------------
        Post savedPost = postRep.save(post);

        // ---------------- SCAM ALERT ----------------
        if (postDTO.getScamTitle() != null &&
            !postDTO.getScamTitle().isBlank() &&
            place != null) {

            ScamAlert scam = new ScamAlert();
            scam.setTitle(postDTO.getScamTitle());
            scam.setDescription(postDTO.getScamDescription());
            scam.setSeverity(postDTO.getScamSeverity());
            scam.setPlace(place);
            scam.setCreatedBy(user);

            scamAlertRepository.save(scam);
        }

        // ---------------- MEDIA ----------------
        if (postDTO.getMediaUrls() != null && !postDTO.getMediaUrls().isEmpty()) {

            for (String url : postDTO.getMediaUrls()) {
                Media media = new Media();
                media.setUrl(url);
                media.setType(MediaType.IMAGE);
                media.setPost(savedPost);
                mediaRepo.save(media);
            }
        }

        return getPostById(savedPost.getPostID());
    }

    
   
    
    @Override
    @Transactional
    public void deletePost(Long postId) {
    	Post post = postRep.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
    	 User currentUser = userService.getCurrentUser();

    	    if (post.getUser().getUserID() != currentUser.getUserID()) {
    	        throw new RuntimeException("Not authorized");
    	    }

        // 🔒 ownership check
    	
        
    	    postRep.delete(post);
    	
    }
    
    @Override
    @Transactional
    public PostDTO updatePost(Long id, PostDTO postDTO) {
	    Post post = postRep.findById(id)
	            .orElseThrow(() -> new PostNotFoundException(id));

	    // Update fields
	    post.setPostUrl(postDTO.getPostUrl());
	    post.setTitle(postDTO.getTitle());
	    post.setContent(postDTO.getContent());

	    // Save and return updated DTO
	    Post updatedPost = postRep.save(post);
	    
    	
    	return new PostDTO(
                updatedPost.getPostID(),
                updatedPost.getPostUrl(),
                updatedPost.getTitle(),
                updatedPost.getContent(),
                updatedPost.getCreatedAt(),
                updatedPost.getUser().getUserID()
                
        );
    }
    @Override
    public List<PostDTO> getPostsByPlaceId(Long placeId) {
        return postRep.findByPlace_PlaceIDOrderByCreatedAtDesc(placeId)
                .stream()
                .map(post -> {
                    PostDTO dto = new PostDTO();
                    dto.setTitle(post.getTitle());
                    dto.setPostID(post.getPostID()); 
                    dto.setPostUrl(post.getPostUrl());
                    dto.setDescription(post.getDescription());
                    dto.setUserName(post.getUser().getUserName()); // set username here
                    return dto;
                }).toList();
    }
    
    @Override
    public List<PostDTO> getPostsByUserId(Long userId) {

        
        List<Post> posts =
                postRep.findByUserUserIDOrderByCreatedAtDesc(userId);

        return posts.stream().map(post -> {
            PostDTO dto = new PostDTO();

            dto.setPostID(post.getPostID());
            dto.setPostUrl(post.getPostUrl());
            dto.setTitle(post.getTitle());
            dto.setContent(post.getContent());
            dto.setCreatedAt(post.getCreatedAt());

            if (post.getUser() != null) {
                dto.setUserID(post.getUser().getUserID());
            }

            if (post.getPlace() != null) {
                dto.setPlaceId(post.getPlace().getPlaceID());
            }

            return dto;
        }).toList();
    }
    
    public Post getPostEntityById(Long id) {
        return postRep.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }
    
    
    @Override
    @Transactional
    public void deletePostByAdmin(Long id) {
    	Post post = postRep.findById(id)
    			.orElseThrow(() -> new RuntimeException("Post not found"));
    	
    	postRep.delete(post);
        
    }
}
