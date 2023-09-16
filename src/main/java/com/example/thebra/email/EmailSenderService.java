package com.example.thebra.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;
    public void sendEmail (
            String toEmail,
            String subject,
            String body
    ){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("tdkhoa.aqua@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        mailSender.send(message);
        System.out.println("Mail sent sucessfully");
    }

    public void reciveEmail (
            String gmail,
            String subject,
            String message
    ){
        SimpleMailMessage messagE = new SimpleMailMessage();
        messagE.setFrom(gmail);
        messagE.setTo("tdkhoa.auqa@gmail.com");
        messagE.setText(message);
        messagE.setSubject(subject);
        mailSender.send(messagE);
        System.out.println("Mail sent sucessfully");
    }
}
