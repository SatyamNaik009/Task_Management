package com.example.task.user.service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "user_generator")
    @SequenceGenerator(name="user_generator",initialValue = 4,allocationSize = 1)
    private Long id;

    private String email;
    private String password;
    private String role;
    private String fullName;
}
