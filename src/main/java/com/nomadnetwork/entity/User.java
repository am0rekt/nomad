package com.nomadnetwork.entity;

import java.util.List;

import com.nomadnetwork.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userID;
    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
 private List<Post> posts;

    @Column(nullable = false,name = "username")
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true)
    private String phone;

    private String bio;

    @Enumerated(EnumType.STRING)
    private Role role;
    
    @Column(nullable = false)
    private boolean enabled=false;
    
    @Column(nullable= false)
    private boolean deleted=false;
    
    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
 private List<Otp> otps;
}
