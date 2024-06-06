package com.example.atividades.atividade10;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserManagerTest {

    private UserService mockUserService;
    private UserManager userManager;

    @BeforeEach
    public void setUp() {
        mockUserService = mock(UserService.class);
        userManager = new UserManager(mockUserService);
    }

    @Test
    public void testFetchUserInfo_UserExists() {
        User mockUser = new User("John Doe", "john.doe@example.com");
        when(mockUserService.getUserInfo(1)).thenReturn(mockUser);

        User user = userManager.fetchUserInfo(1);

        assertNotNull(user);
        assertEquals("John Doe", user.getName());
        assertEquals("john.doe@example.com", user.getEmail());
        verify(mockUserService, times(1)).getUserInfo(1);
    }

    @Test
    public void testFetchUserInfo_UserDoesNotExist() {
        when(mockUserService.getUserInfo(1)).thenReturn(null);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userManager.fetchUserInfo(1);
        });

        assertEquals("User not found", exception.getMessage());
        verify(mockUserService, times(1)).getUserInfo(1);
    }

    @Test
    public void testFetchUserInfo_DifferentUserId() {
        User mockUser = new User("Jane Doe", "jane.doe@example.com");
        when(mockUserService.getUserInfo(2)).thenReturn(mockUser);

        User user = userManager.fetchUserInfo(2);

        assertNotNull(user);
        assertEquals("Jane Doe", user.getName());
        assertEquals("jane.doe@example.com", user.getEmail());
        verify(mockUserService, times(1)).getUserInfo(2);
    }

    @Test
    public void testFetchUserInfo_UserServiceCalledOnce() {
        User mockUser = new User("John Doe", "john.doe@example.com");
        when(mockUserService.getUserInfo(1)).thenReturn(mockUser);

        userManager.fetchUserInfo(1);

        verify(mockUserService, times(1)).getUserInfo(1);
    }

    @Test
    public void testFetchUserInfo_MultipleCalls() {
        User mockUser1 = new User("John Doe", "john.doe@example.com");
        User mockUser2 = new User("Jane Doe", "jane.doe@example.com");

        when(mockUserService.getUserInfo(1)).thenReturn(mockUser1);
        when(mockUserService.getUserInfo(2)).thenReturn(mockUser2);

        User user1 = userManager.fetchUserInfo(1);
        User user2 = userManager.fetchUserInfo(2);

        assertNotNull(user1);
        assertEquals("John Doe", user1.getName());
        assertEquals("john.doe@example.com", user1.getEmail());

        assertNotNull(user2);
        assertEquals("Jane Doe", user2.getName());
        assertEquals("jane.doe@example.com", user2.getEmail());

        verify(mockUserService, times(1)).getUserInfo(1);
        verify(mockUserService, times(1)).getUserInfo(2);
    }

    @Test
    public void testFetchUserInfo_NullResponseFromUserService() {
        when(mockUserService.getUserInfo(anyInt())).thenReturn(null);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userManager.fetchUserInfo(999);
        });

        assertEquals("User not found", exception.getMessage());
        verify(mockUserService, times(1)).getUserInfo(999);
    }
}


