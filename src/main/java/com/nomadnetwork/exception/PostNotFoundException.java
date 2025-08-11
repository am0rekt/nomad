package com.nomadnetwork.exception;

public class PostNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L; 
	public PostNotFoundException(Long id) {
        super("Post with ID " + id + " not found");
    }
    
    public PostNotFoundException(String message) {
        super(message);
    }

}
