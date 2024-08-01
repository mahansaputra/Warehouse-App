package com.mahansa.warehouseapp.services;

import com.mahansa.warehouseapp.models.Inventory;
import com.mahansa.warehouseapp.repositories.InventoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public Inventory increaseProductQuantity(Long productId, int quantityToAdd) {
        Inventory inventory = inventoryRepository.findById(productId);

        if (inventory == null) {
            return null;
        }

        inventory.setQuantity(inventory.getQuantity() + quantityToAdd);
        return inventoryRepository.save(inventory);
    }

    @Override
    public List<Inventory> getInventory() {
        return inventoryRepository.findAll();
    }

    @Override
    public void addInventory(Inventory inventory) {
        inventoryRepository.save(inventory);
    }
}
