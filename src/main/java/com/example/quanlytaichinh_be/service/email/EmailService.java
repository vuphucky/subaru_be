package com.example.quanlytaichinh_be.service.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

    @Service
    public class EmailService {

        @Autowired
        private JavaMailSender mailSender;

        public void sendEmail(String to, String subject, String body) {
            try {
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);

                helper.setTo(to);
                helper.setSubject(subject);
                helper.setText(body, true);

                mailSender.send(message);
            } catch (MessagingException e) {
                System.err.println("Error sending email: " + e.getMessage());
                throw new RuntimeException("Failed to send email", e);
            }
        }
    }


