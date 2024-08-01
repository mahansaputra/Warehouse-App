package com.mahansa.warehouseapp.services;

import com.mahansa.warehouseapp.dto.BorrowRecordDTO;
import com.mahansa.warehouseapp.models.BorrowRecord;
import com.mahansa.warehouseapp.models.Inventory;
import com.mahansa.warehouseapp.models.User;
import com.mahansa.warehouseapp.repositories.BorrowRecordRepository;
import com.mahansa.warehouseapp.repositories.InventoryRepository;
import com.mahansa.warehouseapp.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BorrowServiceImpl implements BorrowService{
    private final BorrowRecordRepository borrowRecordRepository;
    private final InventoryRepository inventoryRepository;
    private final UserRepository userRepository;

    public BorrowServiceImpl(BorrowRecordRepository borrowRecordRepository, InventoryRepository inventoryRepository, UserRepository userRepository) {
        this.borrowRecordRepository = borrowRecordRepository;
        this.inventoryRepository = inventoryRepository;
        this.userRepository = userRepository;
    }


    @Override
    public int borrowItem(Long inventoryId, Long userId, int quantity) {
        Inventory inventory = inventoryRepository.findById(inventoryId);
        User user = userRepository.findById(userId).orElse(null);

        if (inventory == null || user == null) {
            return 0;
        } else if (inventory.getQuantity() < quantity || quantity == 0 ) {
            return -1;
        }

        inventory.setQuantity(inventory.getQuantity() - quantity);
        inventoryRepository.save(inventory);

        BorrowRecord borrowRecord = new BorrowRecord();
        borrowRecord.setInventory(inventory);
        borrowRecord.setUser(user);
        borrowRecord.setQuantity(quantity);
        borrowRecord.setBorrowDate(LocalDateTime.now());
        borrowRecordRepository.save(borrowRecord);

        return 1;
    }

    @Override
    public List<BorrowRecordDTO> getBorrowedItems() {
        return mapBorrowRecordToDTO(borrowRecordRepository.findAll());
    }

    private List<BorrowRecordDTO> mapBorrowRecordToDTO(List<BorrowRecord> borrowRecords) {
        List<BorrowRecordDTO> borrowRecordDTOS = new ArrayList<>();
        for (BorrowRecord borrowRecord : borrowRecords) {
            BorrowRecordDTO borrowRecordDTO = new BorrowRecordDTO();
            String userFullName = borrowRecord.getUser().getFirstName()+ " " + borrowRecord.getUser().getLastName();
            borrowRecordDTO.setId(borrowRecord.getId());
            borrowRecordDTO.setUserId(borrowRecord.getUser().getId());
            borrowRecordDTO.setUserFullName(userFullName);
            borrowRecordDTO.setUserRole(borrowRecord.getUser().getRole());
            borrowRecordDTO.setInventoryId(borrowRecord.getInventory().getId());
            borrowRecordDTO.setInventoryName(borrowRecord.getInventory().getName());
            borrowRecordDTO.setQuantity(borrowRecord.getQuantity());
            borrowRecordDTO.setBorrowDate(borrowRecord.getBorrowDate());
            borrowRecordDTO.setReturnDate(borrowRecord.getReturnDate());
            borrowRecordDTOS.add(borrowRecordDTO);
        }
        return borrowRecordDTOS;
    }
}
