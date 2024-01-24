package com.nnk.springboot.configuration;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class SpringSecurityConfigTest {

  @Test
  void testPasswordEncoder() {
    // Arrange
    SpringSecurityConfig securityConfig = new SpringSecurityConfig();

    // Act
    PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();

    // Assert
    assertInstanceOf(BCryptPasswordEncoder.class, passwordEncoder);

  }
}
