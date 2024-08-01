package com.mahansa.warehouseapp.services;

import com.mahansa.warehouseapp.dto.BorrowRecordDTO;
import com.mahansa.warehouseapp.models.BorrowRecord;
import com.mahansa.warehouseapp.models.Inventory;
import com.mahansa.warehouseapp.models.User;
import com.mahansa.warehouseapp.repositories.BorrowRecordRepository;
import com.mahansa.warehouseapp.repositories.InventoryRepository;
import com.mahansa.warehouseapp.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BorrowServiceImplTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BorrowRecordRepository borrowRecordRepository;

    @InjectMocks
    private BorrowServiceImpl borrowServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void borrowItem_SuccessfulBorrow() {
        Long inventoryId = 1L;
        Long userId = 1L;
        int quantity = 2;

        Inventory inventory = new Inventory();
        inventory.setId(inventoryId);
        inventory.setQuantity(5);

        User user = new User();
        user.setId(userId);

        when(inventoryRepository.findById(inventoryId)).thenReturn(inventory);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        int result = borrowServiceImpl.borrowItem(inventoryId, userId, quantity);

        //Method return 1 = success
        assertEquals(1, result);
        //Quantity is reduced to 3, 5 - 2
        assertEquals(3, inventory.getQuantity());
        //Save method called on both repo
        verify(inventoryRepository, times(1)).save(any(Inventory.class));
        verify(borrowRecordRepository, times(1)).save(any(BorrowRecord.class));
    }

    @Test
    void borrowItem_InventoryOrUserNotFound() {
        Long inventoryId = 1L;
        Long userId = 1L;
        int quantity = 2;

        //Test Inventory not found
        when(inventoryRepository.findById(inventoryId)).thenReturn(null);
        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
        //Check if method return 0
        assertEquals(0, borrowServiceImpl.borrowItem(inventoryId, userId, quantity));

        //Test User not found
        when(inventoryRepository.findById(inventoryId)).thenReturn(new Inventory());
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        //Check if method return 0
        assertEquals(0, borrowServiceImpl.borrowItem(inventoryId, userId, quantity));

        //Ensures save method is not performed
        verify(inventoryRepository, never()).save(any(Inventory.class));
        verify(borrowRecordRepository, never()).save(any(BorrowRecord.class));
    }

    @Test
    void borrowItem_InsufficientQuantity() {
        Long inventoryId = 1L;
        Long userId = 1L;
        int quantity = 10;

        Inventory inventory = new Inventory();
        inventory.setId(inventoryId);
        inventory.setQuantity(5);

        Inventory inventory2 = new Inventory();
        inventory.setId(inventoryId);
        inventory.setQuantity(5);

        User user = new User();
        user.setId(userId);

        when(inventoryRepository.findById(inventoryId)).thenReturn(inventory);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        assertEquals(-1, borrowServiceImpl.borrowItem(inventoryId, userId, quantity));
        assertEquals(5, inventory.getQuantity());

        when(inventoryRepository.findById(inventoryId)).thenReturn(inventory2);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));


        assertEquals(-1, borrowServiceImpl.borrowItem(inventoryId, userId, quantity));
        assertEquals(5, inventory.getQuantity());
        verify(inventoryRepository, never()).save(any(Inventory.class));
        verify(borrowRecordRepository, never()).save(any(BorrowRecord.class));
    }

    @Test
    void getBorrowedItems_ReturnsListOfBorrowRecordDTOs() {
        //Create mock data for users, inventory, and borrow records
        User user1 = new User(1L, "John", "Doe", "USER");
        User user2 = new User(2L, "Jane", "Smith", "ADMIN");
        Inventory inventory1 = new Inventory(1L, "Book", 5);
        Inventory inventory2 = new Inventory(2L, "Laptop", 3);

        BorrowRecord record1 = new BorrowRecord(1L, user1, inventory1, 1, LocalDateTime.now(), null);
        BorrowRecord record2 = new BorrowRecord(2L, user2, inventory2, 2, LocalDateTime.now(), LocalDateTime.now().plusDays(7));

        List<BorrowRecord> borrowRecords = Arrays.asList(record1, record2);
        //When findall method is used it should return the borrowRecords List
        when(borrowRecordRepository.findAll()).thenReturn(borrowRecords);

        List<BorrowRecordDTO> result = borrowServiceImpl.getBorrowedItems();
        //Check the size of the List
        assertEquals(2, result.size());
        //Check the value inside the dto is same as expected value
        BorrowRecordDTO dto1 = result.get(0);
        assertEquals(1L, dto1.getId());
        assertEquals(1L, dto1.getUserId());
        assertEquals("John Doe", dto1.getUserFullName());
        assertEquals(1L, dto1.getInventoryId());
        assertEquals("Book", dto1.getInventoryName());
        assertEquals(1, dto1.getQuantity());
        assertEquals(record1.getBorrowDate(), dto1.getBorrowDate());
        assertEquals(record1.getReturnDate(), dto1.getReturnDate());
        //Check the value inside the dto is same as expected value
        BorrowRecordDTO dto2 = result.get(1);
        assertEquals(2L, dto2.getId());
        assertEquals(2L, dto2.getUserId());
        assertEquals("Jane Smith", dto2.getUserFullName());
        assertEquals(2L, dto2.getInventoryId());
        assertEquals("Laptop", dto2.getInventoryName());
        assertEquals(2, dto2.getQuantity());
        assertEquals(record2.getBorrowDate(), dto2.getBorrowDate());
        assertEquals(record2.getReturnDate(), dto2.getReturnDate());
    }
}