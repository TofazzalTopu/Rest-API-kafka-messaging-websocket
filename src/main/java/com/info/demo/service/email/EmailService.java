package com.info.demo.service.email;

import com.info.demo.config.email.EmailConfiguration;
import com.info.demo.constant.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private EmailConfiguration emailConfiguration;

    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
        mailSenderImpl.setHost(emailConfiguration.getHost());
        mailSenderImpl.setPort(emailConfiguration.getPort());
        mailSenderImpl.setUsername(emailConfiguration.getUsername());
        mailSenderImpl.setPassword(emailConfiguration.getPassword());
        return mailSenderImpl;
    }

    @Async
    public void sendSimpleMessage(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(AppConstants.EMAIL_SUBJECT);
        message.setText(AppConstants.EMAIL_CONTENT + email);
        message.setFrom(AppConstants.SYSTEM_EMAIL);
        getJavaMailSender().send(message);
        System.out.println("Success");
    }
}
