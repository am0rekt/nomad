
package com.nomadnetwork.entity;
import jakarta.validation.constraints.NotBlank;

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

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @NotBlank(message= " email cannot be empty")
    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true)
    private String phone;

    private String bio;

    @Enumerated(EnumType.STRING)
    private Role role;
}
