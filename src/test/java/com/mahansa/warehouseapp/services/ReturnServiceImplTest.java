package com.mahansa.warehouseapp.services;

import com.mahansa.warehouseapp.models.BorrowRecord;
import com.mahansa.warehouseapp.models.Inventory;
import com.mahansa.warehouseapp.repositories.BorrowRecordRepository;
import com.mahansa.warehouseapp.repositories.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ReturnServiceImplTest {

    @Mock
    private BorrowRecordRepository borrowRecordRepository;

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private ReturnServiceImpl returnServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void returnItem_BorrowRecordNotFound() {
        when(borrowRecordRepository.findById(anyLong())).thenReturn(Optional.empty());

        int result = returnServiceImpl.returnItem(1L);

        assertEquals(0, result);
    }

    @Test
    void returnItem_AlreadyReturned() {
        BorrowRecord borrowRecord = new BorrowRecord();
        borrowRecord.setReturnDate(LocalDateTime.now());

        when(borrowRecordRepository.findById(anyLong())).thenReturn(Optional.of(borrowRecord));

        int result = returnServiceImpl.returnItem(1L);

        assertEquals(-1, result);
    }

    @Test
    void returnItem_SuccessfulReturn() {
        BorrowRecord borrowRecord = new BorrowRecord();
        borrowRecord.setQuantity(5);
        borrowRecord.setInventory(new Inventory(1L, "Tool", 10, null));

        when(borrowRecordRepository.findById(anyLong())).thenReturn(Optional.of(borrowRecord));

        Inventory inventory = new Inventory(1L, "Tool", 10, null);
        when(inventoryRepository.findById(anyLong())).thenReturn(inventory);

        int result = returnServiceImpl.returnItem(1L);

        assertEquals(1, result);
        assertEquals(15, inventory.getQuantity());
        verify(inventoryRepository).save(inventory);
        verify(borrowRecordRepository).save(borrowRecord);
    }
}
