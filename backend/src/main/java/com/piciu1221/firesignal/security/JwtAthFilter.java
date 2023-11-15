package com.piciu1221.firesignal.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * Custom JWT authentication filter to intercept and process JWT tokens in incoming requests.
 */
@Component
@RequiredArgsConstructor
public class JwtAthFilter extends OncePerRequestFilter {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtils jwtUtils;

    /**
     * Filter method to handle JWT authentication for each incoming request.
     *
     * @param request     The incoming HTTP request.
     * @param response    The HTTP response.
     * @param filterChain The filter chain for handling subsequent filters.
     * @throws ServletException If an exception occurs during the filter process.
     * @throws IOException      If an I/O exception occurs.
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        // Extract the Authorization header from the incoming request
        final String authHeader = request.getHeader(AUTHORIZATION);
        final String username;
        final String jwtToken;

        // Check if the Authorization header is present and starts with "Bearer"
        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            // If not, continue with the filter chain
            filterChain.doFilter(request, response);
            return;
        }

        // Extract the JWT token from the Authorization header
        jwtToken = authHeader.substring(7);
        // Extract the username from the JWT token
        username = jwtUtils.extractUsername(jwtToken);

        // Check if the SecurityContext doesn't already have an authentication and the username is not null
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load user details from the database based on the extracted username
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            // If the JWT token is valid, create an authentication token
            if (jwtUtils.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                // Set authentication details from the request
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set the authentication in the SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continue with the filter chain
        filterChain.doFilter(request, response);
    }
}
