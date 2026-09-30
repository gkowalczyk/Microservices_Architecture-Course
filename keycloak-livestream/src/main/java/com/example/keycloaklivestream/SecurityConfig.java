package com.example.keycloaklivestream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity httpSecurity
    ) {
        httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/for-all").permitAll()
                        .requestMatchers("/for-debug").authenticated()
                        .requestMatchers("/for-user")
                        .hasAuthority("user-role")
                        .requestMatchers("/for-admin")
                        .hasAuthority("user-admin")
                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults()));
        return httpSecurity.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(keycloakClientRolesConverter());
        return converter;
    }

    private Converter<Jwt, Collection<GrantedAuthority>> keycloakClientRolesConverter() {

        return new Converter<Jwt, Collection<GrantedAuthority>>() {
            @Override
            public Collection<GrantedAuthority> convert(Jwt source) {
                Map<String, Object> resourceAccess = source.getClaimAsMap("resource_access");
                System.out.println(resourceAccess);
                //  {livestream-client={roles=[user-role]}, account={roles=[manage-account, manage-account-links, view-profile]}}
                Object clientObject = resourceAccess.get("livestream-client");

                if (!(clientObject instanceof Map<?, ?> clientAccess)) {
                    return List.of();
                }
                Object roleObject = clientAccess.get("roles");

                if (!(roleObject instanceof Collection<?> roles)) {
                    return List.of();
                }
                return roles.stream()
                        .map(Object::toString)
                        .map(SimpleGrantedAuthority::new)
                        .map(GrantedAuthority.class::cast)
                        .collect(Collectors.toList());
            }
        };
    }
}