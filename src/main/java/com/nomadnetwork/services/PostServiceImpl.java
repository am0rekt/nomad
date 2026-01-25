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
import com.nomadnetwork.entity.User;
import com.nomadnetwork.enums.MediaType;
import com.nomadnetwork.exception.PostNotFoundException;
import com.nomadnetwork.repository.MediaRepo;
import com.nomadnetwork.repository.Postrepos;
import com.nomadnetwork.repository.UserRepos;

import jakarta.transaction.Transactional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private Postrepos postRep;
    
    @Autowired
    private UserRepos userRepo;
    
    @Autowired
    private MediaRepo mediaRepo;
    
    @Autowired
    private PlaceServiceImpl placeService;
    
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
        List<Post> posts = postRep.findAll();

        return posts.stream().map(post -> {
            PostDTO dto = new PostDTO();
            dto.setPostID(post.getPostID());
            dto.setPostUrl(post.getPostUrl());
            dto.setTitle(post.getTitle());
            dto.setContent(post.getContent());
            dto.setCreatedAt(post.getCreatedAt());

            if (post.getUser() != null) {
                dto.setUserId(post.getUser().getUserID());
            }

            if (post.getPlace() != null) {
                dto.setPlaceId(post.getPlace().getPlaceID());
            }

            // ✅ Include media URLs
            List<String> mediaUrls = post.getMediaList()
                    .stream()
                    .map(Media::getUrl)
                    .toList();
            dto.setMediaUrls(mediaUrls);

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
            dto.setUserId(post.getUser().getUserID());
        }

        if (post.getPlace() != null) {
            dto.setPlaceId(post.getPlace().getPlaceID());
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
    public PostDTO savePost(PostDTO postDTO,MultipartFile image) {
        Post post = new Post();
        post.setPostUrl(postDTO.getPostUrl());
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());
        post.setCreatedAt(LocalDateTime.now());

        // ✅ Set user if provided
        if (postDTO.getUserId() != null) {
            User user = userRepo.findById(postDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            post.setUser(user);
        }

        // ✅ Set place if provided
        if (postDTO.getPlaceName() != null && postDTO.getCountry() != null) {
            Place place = placeService.findOrCreatePlace(postDTO.getPlaceName(), postDTO.getCountry());
            post.setPlace(place);
        }

        // ✅ Save post first
        Post savedPost = postRep.save(post);

        // ✅ Save media URLs if present
        if (postDTO.getMediaUrls() != null && !postDTO.getMediaUrls().isEmpty()) {
            for (String url : postDTO.getMediaUrls()) {
                Media media = new Media();
                media.setUrl(url);
                media.setType(MediaType.IMAGE); // or VIDEO based on logic later
                media.setPost(savedPost);
                mediaRepo.save(media);
            }
        }
        if (image != null && !image.isEmpty()) {
            String imagePath = fileStorageService.saveFile(image);
            Media media = new Media();
            media.setUrl(imagePath);
            media.setType(MediaType.IMAGE);
            media.setPost(savedPost);
            mediaRepo.save(media);
        }

        // ✅ Return the saved DTO
        return getPostById(savedPost.getPostID());
    }

    
   
    
    @Override
    @Transactional
    public void deletePost(Long id) {
    	 if (!postRep.existsById(id)) {
    	        throw new PostNotFoundException(id);
    	    }
    	postRep.deleteById(id);
    	
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
        return postRep.findByPlace_PlaceID(placeId)
                .stream()
                .map(post -> {
                    PostDTO dto = new PostDTO();
                    dto.setTitle(post.getTitle());
                    dto.setPostUrl(post.getPostUrl());
                    dto.setDescription(post.getDescription());
                    dto.setUserName(post.getUser().getUserName()); // set username here
                    return dto;
                }).toList();
    }
    }
