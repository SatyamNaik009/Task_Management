package com.example.task.user.service.controller;

import com.example.task.user.service.model.User;
import com.example.task.user.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt){
        User user=userService.getUserProfile(jwt);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/profile/all")
    public ResponseEntity<List<User>> getAllUsers(@RequestHeader("Authorization") String jwt){
        List<User> user=userService.getAllUsers();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<User> getUserProfileById(@PathVariable("id") String id,@RequestHeader("Authorization") String jwt){
        User user=userService.getUserProfileById(Long.parseLong(id));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
