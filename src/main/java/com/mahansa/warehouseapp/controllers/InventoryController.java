package com.mahansa.warehouseapp.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mahansa.warehouseapp.models.Inventory;
import com.mahansa.warehouseapp.services.InventoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/inventory")
@Tag(name = "Product Inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @Operation(
        summary = "Get all products in the inventory"
    )
    @GetMapping
    public ResponseEntity<List<Inventory>> getAllProducts() {
        return new ResponseEntity<>(inventoryService.getInventory(), HttpStatus.OK);
    }

    @Operation(
        summary = "Add new product",
        description = "Add new product by specifying it in the Request Body"
    )
    @PostMapping
    public void addProduct(@RequestBody Inventory inventory) {
        inventoryService.addInventory(inventory);
    }

    @Operation(
        summary = "Increase Product quantity",
        description = "Increase product quantity using its Inventory ID in the Path Variable and specify the quantity in the Request Parameter"
    )
    @PutMapping("/{inventoryId}")
    public ResponseEntity<Inventory> increaseProductQuantity(
            @PathVariable Long inventoryId, @RequestParam int quantity) {
        Inventory inventory = inventoryService.increaseProductQuantity(inventoryId, quantity);
        if (inventory == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(inventory, HttpStatus.OK);
    }
}
