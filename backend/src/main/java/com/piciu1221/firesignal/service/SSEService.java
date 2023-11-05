package com.piciu1221.firesignal.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SSEService {

    private final Map<String, FluxSink<String>> userSinkMap = new ConcurrentHashMap<>();

    public Flux<String> subscribeToAlarmedFirefighters(String username) {
        System.out.println("Subscribing to SSE for user: " + username);

        return Flux.create(sink -> {
            addUserSink(username, sink);

            sink.onDispose(() -> {
                removeUserSink(username);
                System.out.println("SSE connection ended for user: " + username);
            });
        });
    }

    public void addUserSink(String username, FluxSink<String> sink) {
        userSinkMap.put(username, sink);
    }

    public void removeUserSink(String username) {
        userSinkMap.remove(username);
    }

    public void sendSseMessageToUser(String username, String message) {
        FluxSink<String> sink = userSinkMap.get(username);
        if (sink != null) {
            sink.next(message);
        }
    }

    public boolean isUserActive(String username) {
        return userSinkMap.containsKey(username);
    }
}
