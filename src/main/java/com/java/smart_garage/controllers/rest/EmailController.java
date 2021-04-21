package com.java.smart_garage.controllers.rest;

import com.java.smart_garage.contracts.serviceContracts.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/smartgarage/sendemail")
public class EmailController {

    private EmailService emailService;

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping
    public String sendEmail() {
        //emailService.sendMailForCredentials();
        return "Email sent successfully";
    }


}