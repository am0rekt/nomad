package com.nomadnetwork.services;

import com.nomadnetwork.dto.UserRegistrationDTO;
import com.nomadnetwork.entity.User;
import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User saveUser(User user);
    User registerUser(UserRegistrationDTO dto);  // registration workflow
    User findByEmail(String email);
}
