package com.java.smart_garage.controllers.rest;

import com.java.smart_garage.contracts.serviceContracts.MailService;
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

    private MailService mailService;

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping
    public String sendEmail() {
        //mailService.sendMailForCredentials();
        return "Email sent successfully";
    }


}