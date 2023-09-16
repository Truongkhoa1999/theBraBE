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
        message.setFrom("thebra.lingerie@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        mailSender.send(message);
        System.out.println("Mail sent");
    }

    public void reciveEmail (
            String gmail,
            String subject,
            String body
    ){
        String messageText = "Sender's Email: " + gmail + "\n\n" + body;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(gmail.toString().trim());
        message.setTo("thebra.lingerie@gmail.com");
        message.setText(messageText);
        message.setSubject(subject);
        mailSender.send(message);

        String replySubject = "No-reply";
        String replyBody = "Thank you for reaching us! We will get back to you soon.";
        sendEmail(gmail,replySubject,replyBody);

    }
}
