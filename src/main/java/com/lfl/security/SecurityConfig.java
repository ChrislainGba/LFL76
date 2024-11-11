package com.lfl.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.lfl.service.AccountService;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final JWTUtil jwtUtil;
	private final UserDetailServiceImpl userDetailService;
	
	public SecurityConfig(JWTUtil jwtUtil, UserDetailServiceImpl userDetailService) {
        this.jwtUtil = jwtUtil;
        this.userDetailService = userDetailService;
    }
	
	 @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        
        JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter(authenticationManager, jwtUtil);
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);

        http.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authz -> authz
            	.requestMatchers("/login").permitAll() 
            	.requestMatchers("/educator/**").hasRole("educator")
                .requestMatchers("/admin /**").hasRole("admin")
                .anyRequest().authenticated() // Protect all other API endpoints
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilter(jwtAuthenticationFilter) // Add the authentication filter
            .addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class); // Add the authorization filter

        return http.build();
    }
   
}