package com.nomadnetwork.services;

import com.nomadnetwork.dto.UserRegistrationDTO;
import com.nomadnetwork.entity.User;
import com.nomadnetwork.enums.Role;
import com.nomadnetwork.repository.UserRepos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceim implements UserService {

	@Autowired
    private  UserRepos userRepo ;
	
	@Autowired
    private  PasswordEncoder passwordEncoder;
    


    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User saveUser(User user) {
        return userRepo.save(user);
    }

	@Override
	public User registerUser(UserRegistrationDTO dto) {
		if (userRepo.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered: " + dto.getEmail());
        }

        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match!");
        }

        User user = new User();
        user.setUserName(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());

        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        user.setRole(Role.USER);

        return userRepo.save(user);
	}

	@Override
	public User findByEmail(String email) {
		return userRepo.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("User not found with email: " + email));
	}
	@Override
	public User getCurrentUser() {
	    String email = SecurityContextHolder.getContext()
	                    .getAuthentication().getName();
	    return userRepo.findByEmail(email)
	            .orElseThrow();
	}

	@Override
	public void updateProfile(User form) {
	    User user = getCurrentUser();

	    user.setUserName(form.getUserName());
	    user.setPhone(form.getPhone());
	    user.setBio(form.getBio());

	    userRepo.save(user);
	}
}
