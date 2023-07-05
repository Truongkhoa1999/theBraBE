package com.example.thebra.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> findAllUsers () {
        return userRepository.findAll();
    }
    public User createNewUser (User user){
        return userRepository.save(user);
    }
}
