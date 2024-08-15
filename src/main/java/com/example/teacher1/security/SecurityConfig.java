package com.example.teacher1.security;


import com.example.teacher1.jwt.JwtAuthenticationEntryPoint;
import com.example.teacher1.jwt.JwtAuthenticationFilter;
import com.example.teacher1.service.CustomUserDetailSService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.net.http.HttpClient;

@Configuration
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig {
    private final CustomUserDetailSService customUserDetailsService;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final JwtAuthenticationFilter authenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
  http.csrf(csrf->csrf.disable())
          .authorizeHttpRequests(authorize ->{
              authorize.requestMatchers("/blog/auth/**").permitAll();
              authorize.requestMatchers("/blog/admin").hasRole("ADMIN");
              authorize.requestMatchers("/blog/user").hasRole("USER");
              authorize.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
              authorize.anyRequest().authenticated();
          })
          .exceptionHandling(exception -> exception
                  .authenticationEntryPoint(authenticationEntryPoint))
          .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


 @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticate) throws Exception{
     return  authenticate.getAuthenticationManager();
 }
}
