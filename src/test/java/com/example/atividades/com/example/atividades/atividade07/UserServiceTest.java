package com.example.atividades.atividade07;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private Database mockDatabase;
    private UserService userService;

    @BeforeEach
    public void setUp() {
        mockDatabase = mock(Database.class);
        userService = new UserService(mockDatabase);
    }

    @Test
    public void testSaveUser_ValidUser() {
        User validUser = new User("John Doe", "john.doe@example.com");

        userService.saveUser(validUser);

        verify(mockDatabase, times(1)).saveUser(validUser);
    }

    @Test
    public void testSaveUser_NullName() {
        User userWithNullName = new User(null, "john.doe@example.com");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.saveUser(userWithNullName);
        });

        assertEquals("User must have a name and email", exception.getMessage());
        verify(mockDatabase, never()).saveUser(any(User.class));
    }

    @Test
    public void testSaveUser_EmptyName() {
        User userWithEmptyName = new User("", "john.doe@example.com");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.saveUser(userWithEmptyName);
        });

        assertEquals("User must have a name and email", exception.getMessage());
        verify(mockDatabase, never()).saveUser(any(User.class));
    }

    @Test
    public void testSaveUser_NullEmail() {
        User userWithNullEmail = new User("John Doe", null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.saveUser(userWithNullEmail);
        });

        assertEquals("User must have a name and email", exception.getMessage());
        verify(mockDatabase, never()).saveUser(any(User.class));
    }

    @Test
    public void testSaveUser_EmptyEmail() {
        User userWithEmptyEmail = new User("John Doe", "");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.saveUser(userWithEmptyEmail);
        });

        assertEquals("User must have a name and email", exception.getMessage());
        verify(mockDatabase, never()).saveUser(any(User.class));
    }

    @Test
    public void testSaveUser_NullUser() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            userService.saveUser(null);
        });

        assertNotNull(exception);  // To demonstrate that the exception variable is being used
        verify(mockDatabase, never()).saveUser(any(User.class));
    }
}
