package com.nomadnetwork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceimpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendOtpEmail(String toEmail, String otp) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Nomad Network – Verify Your Email");
        message.setText(
            "Welcome to Nomad Network 🌍\n\n" +
            "Your OTP is: " + otp + "\n\n" +
            "This OTP is valid for 5 minutes.\n\n" +
            "If you didn’t request this, ignore this email.\n\n" +
            "– Nomad Network Team"
        );

        mailSender.send(message);
    }
    @Override
    public void sendResetEmail(String toEmail, String resetLink) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Password Reset Request");
        message.setText(
                "Click the link below to reset your password:\n\n"
                + resetLink +
                "\n\nThis link expires in 10 minutes."
        );

        mailSender.send(message);
    }
}
