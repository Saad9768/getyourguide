package com.getourguide.interview.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

import com.getourguide.interview.dto.SupplierDto;
import com.getourguide.interview.service.SupplierService;

class SupplierControllerUTTest {

    @Mock
    private SupplierService supplierService;

    @InjectMocks
    private SupplierController supplierController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetSuppliers() {
        Page<SupplierDto> mockPage = new PageImpl<>(List.of(new SupplierDto()));
        when(supplierService.getSuppliers(0, 10)).thenReturn(mockPage);

        ResponseEntity<Page<SupplierDto>> response = supplierController.getSuppliers(0, 10);

        assertNotNull(response);
        assertEquals(OK, response.getStatusCode());
        assertEquals(1, response.getBody().getTotalElements());
        verify(supplierService, times(1)).getSuppliers(0, 10);
    }

    @Test
    void testSearchSuppliers() {
        Page<SupplierDto> mockPage = new PageImpl<>(List.of(new SupplierDto()));
        when(supplierService.searchSuppliers("test", 0, 10)).thenReturn(mockPage);

        ResponseEntity<Page<SupplierDto>> response = supplierController.searchSuppliers("test", 0, 10);

        assertNotNull(response);
        assertEquals(OK, response.getStatusCode());
        assertEquals(1, response.getBody().getTotalElements());
        verify(supplierService, times(1)).searchSuppliers("test", 0, 10);
    }

    @Test
    void testAddSupplier() {
        SupplierDto inputDto = new SupplierDto();
        SupplierDto savedDto = new SupplierDto();
        when(supplierService.addSupplier(inputDto)).thenReturn(savedDto);

        ResponseEntity<SupplierDto> response = supplierController.addSupplier(inputDto);

        assertNotNull(response);
        assertEquals(OK, response.getStatusCode());
        assertEquals(savedDto, response.getBody());
        verify(supplierService, times(1)).addSupplier(inputDto);
    }
}
