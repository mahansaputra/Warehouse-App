package com.mahansa.warehouseapp.services;

import com.mahansa.warehouseapp.dto.UserDTO;
import com.mahansa.warehouseapp.models.User;
import com.mahansa.warehouseapp.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addUser() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setRole("ADMIN");

        userServiceImpl.addUser(user);

        verify(userRepository).save(user);
    }

    @Test
    void getAllUsers() {
        List<User> usersList = Arrays.asList(
                new User(1L, "John", "Doe", "ADMIN"),
                new User(2L, "Jane", "Doe", "USER")
        );

        when(userRepository.findAll()).thenReturn(usersList);

        List<UserDTO> userDTOs = userServiceImpl.getAllUsers();

        assertEquals(2, userDTOs.size());

        assertEquals(1L, userDTOs.get(0).getId());
        assertEquals("John Doe", userDTOs.get(0).getName());
        assertEquals("ADMIN", userDTOs.get(0).getRole());

        assertEquals(2L, userDTOs.get(1).getId());
        assertEquals("Jane Doe", userDTOs.get(1).getName());
        assertEquals("USER", userDTOs.get(1).getRole());
    }
}
