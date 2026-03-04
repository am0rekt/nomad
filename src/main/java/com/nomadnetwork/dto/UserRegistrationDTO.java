package com.nomadnetwork.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRegistrationDTO {
	
	@NotBlank(message = "Username is required")
	@Size(min = 3, max = 20, message = "Username must be 3-20 characters")
	@Pattern(regexp = "^[a-z0-9_]+$",
	         message = "Username can contain only lowercase letters, numbers, and underscore")
	private String username;
	private String email;
	@NotBlank(message = "Phone number is required")
	@Pattern(regexp = "^[0-9]{10}$",
	         message = "Phone number must be exactly 10 digits")
	private String phone;
	private String password;
	private String confirmPassword;
	
	public UserRegistrationDTO(String username,String email,String phone,String password,String confirmPassword){
		this.username=username;
		this.email=email;
		
		
		this.phone=phone;
		this.password=password;
		this.confirmPassword = confirmPassword;
	}
	
}
