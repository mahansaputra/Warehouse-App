package com.mahansa.warehouseapp.controllers;

import com.mahansa.warehouseapp.dto.BorrowRecordDTO;
import com.mahansa.warehouseapp.services.BorrowService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrow")
@Tag(name = "Borrowing Item")
public class BorrowController {
    private final BorrowService borrowService;

    public BorrowController(BorrowService borrowService) {
        this.borrowService = borrowService;
    }

    @GetMapping
    public ResponseEntity<List<BorrowRecordDTO>> getAllBorrowRecords() {
        return new ResponseEntity<>(borrowService.getBorrowedItems(), HttpStatus.OK);
    }

    @PostMapping("{userId}")
    public ResponseEntity<String> borrowItem(
            @PathVariable Long userId,
            @RequestParam Long inventoryId,
            @RequestParam int quantity) {
        int borrowed = borrowService.borrowItem(inventoryId, userId, quantity);

        if (borrowed == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (borrowed == -1) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>("Item Borrowed", HttpStatus.CREATED);
    }
}
