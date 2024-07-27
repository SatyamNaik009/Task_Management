package com.example.task.submission.service.service.impl;

import com.example.task.submission.service.model.Submission;
import com.example.task.submission.service.model.TaskDto;
import com.example.task.submission.service.model.UserDto;
import com.example.task.submission.service.repo.SubmissionRepository;
import com.example.task.submission.service.service.EmailService;
import com.example.task.submission.service.service.interfac.SubmissionService;
import com.example.task.submission.service.service.interfac.TaskService;
import com.example.task.submission.service.service.interfac.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SubmissionServiceImpl implements SubmissionService {
    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Override
    public Submission submitTask(Long taskId, Long userId, String githubLink,String jwt) throws Exception {
        TaskDto task=taskService.getTaskById(String.valueOf(taskId),jwt);
        if(task!=null){
            if(task.getAssignedUserId()==userId){
            Submission submission=new Submission();
            submission.setTaskId(taskId);
            submission.setGithubLink(githubLink);
            submission.setUserId(userId);
            submission.setSubmissionTime(LocalDateTime.now());
            UserDto user=userService.getUserProfileById(String.valueOf(userId),jwt);
            emailService.sendMail(user.getEmail());
            return submissionRepository.save(submission);
            }else{
                throw new Exception("You are not assigned this task");
            }
        }
        throw new Exception("Task not found");
    }

    @Override
    public Submission getTaskSubmissionById(Long submissionId) throws Exception {
        return submissionRepository.findById(submissionId).orElseThrow(()->new Exception("task submission not found with id"));
    }

    @Override
    public List<Submission> getAllSubmissions() {
        return submissionRepository.findAll();
    }

    @Override
    public List<Submission> getTaskSubmissionsByTaskId(Long taskId) {
        return submissionRepository.findByTaskId(taskId);
    }

    @Override
    public Submission acceptDeclineSubmission(Long id, String status) throws Exception {
        Submission submission=getTaskSubmissionById(id);
        submission.setStatus(status);
        if(status.equals("ACCEPT")) {
            taskService.completeTask(submission.getTaskId());
        }
        return submissionRepository.save(submission);
    }
}
