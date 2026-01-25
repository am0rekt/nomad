package com.nomadnetwork.dto;
import com.nomadnetwork.entity.User;

public class UserDTO {
	private String userName;
	private String bio;
	private String email;
	private String phone;
	private String role;
	
	public UserDTO() {
	}
	
	public UserDTO(String username, String bio, String email, String phone, String role) {
	    this.userName = username;
	    this.bio = bio;
	    this.email = email;
	    this.phone = phone;
	    this.role = role;
	}

	public static UserDTO fromEntity(User user) {
	    return new UserDTO(
	        user.getUserName(),
	        user.getBio(),
	        user.getEmail(),
	        user.getPhone(),
	        user.getRole().toString()
	        );
	}

	public String getUsername() {
		return userName;
	}

	public void setUsername(String username) {
		this.userName = username;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
