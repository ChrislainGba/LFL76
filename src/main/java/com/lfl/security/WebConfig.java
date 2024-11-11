package com.lfl.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Allow all endpoints
                .allowedOrigins("http://localhost:4200","http://localhost:4201","http://localhost:3333")  // Allow your Angular app's URL
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow specific HTTP methods
                .allowedHeaders("*")  // Allow all headers
                .exposedHeaders("Authorization")  // Expose Authorization header so Angular can read it
                .allowCredentials(true);  // Allow cookies or authorization headers
    }
}