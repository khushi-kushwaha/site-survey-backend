package com.sitesurvey.site_survey_backend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import com.sitesurvey.site_survey_backend.entity.User;
import com.sitesurvey.site_survey_backend.repository.UserRepository;
import com.sitesurvey.site_survey_backend.exception.AuthException;
import com.sitesurvey.site_survey_backend.jwt.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
// import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

   
    public User saveUser(User user) {

    // Check duplicate email
    if(userRepository.existsByEmail(user.getEmail())){
        throw new RuntimeException("Email already registered");
    }

    // Encrypt password
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setOrganizationId(1L); // Set default organization ID (or handle as needed)

    // Save user
    return userRepository.save(user);
}


   public String login(String email, String password) {

    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new AuthException("User not found"));

    if (passwordEncoder.matches(password, user.getPassword())) {

        return jwtUtil.generateToken(user.getEmail());

    } else {
        throw new AuthException("Invalid Credentials");
    }
   }

   public User getUserById(Long id) {
    return userRepository.findById(id).orElse(null);
    }

    public User getUserByEmail(String email) {
    return userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
}

   public void deleteUser(Long id) {
    userRepository.deleteById(id);
    }
}