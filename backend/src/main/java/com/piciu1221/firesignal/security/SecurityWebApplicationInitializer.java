package com.piciu1221.firesignal.security;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * Initializes the Spring Security configuration for the web application.
 * This class is used to register the {@link WebSecurityConfig} class with Spring Security.
 */
public class SecurityWebApplicationInitializer
        extends AbstractSecurityWebApplicationInitializer {

    /**
     * Constructs a new instance of the {@code SecurityWebApplicationInitializer}.
     * It calls the constructor of the superclass and passes the {@link WebSecurityConfig} class.
     */
    public SecurityWebApplicationInitializer() {
        super(WebSecurityConfig.class);
    }
}