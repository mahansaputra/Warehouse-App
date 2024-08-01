package com.mahansa.warehouseapp.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mahansa.warehouseapp.models.Inventory;
import com.mahansa.warehouseapp.repositories.InventoryRepository;

class InventoryServiceImplTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private InventoryServiceImpl inventoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void increaseProductQuantity_Success() {
        Long productId = 1L;
        int quantityToAdd = 5;
        Inventory inventory = new Inventory();
        inventory.setId(productId);
        inventory.setQuantity(10);

        when(inventoryRepository.findById(productId)).thenReturn(inventory);
        when(inventoryRepository.save(any(Inventory.class))).thenReturn(inventory);

        Inventory result = inventoryService.increaseProductQuantity(productId, quantityToAdd);
        //Check the value expected is true
        assertNotNull(result);
        assertEquals(15, result.getQuantity());
        verify(inventoryRepository).findById(productId);
        verify(inventoryRepository).save(inventory);
    }

    @Test
    void increaseProductQuantity_ProductNotFound() {
        Long productId = 1L;
        int quantityToAdd = 5;

        when(inventoryRepository.findById(productId)).thenReturn(null);

        Inventory result = inventoryService.increaseProductQuantity(productId, quantityToAdd);

        assertNull(result);
        verify(inventoryRepository).findById(productId);
        verify(inventoryRepository, never()).save(any(Inventory.class));
    }

    @Test
    void getInventory_Success() {
        List<Inventory> inventoryList = Arrays.asList(
                new Inventory(1L, "Product1", 10),
                new Inventory(2L, "Product2", 20)
        );

        when(inventoryRepository.findAll()).thenReturn(inventoryList);

        List<Inventory> result = inventoryService.getInventory();
        //Check the result same as expected value
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(inventoryList, result);
        verify(inventoryRepository).findAll();
    }

    @Test
    void addInventory_Success() {
        Inventory inventory = new Inventory(1L, "NewProduct", 5);

        inventoryService.addInventory(inventory);

        verify(inventoryRepository).save(inventory);
    }
}
