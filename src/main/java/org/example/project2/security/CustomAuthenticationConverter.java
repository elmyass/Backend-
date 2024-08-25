package org.example.project2.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;


public class CustomAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {


    public CustomAuthenticationConverter() {
        System.out.printf("CustomAuthenticationConverter constructor");
    }


    CustomJwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new CustomJwtGrantedAuthoritiesConverter();
    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        String username =jwt.getClaimAsString("preferred_username");
        // JwtAuthenticationToken contient les roles + jwt +username pour ne pa refaire ceci plusieurs fois . once you login cela ce fait
        return new JwtAuthenticationToken(jwt, jwtGrantedAuthoritiesConverter.convert(jwt), username );
    }
}
