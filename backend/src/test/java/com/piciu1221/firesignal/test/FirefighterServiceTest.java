package com.piciu1221.firesignal.test;

import com.piciu1221.firesignal.entity.Firefighter;
import com.piciu1221.firesignal.repository.FirefighterRepository;
import com.piciu1221.firesignal.repository.UserRepository;
import com.piciu1221.firesignal.service.FirefighterService;
import com.piciu1221.firesignal.util.ApiResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FirefighterServiceTest {

    @InjectMocks
    private FirefighterService firefighterService;

    @Mock
    private FirefighterRepository firefighterRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    public void testAddFirefighter() {
        // Arrange
        Firefighter firefighter = new Firefighter();
        firefighter.setFirefighterUsername("testUsername");

        when(firefighterRepository.existsByFirefighterUsername("testUsername")).thenReturn(false);
        when(userRepository.existsByUsername("testUsername")).thenReturn(true);

        // Act
        ApiResponse<String> response = firefighterService.addFirefighter(firefighter);

        // Assert
        assertTrue(response.isSuccess());
        assertEquals("Firefighter added successfully", response.getMessage());

        // Verify that save method is called
        verify(firefighterRepository).save(firefighter);
    }

    @Test
    public void testAddFirefighterWithExistingUsername() {
        // Arrange
        Firefighter firefighter = new Firefighter();
        firefighter.setFirefighterUsername("existingUsername");

        when(firefighterRepository.existsByFirefighterUsername("existingUsername")).thenReturn(true);

        // Act
        ApiResponse<String> response = firefighterService.addFirefighter(firefighter);

        // Assert
        assertFalse(response.isSuccess());
        assertEquals("Firefighter with this username already exists", response.getMessage());

        // Verify that save method is not called
        verify(firefighterRepository, never()).save(any());
    }

    @Test
    public void testAddFirefighterWithNonExistingUser() {
        // Arrange
        Firefighter firefighter = new Firefighter();
        firefighter.setFirefighterUsername("nonExistingUser");

        when(firefighterRepository.existsByFirefighterUsername("nonExistingUser")).thenReturn(false);
        when(userRepository.existsByUsername("nonExistingUser")).thenReturn(false);

        // Act
        ApiResponse<String> response = firefighterService.addFirefighter(firefighter);

        // Assert
        assertFalse(response.isSuccess());
        assertEquals("User with this username does not exist", response.getMessage());

        // Verify that save method is not called
        verify(firefighterRepository, never()).save(any());
    }
}
