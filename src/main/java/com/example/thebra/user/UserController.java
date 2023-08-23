package com.example.thebra.user;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.example.thebra.utils.JwtUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/customers")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/signin")
    public Map<String, String> login(@RequestBody AuthenticateRequest authenticateRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticateRequest.getUsername(),
                        authenticateRequest.getPassword()
                )
        );
        User user = userRepository.findByUsername(authenticateRequest.getUsername());
        Map<String, String> token = new HashMap<>();
        token.put("token", jwtUtils.generateToken(user));
        return token;
    }

    @PostMapping("/signup")
    public Map<String, String> signup(@RequestBody User user) {
        User newUser = new User(
                passwordEncoder.encode(user.getPassword()),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhone(),
                user.getGmail(),
                User.Role.USER
        );
        userService.createNewUser(newUser);
        Map<String, String> token = new HashMap<>();
        token.put("token", jwtUtils.generateToken(newUser));
        return token;
    }

    @PostMapping("/pregister")
    public String checkIfRegisterInfoValid(@RequestBody User newUser) {
        List<User> users = userRepository.findAll();

        for (User existingUser : users) {
            if (existingUser.getUsername().equals(newUser.getUsername())) {
                return "Username already exists";
            }
            if(existingUser.getGmail().equalsIgnoreCase(newUser.getGmail())){
                return "Gmail already assigned with another account";
            }
        }
        return "Passed";

    }
}
