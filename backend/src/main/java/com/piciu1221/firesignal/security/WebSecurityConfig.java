package com.piciu1221.firesignal.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * Configuration class for defining security-related settings.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    /**
     * Configures an in-memory user details service.
     *
     * @return An instance of {@link InMemoryUserDetailsManager} containing a default user with roles.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        // Create a default user with username "user", password "password", and role "USER".
        manager.createUser(User.withDefaultPasswordEncoder().username("user").password("password").roles("USER").build());
        return manager;
    }
}