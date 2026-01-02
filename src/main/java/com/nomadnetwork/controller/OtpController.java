package com.nomadnetwork.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nomadnetwork.entity.Otp;
import com.nomadnetwork.entity.User;
import com.nomadnetwork.repository.OtpRepository;
import com.nomadnetwork.services.OtpServiceimpl;
import com.nomadnetwork.services.UserService;



@Controller
@RequestMapping("/otp")
public class OtpController {
	
    private final OtpServiceimpl otpService;
    private final UserService userService;
    private final OtpRepository otpRepository;

    public OtpController(OtpServiceimpl otpService,
    		UserService userService,
            OtpRepository otpRepository) {
        this.otpService = otpService;
        this.userService = userService;
        this.otpRepository = otpRepository;
    }

    // Show OTP page
    @GetMapping("/page")
    public String showOtpPage(@RequestParam String email, Model model) {

        User user = userService.findByEmail(email);
        Otp otp = otpRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("OTP not found"));
        
        model.addAttribute("email", email);
        model.addAttribute("expiryTime", otp.getExpiryTime().toString());
        return "otp-page"; // Thymeleaf template
    }

    // Verify OTP
    @PostMapping("/verify")
    public String verifyOtp(@RequestParam String email,
                            @RequestParam String otp,
                            Model model) {

        try {
            otpService.verifyOtp(email, otp);
            return "redirect:/login?verified=true";

        } catch (RuntimeException e) {
            model.addAttribute("email", email);
            model.addAttribute("error", e.getMessage());
            return "otp-page";
        }
    }
    
    @GetMapping("/resend")
    public String resendOtp(@RequestParam String email, RedirectAttributes redirectAttributes) {
        try {
            otpService.resendOtp(email);
            redirectAttributes.addFlashAttribute("success", "OTP resent successfully");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/register";
        }
        return "redirect:/otp/page?email=" + email;
    }
}
