package com.mahansa.warehouseapp.repositories;

import com.mahansa.warehouseapp.models.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
    Inventory findById(Long productId);
}
