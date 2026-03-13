package com.sitesurvey.site_survey_backend.controller;

import com.sitesurvey.site_survey_backend.entity.User;
import com.sitesurvey.site_survey_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
    return userService.saveUser(user);
    }

    // Get All Users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    
    //login
    @PostMapping("/login")
    public String loginUser(@RequestBody User user) {
        return userService.login(user.getEmail(), user.getPassword());
    }

    // Get User By ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
       return userService.getUserById(id);
    }

    // Delete User
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
     userService.deleteUser(id);
    return "User deleted successfully";
}
}
