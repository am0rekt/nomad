package com.nomadnetwork.dto;
import com.nomadnetwork.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
	private Long userID;
	
	@NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", 
             message = "Username can only contain letters, numbers, and underscore")
	private String userName;
	private String bio;
	private String email;
	
	@NotBlank
    @Pattern(
        regexp = "^\\+[1-9][0-9]{7,14}$",
        message = "Phone number must be in international format (+countrycode...)"
    )
	private String phone;
	private String role;
	private boolean enabled;
	
	public UserDTO() {
	}
	
	public UserDTO(Long userID,String userName, String bio, String email, String phone, String role,boolean enabled) {
		this.userID=userID;
	    this.userName = userName;
	    this.bio = bio;
	    this.email = email;
	    this.phone = phone;
	    this.role = role;
	    this.enabled = enabled;
	}

	public static UserDTO fromEntity(User user) {
	    return new UserDTO(
	    	user.getUserID(),
	        user.getUserName(),
	        user.getBio(),
	        user.getEmail(),
	        user.getPhone(),
	        user.getRole().toString(),
	        user.isEnabled()
	        );
	}

}