package com.nomadnetwork.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Post> posts;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true)
    private String phone;

    private String bio;

    @Enumerated(EnumType.STRING)
    private Role role;
    
    private boolean enabled;
}
