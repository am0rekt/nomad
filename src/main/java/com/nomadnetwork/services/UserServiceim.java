package com.nomadnetwork.services;

import com.nomadnetwork.entity.User;
import com.nomadnetwork.userRepo.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceim implements UserService {

    @Autowired
    private UserRepos userRepo;

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User saveUser(User user) {
        return userRepo.save(user);
    }
}
