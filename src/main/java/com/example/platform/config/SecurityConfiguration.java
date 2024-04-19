package com.example.platform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    public SecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter, AuthenticationProvider authenticationProvider) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authenticationProvider = authenticationProvider;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrfCustomizer -> csrfCustomizer.disable())
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers( "/api/v1/demo/all").permitAll()
                        .requestMatchers( "/api/v1/demo/admin").hasAnyRole("ADMIN")
                        .requestMatchers( "/api/v1/demo/secure").authenticated()
                        .requestMatchers( "/api/v1/auth/**").permitAll()
                        .requestMatchers("/api/v1/**").authenticated()
                        .anyRequest().authenticated()
                )
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}
