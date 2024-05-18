package com.example.platform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
                        .requestMatchers( HttpMethod.GET,"/api/v1/resources/downloadGroup/**").permitAll()
                        .requestMatchers( HttpMethod.GET,"/api/v1/resources/download/**").permitAll()
                        .requestMatchers( "/api/v1/auth/register").permitAll()
                        .requestMatchers( "/api/v1/auth/authenticate").permitAll()
                        .requestMatchers( "/api/v1/auth/registerNewAdmin").hasAnyRole("ADMIN")
                        .requestMatchers( HttpMethod.GET,"/api/v1/users/**").hasAnyRole("ADMIN")
                        .requestMatchers( HttpMethod.GET,"/api/v1/users").hasAnyRole("ADMIN")
                        .requestMatchers( HttpMethod.DELETE,"/api/v1/users/**").hasAnyRole("ADMIN")
                        .requestMatchers( HttpMethod.PATCH,"/api/v1/users").authenticated()
                        .requestMatchers( HttpMethod.GET,"/api/v1/courses/**").permitAll()
                        .requestMatchers( HttpMethod.POST,"/api/v1/courses").hasAnyRole("ADMIN")
                        .requestMatchers( HttpMethod.GET,"/api/v1/courses").permitAll()
                        .requestMatchers( HttpMethod.GET,"/api/v1/courses/participants/**").authenticated()
                        .requestMatchers( HttpMethod.GET,"/api/v1/courses/myCourses").authenticated()
                        .requestMatchers( HttpMethod.GET,"/api/v1/courses/myCoursesAdmin").hasAnyRole("ADMIN")
                        .requestMatchers( HttpMethod.DELETE,"/api/v1/courses/**").hasAnyRole("ADMIN")
                        .requestMatchers( HttpMethod.POST,"/api/v1/invitations/join/**").hasAnyRole("USER")
                        .requestMatchers( HttpMethod.POST,"/api/v1/invitations/**").hasAnyRole("ADMIN")
                        .requestMatchers( HttpMethod.DELETE,"/api/v1/invitations/**").hasAnyRole("ADMIN")
                        .requestMatchers( HttpMethod.GET,"/api/v1/invitations/myInvitations").hasAnyRole("USER")
                        .requestMatchers( HttpMethod.GET,"/api/v1/invitations/myInvitationsAdmin").hasAnyRole("ADMIN")
                        .requestMatchers( HttpMethod.POST,"/api/v1/tasks/**").hasAnyRole("ADMIN")
                        .requestMatchers( HttpMethod.GET,"/api/v1/tasks/individualTask/**").authenticated()
                        .requestMatchers( HttpMethod.GET,"/api/v1/tasks/groupTask/**").authenticated()
                        .requestMatchers( HttpMethod.GET,"/api/v1/tasks/course/**").authenticated()
                        .requestMatchers( HttpMethod.GET,"/api/v1/tasks/myTasks").hasAnyRole("USER")
                        .requestMatchers( HttpMethod.GET,"/api/v1/tasks/myTasksAdmin").hasAnyRole("ADMIN")
                        .requestMatchers( HttpMethod.GET,"/api/v1/tasks/myUnsolvedTasksAdmin").hasAnyRole("ADMIN")
                        .requestMatchers( HttpMethod.PATCH,"/api/v1/tasks").hasAnyRole("ADMIN")
                        .requestMatchers( HttpMethod.DELETE,"/api/v1/tasks").hasAnyRole("ADMIN")
                        .requestMatchers( HttpMethod.POST,"/api/v1/resources/upload/**").authenticated()
                        .requestMatchers( HttpMethod.DELETE,"/api/v1/resources/**").authenticated()
                        .requestMatchers( HttpMethod.POST,"/api/v1/resources/uploadGroup/**").authenticated()
                        .requestMatchers( HttpMethod.DELETE,"/api/v1/resources/group/**").authenticated()
                        .requestMatchers( HttpMethod.GET,"/api/v1/resources/myFromTask/**").hasAnyRole("USER")
                        .requestMatchers( HttpMethod.GET,"/api/v1/resources/fromTask/**").hasAnyRole("ADMIN")
                        .requestMatchers( HttpMethod.GET,"/api/v1/resources/myFromGroupTask/**").hasAnyRole("USER")
                        .requestMatchers( HttpMethod.GET,"/api/v1/resources/fromGroupTask/**").hasAnyRole("ADMIN")
                        .requestMatchers("/api/v1/**").authenticated()
                        .anyRequest().authenticated()
                )
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}
