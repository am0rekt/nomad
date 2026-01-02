package com.nomadnetwork.controller;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.nomadnetwork.dto.UserRegistrationDTO;
import com.nomadnetwork.entity.Otp;
import com.nomadnetwork.entity.User;
import com.nomadnetwork.repository.OtpRepository;
import com.nomadnetwork.services.EmailService;
import com.nomadnetwork.services.UserService;

@Controller
public class RegisterUserController {
	@Autowired
    private UserService userService;
	
	@Autowired
	private OtpRepository otpRepository;
	
	@Autowired
	private EmailService emailService;
	
	 @GetMapping("/register")
	 public String showRegistrationForm(Model model) {
	        model.addAttribute("userDTO", new UserRegistrationDTO());
	        return "user/register";
	 }
	 
	 @PostMapping("/register")
	    public String registerUser(@ModelAttribute("userDTO") UserRegistrationDTO dto, Model model) {
	        try {
	            User user = userService.registerUser(dto); // Save user with enabled=false

	            // Generate OTP
	            String otpCode = String.valueOf(new Random().nextInt(900000) + 100000);
	            Otp otp = new Otp();
	            otp.setCode(otpCode);
	            otp.setCreatedAt(LocalDateTime.now());
	            otp.setExpiryTime(LocalDateTime.now().plusMinutes(5));
	            otp.setUser(user);
	            otpRepository.save(otp);
	            emailService.sendOtpEmail(user.getEmail(), otpCode);


	            System.out.println("OTP for user " + user.getEmail() + ": " + otpCode); // for testing

	            // Redirect to OTP page
	            return "redirect:/otp/page?email=" + user.getEmail();
	        } catch (RuntimeException e) {
	            model.addAttribute("error", e.getMessage());
	            return "user/register";
	        }
	    }
}
