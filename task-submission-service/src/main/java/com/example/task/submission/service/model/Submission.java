package com.example.task.submission.service.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "submission_generator")
    @SequenceGenerator(name="submission_generator",initialValue = 1,allocationSize = 1)
    private Long id;

    private Long taskId;
    private Long userId;

    private String githubLink;
    private String status="PENDING";
    private LocalDateTime submissionTime;
}