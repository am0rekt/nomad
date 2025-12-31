package com.nomadnetwork.services;

import java.time.LocalDateTime;
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
    
    @Autowired
    private UserService userService;

    public boolean verifyOtp(String email, String enteredOtp) {

    	User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return false;
        }

        Otp otp = otpRepository.findByUser(user).orElse(null);
        if (otp == null) {
            return false;
        }

        if (otp.isExpired()) {
            return false;
        }

        if (!otp.getCode().equals(enteredOtp)) {
            return false;
        }

        // OTP is correct
        user.setEnabled(true);
        userRepository.save(user);

        otpRepository.delete(otp);

        return true;


    }
    
    public void resendOtp(String email) {

        User user = userService.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // delete old OTP
        otpRepository.findByUser(user)
        .ifPresent(otpRepository::delete);;

        // generate OTP
        String otpCode = String.valueOf(
                new Random().nextInt(900000) + 100000
        );

        Otp otp = new Otp();
        otp.setCode(otpCode);
        otp.setUser(user);
        otp.setExpiryTime(LocalDateTime.now().plusMinutes(5));
        otpRepository.save(otp);

        // send email
        emailService.sendOtpEmail(user.getEmail(), otpCode);
        System.out.println("Resent OTP for " + user.getEmail() + ": " + otpCode);

    }
}
