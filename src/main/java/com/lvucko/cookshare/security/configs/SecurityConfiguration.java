package com.lvucko.cookshare.security.configs;

import com.lvucko.cookshare.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST,"/api/users/login").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/users/register").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/users/*").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/users/personal").hasAnyAuthority(Role.USER.name(), Role.MODERATOR.name(), Role.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT, "/api/users").hasAnyAuthority(Role.USER.name(), Role.MODERATOR.name(), Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET,"/api/users/*/comments").hasAnyAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.POST,"/api/users/*/role/*").hasAnyAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/api/recipes/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/categories").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/categories").hasAnyAuthority(Role.MODERATOR.name(), Role.ADMIN.name())
                        .requestMatchers(HttpMethod.POST, "/api/categories").hasAnyAuthority(Role.MODERATOR.name(), Role.ADMIN.name())
                        .requestMatchers(HttpMethod.POST, "/api/upload").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/upload").hasAnyAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.POST, "/api/recipes").hasAnyAuthority(Role.USER.name(), Role.MODERATOR.name(), Role.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT, "/api/recipes").hasAnyAuthority(Role.USER.name(), Role.MODERATOR.name(), Role.ADMIN.name())
                        .requestMatchers(HttpMethod.POST, "/api/recipes/*/comments").hasAnyAuthority(Role.USER.name(), Role.MODERATOR.name(), Role.ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE, "/api/recipes/**").hasAnyAuthority(Role.USER.name(), Role.MODERATOR.name(), Role.ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE, "/api/recipes/comment/**").hasAnyAuthority(Role.MODERATOR.name(), Role.ADMIN.name())
                        .requestMatchers("/api/users").hasAuthority(Role.ADMIN.toString()).anyRequest()
                        .authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}