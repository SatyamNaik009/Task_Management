package com.example.task.user.service.controller;

import com.example.task.user.service.config.JwtProvider;
import com.example.task.user.service.model.User;
import com.example.task.user.service.repo.UserRepository;
import com.example.task.user.service.response.AuthResponse;
import com.example.task.user.service.response.LoginRequest;
import com.example.task.user.service.service.CustomUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserServiceImpl customUserDetails;
    @Autowired
    PasswordEncoder passwordEncoder;
    //@Autowired
    //private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody User user) throws Exception{
        String email=user.getEmail();
        System.out.println(email);
        String password= user.getPassword();
        String role = user.getRole();
        String fullName = user.getFullName();


        User isEmailExist=userRepository.findByEmail(email);
        if(isEmailExist!=null){
            throw new Exception("Email already exits");
        }

        user.setPassword(passwordEncoder.encode(password));
        User savedUser = userRepository.save(user);

        Authentication authentication=new UsernamePasswordAuthenticationToken(email,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token= JwtProvider.generateToken(authentication);

        AuthResponse authResponse=new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Register Success");
        authResponse.setStatus(true);

        return new ResponseEntity<>(authResponse, HttpStatus.OK);

    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest loginRequest) throws Exception{

        String email=loginRequest.getEmail();
        String password= loginRequest.getPassword();

        Authentication authentication=authenticate(email,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token= JwtProvider.generateToken(authentication);

        AuthResponse authResponse=new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Login Success");
        authResponse.setStatus(true);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails=customUserDetails.loadUserByUsername(username);
        if(userDetails==null){
            throw new BadCredentialsException("Invalid username or password");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Invalid username or password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}