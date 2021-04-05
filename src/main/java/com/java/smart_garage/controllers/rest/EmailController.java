package com.java.smart_garage.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/smartgarage/sendemail")
public class EmailController {

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping
    public String sendEmail() {
        sendMail();
        return "Email sent successfully";
    }

    private void sendMail() {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("karailiev1994@gmail.com");
        message.setTo("karailiev1994@gmail.com");
        message.setSubject("New Registered User Credentials");
        message.setText("Username: ....\n" +
                "Password: ....");

        mailSender.send(message);
    }

}