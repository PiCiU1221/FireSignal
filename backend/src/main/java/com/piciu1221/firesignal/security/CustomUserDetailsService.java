package com.piciu1221.firesignal.security;

import com.piciu1221.firesignal.entity.User;
import com.piciu1221.firesignal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Custom implementation of the UserDetailsService interface to load user details from the database.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Load user details by username from the database.
     *
     * @param username The username for which user details are to be loaded.
     * @return UserDetails object containing user details.
     * @throws UsernameNotFoundException If the user with the given username is not found in the database.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Load the user from the database by username
        User user = userRepository.findByUsername(username);

        // Check if the user is not found
        if (user == null) {
            // Throw an exception indicating that the user is not found
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        // Create a UserDetails object from the User entity
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
}
