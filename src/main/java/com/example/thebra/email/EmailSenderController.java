package com.example.thebra.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/email")
public class EmailSenderController {
    @Autowired
    EmailSenderService emailSenderService;

    @GetMapping("/")
    public String sendCustomEmailtoMyBussiness(@RequestBody EmailRequest emailRequest) {
        try {
            String clientEmail = emailRequest.getEmail();
            String subject = emailRequest.getSubject();
            String message = emailRequest.getMessage();
            emailSenderService.reciveEmail(clientEmail, subject, message);
            System.out.println("send done");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ok";
    }
}
