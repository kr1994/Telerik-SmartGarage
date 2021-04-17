package com.java.smart_garage.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;
import java.util.Random;

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
        message.setTo("plamenchipev16@gmail.com");
        message.setSubject("New Registered User Credentials");

        // User -> Customer -> personal info {first name, last name, email, phoneNumber}
        // Api -> mail -> username, password {}


        // Link -> Forgotten password -> User writes mail -> Send new password
        message.setText("Username: ....\n" +
                        "Password: " + generateNewPassword());

        mailSender.send(message);
    }


    public String generateNewPassword() {
        byte[] array = new byte[8]; // length is bounded by 8
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));

        return generatedString;
    }

}