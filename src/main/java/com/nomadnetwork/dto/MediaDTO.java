package com.nomadnetwork.dto;

import com.nomadnetwork.entity.Media;

public class MediaDTO {
	private Long mediaID;
	private String url;
	private String type;
	private Long postId;

	public MediaDTO toDTO(Media media) {
	    MediaDTO dto = new MediaDTO();
	    dto.setMediaID(media.getMediaID());
	    dto.setUrl(media.getUrl());
	    dto.setType(media.getType().name()); // Enum to String
	    dto.setPostId(media.getPost().getPostID());
	    return dto;
	}

	
	
	public Long getMediaID() {
		return mediaID;
	}
	public void setMediaID(Long mediaID) {
		this.mediaID = mediaID;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getPostId() {
		return postId;
	}
	public void setPostId(Long postId) {
		this.postId = postId;
	}
	
}
