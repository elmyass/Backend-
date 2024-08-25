package org.example.project2.security;


import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.*;

public class CustomJwtGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {


    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        getAuthorities(source).forEach(authority -> authorities.add(new SimpleGrantedAuthority("ROLE_"+authority)));
        return authorities;
    }

    private Collection<String> getAuthorities(Jwt jwt) {
        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
        if (resourceAccess == null) {
            System.out.printf("no ressource access");
            return  Collections.emptyList();
        }
       Map<String,Object> projet2 = (Map<String, Object>) resourceAccess.get("projet2");
        if (projet2 == null) {
            System.out.printf("no project2 found");
            return  Collections.emptyList();
        }
        Collection<String> roles = (Collection<String>) projet2.get("roles");
        if (roles == null) {
            System.out.printf("no roles found");
            return  Collections.emptyList();
        }
        return roles;
    }
}
