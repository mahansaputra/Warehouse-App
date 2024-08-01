package com.mahansa.warehouseapp.services;

import com.mahansa.warehouseapp.models.Inventory;

import java.util.List;

public interface InventoryService {
    Inventory increaseProductQuantity(Long productId, int quantityToAdd);
    List<Inventory> getInventory();
    void addInventory(Inventory inventory);
}
