package com.nomadnetwork.services;


public interface EmailService {
	    void sendOtpEmail(String toEmail, String otp);
	    public void sendResetEmail(String toEmail, String resetLink);
	}

