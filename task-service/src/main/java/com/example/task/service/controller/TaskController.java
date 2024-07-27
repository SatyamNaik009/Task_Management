package com.example.task.service.controller;

import com.example.task.service.model.Task;
import com.example.task.service.model.TaskStatus;
import com.example.task.service.model.UserDto;
import com.example.task.service.service.interfac.TaskService;
import com.example.task.service.service.interfac.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task, @RequestHeader("Authorization") String jwt) throws Exception {
        UserDto user=userService.getUserProfile(jwt);
        Task createdTask=taskService.createTask(task,user.getRole());
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable("id") String id,@RequestHeader("Authorization") String jwt) throws Exception {
        UserDto user=userService.getUserProfile(jwt);
        Task task=taskService.getTaskById(Long.parseLong(id));
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Task>> getAssignedUsersTask(@RequestParam(required=false)TaskStatus status, @RequestHeader("Authorization") String jwt) throws Exception {
        UserDto user=userService.getUserProfile(jwt);
        List<Task> tasks=taskService.assignedUsersTask(user.getId(),status);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Task>> getALLTask(@RequestParam(required=false)TaskStatus status, @RequestHeader("Authorization") String jwt) throws Exception {
        UserDto user=userService.getUserProfile(jwt);
        List<Task> tasks=taskService.getAllTasks(status);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PutMapping("/{id}/user/{userid}/assign")
    public ResponseEntity<Task> assignedTaskToUser(@PathVariable Long id,@PathVariable Long userid, @RequestHeader("Authorization") String jwt) throws Exception {
        UserDto user=userService.getUserProfile(jwt);
        if(!user.getRole().equals("ROLE_ADMIN")){
            throw new Exception("ONLY admin can assign task");
        }
        Task tasks=taskService.assignedToUser(userid,id,jwt);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id,@RequestBody Task updatedTask, @RequestHeader("Authorization") String jwt) throws Exception {
        UserDto user=userService.getUserProfile(jwt);
        if(!user.getRole().equals("ROLE_ADMIN")){
            throw new Exception("ONLY admin can update task");
        }
        Task tasks=taskService.updateTask(id,updatedTask,user.getId());
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> completeTask(@PathVariable Long id) throws Exception {

        Task tasks=taskService.completeTask(id);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) throws Exception {

        taskService.deleteTask(id);
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }


}
