package com.mahansa.warehouseapp.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mahansa.warehouseapp.dto.BorrowRecordDTO;
import com.mahansa.warehouseapp.services.BorrowService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/borrow")
@Tag(name = "Borrowing Item")
public class BorrowController {
    private final BorrowService borrowService;

    public BorrowController(BorrowService borrowService) {
        this.borrowService = borrowService;
    }

    @Operation(
        summary = "Get all borrow records"
    )
    @GetMapping
    public ResponseEntity<List<BorrowRecordDTO>> getAllBorrowRecords() {
        return new ResponseEntity<>(borrowService.getBorrowedItems(), HttpStatus.OK);
    }

    @Operation(
        summary = "Borrow an item",
        description = "Borrow an item by specifying User ID in the path variable and Inventory ID and Quantity in the Request Parameter"
    )
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
