package com.piciu1221.firesignal.repository;

import com.piciu1221.firesignal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing and managing user data in the database.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Retrieve a user by their username.
     *
     * @param username The username of the user.
     * @return The user with the specified username.
     */
    User findByUsername(String username);

    /**
     * Check if a user with the given username exists.
     *
     * @param username The username to check for existence.
     * @return True if a user with the given username exists, false otherwise.
     */
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.username = ?1")
    boolean existsByUsername(String username);
}
