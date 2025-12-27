package com.nomadnetwork.services;

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
}
