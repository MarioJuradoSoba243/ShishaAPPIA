package com.shisha.appia.backend.user;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Business logic layer for managing {@link User} entities.
 */
@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Retrieves all registered users.
     *
     * @return immutable list of users
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Retrieves a single user by identifier.
     *
     * @param id unique identifier
     * @return optional containing the user if present
     */
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Creates a new user.
     *
     * @param user user to create
     * @return persisted user
     */
    @Transactional
    public User createUser(User user) {
        userRepository.findByEmail(user.getEmail()).ifPresent(existing -> {
            throw new UserValidationException("Email already registered: " + existing.getEmail());
        });
        return userRepository.save(user);
    }

    /**
     * Updates an existing user.
     *
     * @param id      user identifier
     * @param request updated user data
     * @return updated user
     */
    @Transactional
    public User updateUser(Long id, User request) {
        return userRepository.findById(id)
            .map(user -> {
                user.setName(request.getName());
                user.setEmail(request.getEmail());
                return userRepository.save(user);
            })
            .orElseThrow(() -> new UserNotFoundException(id));
    }

    /**
     * Deletes a user by identifier.
     *
     * @param id user identifier
     */
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }
}
