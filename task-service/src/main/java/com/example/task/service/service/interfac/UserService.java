package com.example.task.service.service.interfac;

import com.example.task.service.model.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="USER-SERVICE",url="http://localhost:5001")
public interface UserService {

    @GetMapping("/api/user/profile")
    public UserDto getUserProfile(@RequestHeader("Authorization") String jwt);

    @GetMapping("/api/user/profile/{id}")
    public UserDto getUserProfileById(@PathVariable("id") String id,@RequestHeader("Authorization") String jwt);

}
