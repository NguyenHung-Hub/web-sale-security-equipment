package com.metan.websalesecurityequipment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String toEmail,String subject,String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("huyhoang14901@gmail.com");
        message.setBcc(toEmail);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
}
