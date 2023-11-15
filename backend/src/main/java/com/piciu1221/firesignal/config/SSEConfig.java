package com.piciu1221.firesignal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Configuration class for Server-Sent Events (SSE) settings.
 */
@Configuration
public class SSEConfig {

    /**
     * Creates and configures a concurrent map to store SSE emitters.
     *
     * @return A concurrent map for storing SSE emitters.
     */
    @Bean
    public Map<String, SseEmitter> sseEmitters() {
        return new ConcurrentHashMap<>();
    }
}