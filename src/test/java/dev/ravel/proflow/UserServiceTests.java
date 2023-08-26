package dev.ravel.proflow;

import dev.ravel.proflow.domain.services.UserServiceImpl;
import dev.ravel.proflow.infrastructure.model.User;
import dev.ravel.proflow.infrastructure.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testAddUser() {
        User newUser = new User();
        newUser.setUsername("john");
        newUser.setPassword("password");

        when(userRepository.save(any(User.class))).thenReturn(newUser);

        User addedUser = userService.addUser(newUser);

        assertNotNull(addedUser);
        assertEquals("john", addedUser.getUsername());
        assertEquals("password", addedUser.getPassword());

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testGetUserById() {
        User user = new User();
        user.setId(1L);
        user.setUsername("john");
        user.setPassword("password");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User foundUser = userService.getUserById(1L);

        assertNotNull(foundUser);
        assertEquals("john", foundUser.getUsername());
        assertEquals("password", foundUser.getPassword());

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetUsers() {
        List<User> mockUserList = new ArrayList<>();
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("john");
        user1.setPassword("password");

        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("jane");
        user2.setPassword("securepassword");

        mockUserList.add(user1);
        mockUserList.add(user2);

        when(userRepository.findAll()).thenReturn(mockUserList);

        List<User> users = userService.getUsers();

        assertNotNull(users);
        assertEquals(2, users.size());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteUser() {
        long userIdToDelete = 1L;

        userService.deleteUser(userIdToDelete);

        verify(userRepository, times(1)).deleteById(userIdToDelete);
    }

    @Test
    public void testUpdateUser() {
        User userToUpdate = new User();
        userToUpdate.setId(1L);
        userToUpdate.setUsername("updatedUser");
        userToUpdate.setPassword("updatedPassword");

        when(userRepository.save(any(User.class))).thenReturn(userToUpdate);

        User updatedUser = userService.updateUser(userToUpdate);

        assertNotNull(updatedUser);
        assertEquals("updatedUser", updatedUser.getUsername());
        assertEquals("updatedPassword", updatedUser.getPassword());

        verify(userRepository, times(1)).save(any(User.class));
    }
}
