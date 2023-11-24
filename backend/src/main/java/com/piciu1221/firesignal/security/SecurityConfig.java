package com.piciu1221.firesignal.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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

/**
 * Configuration class for defining security settings in the application.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAthFilter jwtAthFilter;
    private final CustomUserDetailsService customUserDetailsService;

    /**
     * Configures the security settings for the application.
     *
     * @param http The HttpSecurity instance to configure.
     * @return The configured SecurityFilterChain.
     * @throws Exception If an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF protection and enable CORS (Cross-Origin Resource Sharing)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> authorize
                        // Allow access to authentication endpoint for all
                        .requestMatchers("/api/auth/authenticate").permitAll()
                        .requestMatchers("/api/user/register").permitAll()

                        // Define access based on user roles
                        .requestMatchers("/api/commander").hasRole("COMMANDER")
                        .requestMatchers("/api/admin").hasRole("ADMIN")
                        .requestMatchers("/api/firefighters").hasRole("ADMIN")

                        // Require authentication for any other requests
                        .anyRequest().authenticated()
                )
                // Set up the authentication provider and JWT filter
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAthFilter, UsernamePasswordAuthenticationFilter.class)
                // Configure session management to be stateless
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );
        return http.build();
    }

    /**
     * Creates an AuthenticationManager bean for handling authentication.
     *
     * @param config The AuthenticationConfiguration instance.
     * @return The configured AuthenticationManager.
     * @throws Exception If an error occurs during configuration.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Creates an AuthenticationProvider bean for DAO-based authentication.
     *
     * @return The configured AuthenticationProvider.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(customUserDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    /**
     * Creates a PasswordEncoder bean for handling password encoding.
     *
     * @return The configured PasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
