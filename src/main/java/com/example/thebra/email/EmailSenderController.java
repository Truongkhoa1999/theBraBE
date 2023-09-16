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
            String gmail = emailRequest.getGmail();
            String subject = emailRequest.getSubject();
            String message = emailRequest.getMessage();
            emailSenderService.reciveEmail(gmail, subject, message);
            System.out.println("send done");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ok";
    }
}
