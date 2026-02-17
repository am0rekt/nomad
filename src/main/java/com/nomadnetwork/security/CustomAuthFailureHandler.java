package com.nomadnetwork.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.nomadnetwork.entity.User;
import com.nomadnetwork.repository.UserRepos;
import com.nomadnetwork.services.OtpServiceimpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private UserRepos userRepo;

    @Autowired
    private OtpServiceimpl otpService;

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception)
            throws IOException {

        if (exception instanceof DisabledException) {

        	String email = request.getParameter("username");
        	
            User user = userRepo.findByEmail(email).orElse(null);

            if (user != null) {
            	otpService.resendOtp(email);  // 🔥 SEND OTP HERE
            }

            response.sendRedirect("/otp/page?email=" + email);
            return;
        }

        response.sendRedirect("/login?error");
    }
}

