package com.shisha.appia.backend.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

/**
 * Integration tests for {@link UserService} using an in-memory SQLite database.
 */
@SpringBootTest
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:sqlite:memory:testdb?cache=shared",
    "spring.datasource.driver-class-name=org.sqlite.JDBC",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.jpa.show-sql=false"
})
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void createUserPersistsEntity() {
        User created = userService.createUser(new User("Alice", "alice@example.com"));

        assertNotNull(created.getId(), "Identifier should be generated");
        assertEquals("Alice", created.getName());
        assertEquals("alice@example.com", created.getEmail());

        List<User> users = userService.getAllUsers();
        assertEquals(1, users.size());
    }

    @Test
    void duplicateEmailThrowsValidationException() {
        userService.createUser(new User("Bob", "bob@example.com"));

        User duplicate = new User("Robert", "bob@example.com");
        assertThrows(UserValidationException.class, () -> userService.createUser(duplicate));
    }

    @Test
    void deleteMissingUserThrowsException() {
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userService.deleteUser(999L));
        assertTrue(exception.getMessage().contains("999"));
    }
}
