package com.nnk.springboot.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username")
    @NotBlank(message = "Username is mandatory")
    private String username;

    @Column(name = "password")
    @NotBlank(message = "Password is mandatory")
    @Pattern(regexp = ".*[A-Z].*", message = "Password must contain at least one uppercase letter.")
    @Pattern(regexp = ".*\\d.*", message = "Password must contain at least one digit.")
    @Pattern(regexp = ".*[@$!%*?&].*", message = "Password must contain at least one symbol among @$!%*?&.")
    @Pattern(regexp = ".{8,}", message = "Password must have a length of at least 8 characters.")
    private String password;

    @Column(name = "fullname")
    @NotBlank(message = "FullName is mandatory")
    private String fullname;

    @Column(name = "role")
    @NotBlank(message = "Role is mandatory")
    private String role;
}