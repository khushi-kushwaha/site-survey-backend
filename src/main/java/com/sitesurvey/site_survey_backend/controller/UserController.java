package com.sitesurvey.site_survey_backend.controller;

import com.sitesurvey.site_survey_backend.entity.User;
import com.sitesurvey.site_survey_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    @Autowired
    private UserService userService;

    // Register
    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    // Get All Users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // 🔥 LOGIN (UPDATED)
    @PostMapping("/login")
    public Map<String, Object> loginUser(@RequestBody User user) {

        // token generate hoga service se
        String token = userService.login(user.getEmail(), user.getPassword());

        // user find karo (role nikalne ke liye)
        User dbUser = userService.getUserByEmail(user.getEmail());

        return Map.of(
                "token", token,
                "role", dbUser.getRole(),
                "email", dbUser.getEmail()
        );
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