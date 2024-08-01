package com.mahansa.warehouseapp.repositories;

import com.mahansa.warehouseapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
