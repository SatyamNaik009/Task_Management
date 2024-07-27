package com.example.task.submission.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendMail(String fromEmail){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo("drt7y89e9@gmail.com");
        message.setText("Task has been submitted");
        message.setSubject("Task submitted");

        mailSender.send(message);
    }

}
