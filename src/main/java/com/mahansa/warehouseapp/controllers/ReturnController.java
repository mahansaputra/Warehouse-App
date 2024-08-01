package com.mahansa.warehouseapp.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahansa.warehouseapp.services.ReturnService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/return")
@Tag(name = "Returning Item")
public class ReturnController {
    private final ReturnService returnService;

    public ReturnController(ReturnService returnService) {
        this.returnService = returnService;
    }

    @Operation(
        summary = "Return an Item",
        description= "Return an Item using the Borrow ID in the Path Variable"
    )
    @PutMapping("{borrowId}")
    public ResponseEntity<String> returnItem(
            @PathVariable Long borrowId) {
        int returned = returnService.returnItem(borrowId);

        if (returned == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (returned == -1) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>("Item Returned", HttpStatus.OK);
    }
}
