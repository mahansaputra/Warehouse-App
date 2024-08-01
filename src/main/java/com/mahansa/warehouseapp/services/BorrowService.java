package com.mahansa.warehouseapp.services;

import com.mahansa.warehouseapp.dto.BorrowRecordDTO;

import java.util.List;

public interface BorrowService {
    int borrowItem(Long inventoryId, Long userId, int quantity);
    List<BorrowRecordDTO> getBorrowedItems();
}
