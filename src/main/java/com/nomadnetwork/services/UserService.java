package com.nomadnetwork.services;

import com.nomadnetwork.entity.User;
import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User saveUser(User user);
}
