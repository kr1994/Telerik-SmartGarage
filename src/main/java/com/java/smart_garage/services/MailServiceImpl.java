package com.java.smart_garage.services;

import com.java.smart_garage.configuration.Md5Hashing;
import com.java.smart_garage.contracts.serviceContracts.MailService;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.Random;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendMailForCredentials(String email, String username, String password) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("karailiev1994@gmail.com");
        message.setTo("plamenchipev16@gmail.com");
        message.setSubject("New Registered User Credentials");
        message.setText("Username: " + username + "\n" + "Password: " + password);

        mailSender.send(message);
    }

    public void sendMailForNewPassword(String email, String password) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("karailiev1994@gmail.com");
        message.setTo("plamenchipev16@gmail.com");
        message.setSubject("New Password");


        // Link -> Forgotten password -> User writes mail -> Send new password
        message.setText("Password: " + password);

        mailSender.send(message);
    }

}
