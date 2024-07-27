package com.example.task.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendMail(String toEmail){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom("drt7y89e9@gmail.com");
        message.setTo(toEmail);
        message.setText("you have been assigned a task");
        message.setSubject("Task Assigned");

        mailSender.send(message);
    }

}
