package com.example.task.service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "task_generator")
    @SequenceGenerator(name="task_generator",initialValue = 1,allocationSize = 1)
    private Long id;

    private String title;
    private String description;
    private Long assignedUserId;
    private List<String> tags=new ArrayList<>();
   // @Enumerated(EnumType.STRING)
    private TaskStatus status;
    private LocalDateTime deadline;
    private LocalDateTime createdAt;
}
