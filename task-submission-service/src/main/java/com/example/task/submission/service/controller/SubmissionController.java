package com.example.task.submission.service.controller;

import com.example.task.submission.service.model.Submission;
import com.example.task.submission.service.model.UserDto;
import com.example.task.submission.service.service.interfac.SubmissionService;
import com.example.task.submission.service.service.interfac.TaskService;
import com.example.task.submission.service.service.interfac.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/submission")
public class SubmissionController {

    @Autowired
    SubmissionService submissionService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<Submission> submitTask(@RequestParam Long taskId,@RequestParam String githubLink, @RequestHeader("Authorization") String jwt)throws Exception{
        UserDto user=userService.getUserProfile(jwt);
        Submission submission=submissionService.submitTask(taskId,user.getId(),githubLink,jwt);
        return new ResponseEntity<>(submission, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Submission> getSubmissionById(@PathVariable Long id, @RequestHeader("Authorization") String jwt)throws Exception{
        UserDto user=userService.getUserProfile(jwt);
        Submission submission=submissionService.getTaskSubmissionById(id);
        return new ResponseEntity<>(submission, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<Submission>> getAllSubmission(@RequestHeader("Authorization") String jwt)throws Exception{
        UserDto user=userService.getUserProfile(jwt);
        List<Submission> submission=submissionService.getAllSubmissions();
        return new ResponseEntity<>(submission, HttpStatus.CREATED);
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<Submission>> getAllTaskSubmission(@PathVariable Long taskId, @RequestHeader("Authorization") String jwt)throws Exception{
        UserDto user=userService.getUserProfile(jwt);
        List<Submission> submission=submissionService.getTaskSubmissionsByTaskId(taskId);
        return new ResponseEntity<>(submission, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Submission> acceptOrDeclineSubmissiom(@PathVariable Long id,@RequestParam("status") String status,@RequestHeader("Authorization") String jwt)throws Exception{
        UserDto user=userService.getUserProfile(jwt);
        Submission submission=submissionService.acceptDeclineSubmission(id,status);
        return new ResponseEntity<>(submission, HttpStatus.CREATED);
    }
}
