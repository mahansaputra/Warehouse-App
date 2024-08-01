package com.mahansa.warehouseapp.controllers;

import com.mahansa.warehouseapp.models.Inventory;
import com.mahansa.warehouseapp.services.InventoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@Tag(name = "Product Inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public ResponseEntity<List<Inventory>> getAllProducts() {
        return new ResponseEntity<>(inventoryService.getInventory(), HttpStatus.OK);
    }

    @PostMapping
    public void addProduct(@RequestBody Inventory inventory) {
        inventoryService.addInventory(inventory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inventory> increaseProductQuantity(
            @PathVariable Long id, @RequestParam int quantity) {
        Inventory inventory = inventoryService.increaseProductQuantity(id, quantity);
        if (inventory == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(inventory, HttpStatus.OK);
    }
}
