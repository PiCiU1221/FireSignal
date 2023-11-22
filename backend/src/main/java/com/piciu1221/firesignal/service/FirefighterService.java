package com.piciu1221.firesignal.service;

import com.piciu1221.firesignal.model.Firefighter;
import com.piciu1221.firesignal.repository.FirefighterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FirefighterService {

    private final FirefighterRepository firefighterRepository;

    @Autowired
    public FirefighterService(FirefighterRepository firefighterRepository) {
        this.firefighterRepository = firefighterRepository;
    }
    public void createFirefighter(Firefighter firefighter) {
        // Check if the username is unique
        if (firefighterRepository.existsByFirefighterUsername(firefighter.getFirefighterUsername())) {
            throw new IllegalArgumentException("Department name must be unique");
        }

        // Save the firefighter using the repository
        firefighterRepository.save(firefighter);
    }
}
