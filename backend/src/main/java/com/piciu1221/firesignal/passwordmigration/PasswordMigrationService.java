package com.piciu1221.firesignal.passwordmigration;

import com.piciu1221.firesignal.entity.User;
import com.piciu1221.firesignal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PasswordMigrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void migratePasswords() {
        List<User> users = userRepository.findAll();
        int passwordsChanged = 0;

        for (User user : users) {
            // Check if the password is already hashed (skip already hashed passwords)
            if (!user.getPassword().startsWith("$2a$")) {
                // Hash the plain text password using bcrypt
                String hashedPassword = passwordEncoder.encode(user.getPassword());
                // Update the user's password in the database
                user.setPassword(hashedPassword);
                userRepository.save(user);
                passwordsChanged++;
            }
        }

        // Provide a summary message after the migration is finished
        System.out.println("Password migration finished. Total passwords changed: " + passwordsChanged);
    }
}
