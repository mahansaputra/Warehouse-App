package com.mahansa.warehouseapp.services;

import com.mahansa.warehouseapp.dto.UserDTO;
import com.mahansa.warehouseapp.models.User;

import java.util.List;

public interface UserService {
    void addUser(User user);
    List<UserDTO> getAllUsers();
}
