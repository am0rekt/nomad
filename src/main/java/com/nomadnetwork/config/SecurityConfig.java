package com.nomadnetwork.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.nomadnetwork.security.CustomAuthFailureHandler;

import lombok.RequiredArgsConstructor;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomAuthFailureHandler customAuthFailureHandler;

	
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
        .authorizeHttpRequests(auth -> auth

                // ✅ static resources
                .requestMatchers(
                    "/images/**",
                    "/css/**",
                    "/js/**",
                    "/uploads/**",
                    "/favicon.ico"
                ).permitAll()

                // ✅ public pages
                .requestMatchers("/register", "/otp/**", "/login","/page", "/verify").permitAll()

                // ✅ admin only
                .requestMatchers("/admin/**").hasRole("ADMIN")

                // ✅ everything else needs login
                .anyRequest().authenticated()
            )
        .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .failureHandler(customAuthFailureHandler)
                .permitAll()
        )

            .exceptionHandling(ex -> ex
                    .accessDeniedPage("/access-denied")
                )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );

        return http.build();
    }
}
