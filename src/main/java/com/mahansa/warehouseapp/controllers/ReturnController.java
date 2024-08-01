package com.mahansa.warehouseapp.controllers;

import com.mahansa.warehouseapp.services.ReturnService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/return")
@Tag(name = "Returning Item")
public class ReturnController {
    private final ReturnService returnService;

    public ReturnController(ReturnService returnService) {
        this.returnService = returnService;
    }

    @PutMapping("{id}")
    public ResponseEntity<String> returnItem(
            @PathVariable Long id) {
        int returned = returnService.returnItem(id);

        if (returned == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (returned == -1) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>("Item Returned", HttpStatus.OK);
    }
}
