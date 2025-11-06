package com.nomadnetwork.postController;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.nomadnetwork.services.FileStorageServiceImpl;
import com.nomadnetwork.services.PlaceService;

import jakarta.validation.Valid;


import com.nomadnetwork.dto.PostDTO;
import com.nomadnetwork.services.PostService;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;
    
    @Autowired
    private PlaceService placeService;
    
    @Autowired
    private FileStorageServiceImpl fileStorageService;

    @GetMapping("")
    public List<PostDTO> getAllPosts() {
        return postService.getAllPost();
    }
    @GetMapping("/create")
    public String showCreatePostForm(Model model) {
        model.addAttribute("post", new PostDTO());
        model.addAttribute("places", placeService.getAllPlaces());
        return "posts/create-post"; // your form HTML
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        PostDTO postDTO = postService.getPostById(id);
        return ResponseEntity.ok(postDTO);
    }

    @PostMapping("")
    public PostDTO createPost(@Valid @RequestBody PostDTO postDTO,MultipartFile image) {
        return postService.savePost(postDTO,image);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok("Post deleted successfully");
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody PostDTO postDTO) {
        PostDTO updatedPost = postService.updatePost(id, postDTO);
        return ResponseEntity.ok(updatedPost);
    }
    
    @PostMapping("uploads")
    public ResponseEntity<PostDTO> uploadPost(
    		@RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("image") MultipartFile image) throws IOException{
    	
    	 String imagePath = fileStorageService.saveFile(image);
    	 
    	 PostDTO postDTO = new PostDTO();
    	 postDTO.setTitle(title);
    	 postDTO.setDescription(description);
    	 postDTO.setImageName(image.getOriginalFilename());
         postDTO.setImagePath(imagePath);
         postDTO.setImageType(image.getContentType());
    	 
    	 PostDTO savedPost = postService.savePost(postDTO,image);
    	return ResponseEntity.ok(savedPost);
    	
    }
}
