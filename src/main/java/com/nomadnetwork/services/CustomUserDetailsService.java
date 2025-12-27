package com.nomadnetwork.services;

import com.nomadnetwork.entity.User;
import com.nomadnetwork.repository.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepos userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        if (!user.isEnabled()) {
            throw new DisabledException("Account not verified");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), // login using email
                user.getPassword(), // hashed password
                true,   // enabled (already checked)
                true,   // accountNonExpired
                true,   // credentialsNonExpired
                true,
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );
    }
}
