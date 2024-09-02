package org.example.project2.config;

import org.example.project2.security.CustomAuthenticationConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //http.authorizeRequests().requestMatchers("/products").permitAll();
        //http.authorizeRequests().requestMatchers("/products/{id}").permitAll();
       // http.authorizeRequests().anyRequest().authenticated();
        http.authorizeRequests().anyRequest().permitAll();
        http.oauth2ResourceServer().jwt().jwtAuthenticationConverter(new CustomAuthenticationConverter());
        return http.build();
    }



}
