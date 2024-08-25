package org.example.project2.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.security.Principal;

public class SecurityUtils {
    public static String getUserIdFromPrincipal( ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            String preferredUsername = jwt.getClaimAsString("preferred_username");
            // Convert preferredUsername to userId if needed, or use directly if it's the user ID
            return preferredUsername; // Adjust this if needed
        }
        return null;
    }
}