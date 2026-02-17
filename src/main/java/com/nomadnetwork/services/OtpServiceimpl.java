package com.nomadnetwork.services;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nomadnetwork.entity.Otp;
import com.nomadnetwork.entity.User;
import com.nomadnetwork.repository.OtpRepository;
import com.nomadnetwork.repository.UserRepos;

@Service
public class OtpServiceimpl {
	private final UserRepos userRepository;
    private final OtpRepository otpRepository ;

    public OtpServiceimpl(UserRepos userRepository,
            OtpRepository otpRepository) {
    	this.userRepository = userRepository;
    	this.otpRepository = otpRepository;
    }

    @Autowired
    private EmailService emailService;
    

    public void verifyOtp(String email, String enteredOtp) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Otp otp = otpRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("OTP not found"));

        if (otp.isExpired()) {
            throw new RuntimeException("OTP expired");
        }

        if (!otp.getCode().equals(enteredOtp)) {
            throw new RuntimeException("Invalid OTP");
        }

        user.setEnabled(true);
        userRepository.save(user);
        otpRepository.delete(otp);
    }
    
    public void resendOtp(String email) {

    	User user = userRepository.findByEmail(email)
    	        .orElseThrow(() -> new RuntimeException("User not found"));

        
        
        if (user.isEnabled()) {
            throw new RuntimeException("Account already verified");
        }

        Optional<Otp> existingOtp = otpRepository.findByUser(user);

        if (existingOtp.isPresent()) {
            Otp otp = existingOtp.get();

            if (!otp.canResend()) {
                throw new RuntimeException("Please wait 30 seconds before resending OTP");
            }

            otpRepository.delete(otp);
        }

        // generate OTP
        String otpCode = String.valueOf(
                new Random().nextInt(900000) + 100000
        );

        Otp otp = new Otp();
        otp.setCode(otpCode);
        otp.setUser(user);
        otp.setCreatedAt(LocalDateTime.now());
        otp.setExpiryTime(LocalDateTime.now().plusMinutes(3));
        otpRepository.save(otp);

        // send email
        emailService.sendOtpEmail(user.getEmail(), otpCode);
        System.out.println("Resent OTP for " + user.getEmail() + ": " + otpCode);

    }
}
