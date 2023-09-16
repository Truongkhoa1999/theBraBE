package com.example.thebra.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/email")
public class EmailSenderController {
    @Autowired
    EmailSenderService emailSenderService;

    @PostMapping("/client")
    public String sendCustomEmailtoMyBussiness(@RequestBody EmailRequest emailRequest) {
        try {
            String clientEmail = emailRequest.getGmail();
            String subject = emailRequest.getSubject();
            String body = emailRequest.getBody();
            emailSenderService.reciveEmail(clientEmail, subject, body);
            System.out.println("send done");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ok";
    }
}
