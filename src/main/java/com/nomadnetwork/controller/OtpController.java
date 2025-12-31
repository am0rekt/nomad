package com.nomadnetwork.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.nomadnetwork.services.OtpServiceimpl;


@Controller
@RequestMapping("/otp")
public class OtpController {
	
    private final OtpServiceimpl otpService;

    public OtpController(OtpServiceimpl otpService) {
        this.otpService = otpService;
    }

    // Show OTP page
    @GetMapping("/page")
    public String showOtpPage(@RequestParam String email, Model model) {
        model.addAttribute("email", email);
        return "otp-page"; // Thymeleaf template
    }

    // Verify OTP
    @PostMapping("/verify")
    public String verifyOtp(@RequestParam String email,
                            @RequestParam String otp,
                            Model model) {
    	 boolean verified = otpService.verifyOtp(email, otp);
        
    	 if (!verified) {
    	        model.addAttribute("email", email);
    	        model.addAttribute("error", "Invalid or expired OTP");
    	        return "otp-page";
    	    }

    	    return "redirect:/login?verified=true";
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
