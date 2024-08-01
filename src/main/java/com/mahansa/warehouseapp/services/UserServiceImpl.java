package com.mahansa.warehouseapp.services;

import com.mahansa.warehouseapp.dto.UserDTO;
import com.mahansa.warehouseapp.models.User;
import com.mahansa.warehouseapp.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void addUser(User user) {
        userRepository.save(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return mapUserToDTO(userRepository.findAll());
    }

    private List<UserDTO> mapUserToDTO(List<User> users) {
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setName(user.getFirstName(), user.getLastName());
            userDTO.setRole(user.getRole());
            userDTOs.add(userDTO);
        }
        return userDTOs;
    }
}
