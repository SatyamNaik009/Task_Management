package com.example.task.submission.service.service.interfac;

import com.example.task.submission.service.model.Submission;

import java.util.List;

public interface SubmissionService {
    Submission submitTask(Long taskId,Long userId,String githubLink,String jwt)throws Exception;

    Submission getTaskSubmissionById(Long submissionId)throws Exception;

    List<Submission> getAllSubmissions();

    List<Submission> getTaskSubmissionsByTaskId(Long taskId);

    Submission acceptDeclineSubmission(Long id,String status)throws Exception;
}
