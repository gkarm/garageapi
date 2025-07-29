package nl.novi.garageapi.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import nl.novi.garageapi.Security.MyUserDetails;
import nl.novi.garageapi.dto.UserDto;
import nl.novi.garageapi.dto.UserInputDto;
import nl.novi.garageapi.enumeration.UserRole;
import nl.novi.garageapi.exception.ForbiddenException;
import nl.novi.garageapi.exception.ResourceNotFoundException;
import nl.novi.garageapi.model.User;
import nl.novi.garageapi.repository.UserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserService userService;

    @Test
    public void testGetAllUsers() {
        // Arrange
        User user1 = new User();
        user1.setUsername("user1");

        User user2 = new User();
        user2.setUsername("user2");

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        // Act
        List<String> result = userService.getAllUsers();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains("user1"));
        assertTrue(result.contains("user2"));
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testGetUser() {
        // Arrange
        MyUserDetails myUserDetails = new MyUserDetails(new User("admin", "password", List.of(UserRole.ADMIN)));
        User user = new User("user1", "password", List.of(UserRole.USER));

        when(userRepository.findById("user1")).thenReturn(Optional.of(user));

        // Act
        UserDto result = userService.getUser(myUserDetails, "user1");

        // Assert
        assertNotNull(result);
        assertEquals("user1", result.getUsername());
        verify(userRepository, times(1)).findById("user1");
    }

    @Test
    public void testGetUser_Forbidden() {
        // Arrange
        MyUserDetails myUserDetails = new MyUserDetails(new User("user2", "password", List.of(UserRole.USER)));

        // Act & Assert
        ForbiddenException exception = assertThrows(ForbiddenException.class, () -> {
            userService.getUser(myUserDetails, "user1");
        });

        assertTrue(exception.getMessage().contains("You are logged in as user2, not as user1."));
    }

    @Test
    public void testCreateUser() {
        // Arrange
        UserInputDto userInputDto = new UserInputDto();
        userInputDto.setUsername("new_user");
        userInputDto.setPassword("password");
        userInputDto.setRole(List.of("USER"));

        User newUser = new User("new_user", "encoded_password", List.of(UserRole.USER));

        when(passwordEncoder.encode("password")).thenReturn("encoded_password");
        when(userRepository.findById("new_user")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(newUser);

        // Act
        UserDto result = userService.createUser(userInputDto);

        // Assert
        assertNotNull(result);
        assertEquals("new_user", result.getUsername());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testCreateUser_UsernameTaken() {
        // Arrange
        UserInputDto userInputDto = new UserInputDto();
        userInputDto.setUsername("existing_user");
        userInputDto.setPassword("password");
        userInputDto.setRole(List.of("USER"));

        User existingUser = new User("existing_user", "password", List.of(UserRole.USER));

        when(userRepository.findById("existing_user")).thenReturn(Optional.of(existingUser));

        // Act & Assert
        ForbiddenException exception = assertThrows(ForbiddenException.class, () -> {
            userService.createUser(userInputDto);
        });

        assertTrue(exception.getMessage().contains("The username existing_user is no longer available"));
    }

    @Test
    public void testEditUser() {
        // Arrange
        MyUserDetails myUserDetails = new MyUserDetails(new User("admin", "password", List.of(UserRole.ADMIN)));

        UserInputDto userInputDto = new UserInputDto();
        userInputDto.setUsername("user1");
        userInputDto.setPassword("new_password");
        userInputDto.setRole(List.of("ADMIN"));

        User existingUser = new User("user1", "password", List.of(UserRole.USER));

        when(userRepository.findById("user1")).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode("new_password")).thenReturn("encoded_password");
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        // Act
        UserDto result = userService.editUser(myUserDetails, "user1", userInputDto);

        // Assert
        assertNotNull(result);
        assertEquals("user1", result.getUsername());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testEditUser_Forbidden() {
        // Arrange
        MyUserDetails myUserDetails = new MyUserDetails(new User("user2", "password", List.of(UserRole.USER)));

        UserInputDto userInputDto = new UserInputDto();
        userInputDto.setUsername("user1");
        userInputDto.setPassword("new_password");
        userInputDto.setRole(List.of("ADMIN"));

//        when(userRepository.findById("user1")).thenReturn(Optional.of(new User("user1", "password", List.of(UserRole.USER))));

        // Act & Assert
        ForbiddenException exception = assertThrows(ForbiddenException.class, () -> {
            userService.editUser(myUserDetails, "user1", userInputDto);
        });

        assertTrue(exception.getMessage().contains("You are logged in as user2, not as user1."));
    }

    @Test
    public void testDeleteUser() {
        // Arrange
        MyUserDetails myUserDetails = new MyUserDetails(new User("admin", "password", List.of(UserRole.ADMIN)));

        User existingUser = new User("user1", "password", List.of(UserRole.USER));

        when(userRepository.findById("user1")).thenReturn(Optional.of(existingUser));

        // Act
        userService.deleteUser(myUserDetails, "user1");

        // Assert
        verify(userRepository, times(1)).deleteById("user1");
    }

    @Test
    public void testDeleteUser_Forbidden() {
        // Arrange
        MyUserDetails myUserDetails = new MyUserDetails(new User("user2", "password", List.of(UserRole.USER)));

//        when(userRepository.findById("user1")).thenReturn(Optional.of(new User("user1", "password", List.of(UserRole.USER))));

        // Act & Assert
        ForbiddenException exception = assertThrows(ForbiddenException.class, () -> {
            userService.deleteUser(myUserDetails, "user1");
        });

        assertTrue(exception.getMessage().contains("You are logged in as user2, not as user1."));
    }

    @Test
    public void testDeleteUser_NotFound() {
        // Arrange
        MyUserDetails myUserDetails = new MyUserDetails(new User("admin", "password", List.of(UserRole.ADMIN)));

        when(userRepository.findById("user1")).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            userService.deleteUser(myUserDetails, "user1");
        });

        assertTrue(exception.getMessage().contains("User user1 could not be found"));
    }

}