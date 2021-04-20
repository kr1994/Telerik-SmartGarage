package com.java.smart_garage.services;

import com.java.smart_garage.contracts.serviceContracts.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping
    public void sendMailForCredentials(String email, String username, String password) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("karailiev1994@gmail.com");
        message.setTo(email);
        message.setSubject("New Registered User Credentials");
        message.setText("Username: " + username + "\n" + "Password: " + password);

        mailSender.send(message);
    }

    @PostMapping
    public void sendMailForNewPassword(String email, String password) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("karailiev1994@gmail.com");
        message.setTo(email);
        message.setSubject("New Password");

        // Link -> Forgotten password -> User writes mail -> Send new password
        message.setText("Password: " + password);

        mailSender.send(message);
    }

}
