package com.example.Spring.Security.Learn.securityconfig;

import com.example.Spring.Security.Learn.jwt.JwtAuthenticationFilter;
import com.example.Spring.Security.Learn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtAuthenticationFilter authenticationFilter;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

return http
        .csrf(AbstractHttpConfigurer::disable)
        .cors(Customizer.withDefaults())
        .authorizeHttpRequests(
                req -> req.requestMatchers("/login", "/register", "/images/**", "/active/**")
                        .permitAll()
                        .requestMatchers("/api/hotel/save")
                        .hasAuthority("HOTEL")
                        .requestMatchers("/api/test/**")
                        .hasAuthority("USER")
                        .requestMatchers("/api/hotel/")
                        .hasAnyAuthority("USER","ADMIN", "HOTEL")
                        .requestMatchers("/api/hotel/**")
                        .hasAuthority("ADMI")

        )
        .userDetailsService(userService)
        .sessionManagement(
                session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .build();

    }


    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();

    }


}
