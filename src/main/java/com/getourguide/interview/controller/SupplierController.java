package com.getourguide.interview.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.getourguide.interview.dto.SupplierDto;
import com.getourguide.interview.service.SupplierService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/suppliers")
@AllArgsConstructor
public class SupplierController {
    private final SupplierService supplierService;

    @GetMapping
    public ResponseEntity<Page<SupplierDto>> getSuppliers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(supplierService.getSuppliers(page, size));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<SupplierDto>> searchSuppliers(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(supplierService.searchSuppliers(query, page, size));
    }
    
    @Validated(SupplierDto.class)
    @PostMapping
    public ResponseEntity<SupplierDto> addSupplier(@RequestBody SupplierDto supplierDto) {
        SupplierDto createdSupplier = supplierService.addSupplier(supplierDto);
        return ResponseEntity.ok(createdSupplier);
    }
}
