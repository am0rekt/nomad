package com.nomadnetwork.userController;

import com.nomadnetwork.dto.UserDTO;
import com.nomadnetwork.entity.User;
import com.nomadnetwork.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;




import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers()
        .stream()
        .map(UserDTO::fromEntity) 
        .toList(); 
    }

    @PostMapping("")
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }
}
