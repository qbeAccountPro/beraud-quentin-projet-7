package com.nnk.springboot.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Some javadoc :
 * 
 * This class represents the configuration of Spring Security.
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

  @Autowired DataSource dataSource;

  /**
   * Some javadoc :
   * 
   * This method represents the configuration of authentification fron a database.
   */
  @Autowired
  public void configAuthentification(AuthenticationManagerBuilder auth) throws Exception {
    auth.jdbcAuthentication()
        .passwordEncoder(new BCryptPasswordEncoder())
        .dataSource(dataSource)
        .usersByUsernameQuery("SELECT username, password, 'true' FROM users WHERE username=?")
        .authoritiesByUsernameQuery("SELECT username, 'true' FROM users WHERE username=?");
  }

  /**
   * Some javadoc :
   * 
   * This method represents the configuration of each authorized or not request on
   * the app.
   * Its detemine how and which file are authorized. The login page, the success
   * defauflt URL or many
   * other URL are dertimined here.
   * 
   * @param http represent the basic configuration.
   * 
   * @return the filter chain.
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http.authorizeHttpRequests(auth -> {
      auth.requestMatchers("/bidList/**").permitAll();
      auth.requestMatchers("/curvePoint/**").permitAll();
      auth.requestMatchers("/rating/**").permitAll();
      auth.requestMatchers("/ruleName/**").permitAll();
      auth.requestMatchers("/trade/**").permitAll();
      auth.requestMatchers("/user/**").permitAll();
      auth.requestMatchers("/403").permitAll();
      auth.requestMatchers("/home").permitAll();
      auth.requestMatchers("/css/**", "/js/**").permitAll();
      auth.anyRequest().authenticated();
    })
        .logout(logout -> logout
            .logoutUrl("/app-logout")
            .logoutSuccessUrl("/login?logout")
            .permitAll())

        .formLogin(form -> form
            .defaultSuccessUrl("/bidList/list", true)
            .permitAll());

    return http.build();
  }

  /**
   * Some javadoc :
   * 
   * Service interface for encoding passwords. The preferred implementation is
   * BCryptPasswordEncoder
   * 
   * @return the encoding passwords.
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}