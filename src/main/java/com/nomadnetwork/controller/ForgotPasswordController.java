package com.nomadnetwork.controller;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nomadnetwork.entity.PasswordResetToken;
import com.nomadnetwork.entity.User;
import com.nomadnetwork.repository.PasswordResetTokenRepository;
import com.nomadnetwork.repository.UserRepos;
import com.nomadnetwork.services.EmailService;
import com.nomadnetwork.services.PasswordResetService;

@Controller
public class ForgotPasswordController {

    private final UserRepos userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private PasswordResetService resetService;

    public ForgotPasswordController(UserRepos userRepository,
                                    PasswordResetTokenRepository tokenRepository,
                                    PasswordEncoder passwordEncoder,
                                    EmailService emailService,
                                    PasswordResetService resetService) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.resetService=resetService;
    }

    // 1️⃣ Show forgot password page
    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password";
    }

    // 2️⃣ Handle email submission
    @PostMapping("/forgot-password")
    public String processForgotPassword(
            @RequestParam("email") String email,
            Model model) {

        Optional<User> optionalUser = userRepository.findByEmail(email);

        // Always show same message (security best practice)
        model.addAttribute("message",
                "If the email exists, a reset link has been sent.");

        if (optionalUser.isEmpty()) {
            return "forgot-password";
        }

        User user = optionalUser.get();

        // Transactional service call
        PasswordResetToken token = resetService.createResetTokenForUser(user);

        // Send reset link
        String resetLink = "http://localhost:8080/reset-password?token=" + token.getToken();
        emailService.sendResetEmail(user.getEmail(), resetLink);

        return "forgot-password";
    }

    // 3️⃣ Show reset password form
    @GetMapping("/reset-password")
    public String showResetPasswordForm(
            @RequestParam("token") String token,
            Model model) {

        Optional<PasswordResetToken> tokenOptional =
                tokenRepository.findByToken(token);

        if (tokenOptional.isEmpty()) {
            return "invalid-token";
        }

        PasswordResetToken resetToken = tokenOptional.get();

        if (resetToken.getExpiryTime().isBefore(LocalDateTime.now())) {
            return "expired-token";
        }

        model.addAttribute("token", token);
        return "reset-password";
    }

    // 4️⃣ Handle new password submission
    @PostMapping("/reset-password")
    public String resetPassword(
            @RequestParam("token") String token,
            @RequestParam("password") String password,
            Model model) {

        Optional<PasswordResetToken> tokenOptional =
                tokenRepository.findByToken(token);

        if (tokenOptional.isEmpty()) {
            return "invalid-token";
        }

        PasswordResetToken resetToken = tokenOptional.get();

        if (resetToken.getExpiryTime().isBefore(LocalDateTime.now())) {
            return "expired-token";
        }

        User user = resetToken.getUser();

        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        // Delete token after successful reset
        tokenRepository.delete(resetToken);

        return "redirect:/login?resetSuccess";
    }
}