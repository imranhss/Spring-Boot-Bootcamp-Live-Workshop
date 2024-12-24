package com.example.Spring.Security.Learn.jwt;

import com.example.Spring.Security.Learn.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;


    public JwtAuthenticationFilter(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader=request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            // If no token found or token format is incorrect, proceed with the filter chain
            filterChain.doFilter(request,response);
            return;
        }

        String token = authHeader.substring(7);
        //  Get User name from token
        String username = jwtService.extractUserName(token);



        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Load user details by username from userDetailsService
            UserDetails userDetails = userService.loadUserByUsername(username);

            // Validate token and user details
            if(jwtService.isValid(token, userDetails)) {
                // If token is valid, create authentication token
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );

                // Set authentication token details
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // Set authentication token in security context
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        }

        // Proceed with the filter chain
        filterChain.doFilter(request, response);


    }

}
