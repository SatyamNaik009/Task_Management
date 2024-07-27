package com.example.task.user.service.service;

import com.example.task.user.service.config.JwtProvider;
import com.example.task.user.service.model.User;
import com.example.task.user.service.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;
    @Override
    public User getUserProfile(String jwt) {
        String email= JwtProvider.getEmailFromJwtToken(jwt);
        return userRepository.findByEmail(email);
    }
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    @Override
    public User getUserProfileById(Long id){
        return userRepository.findById(id).get();
    }
}
