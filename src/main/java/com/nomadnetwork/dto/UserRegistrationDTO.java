package com.nomadnetwork.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRegistrationDTO {
	
	private String username;
	private String email;
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
