package com.mahansa.warehouseapp.services;

import com.mahansa.warehouseapp.models.BorrowRecord;
import com.mahansa.warehouseapp.models.Inventory;
import com.mahansa.warehouseapp.repositories.BorrowRecordRepository;
import com.mahansa.warehouseapp.repositories.InventoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReturnServiceImpl implements ReturnService{
    private final BorrowRecordRepository borrowRecordRepository;
    private final InventoryRepository inventoryRepository;

    public ReturnServiceImpl(BorrowRecordRepository borrowRecordRepository, InventoryRepository inventoryRepository) {
        this.borrowRecordRepository = borrowRecordRepository;
        this.inventoryRepository = inventoryRepository;
    }
    @Override
    public int returnItem(Long borrowId) {
        BorrowRecord borrowRecord = borrowRecordRepository.findById(borrowId).orElse(null);
        if (borrowRecord == null) {
            return 0;
        }

        if (borrowRecord.getReturnDate() != null) {
            return -1;
        }

        Inventory inventory = inventoryRepository.findById(borrowRecord.getInventory().getId());

        inventory.setQuantity(inventory.getQuantity() + borrowRecord.getQuantity());
        inventoryRepository.save(inventory);

        borrowRecord.setReturnDate(LocalDateTime.now());
        borrowRecordRepository.save(borrowRecord);
        return 1;
    }
}
