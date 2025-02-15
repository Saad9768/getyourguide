package com.getourguide.interview.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.getourguide.interview.dto.SupplierDto;
import com.getourguide.interview.entity.Supplier;
import com.getourguide.interview.repository.SupplierRepository;

public class SupplierServiceTest {

    @Mock
    private SupplierRepository supplierRepository;

    @InjectMocks
    private SupplierService supplierService;

    private Supplier supplier;
    private SupplierDto supplierDto;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        supplier = new Supplier();
        supplier.setId(1L);
        supplier.setName("Test Supplier");
        supplier.setAddress("123 Test Street");
        supplier.setZip("12345");
        supplier.setCity("Test City");
        supplier.setCountry("Test Country");

        supplierDto = SupplierDto.convertToDto(supplier,true);
        pageable = PageRequest.of(0, 10);
    }

    @Test
    void testGetSuppliers() {
        Page<Supplier> supplierPage = new PageImpl<>(List.of(supplier), pageable, 1);
        when(supplierRepository.findAll(pageable)).thenReturn(supplierPage);

        Page<SupplierDto> result = supplierService.getSuppliers(0, 10);

        assertEquals(1, result.getTotalElements());
        assertEquals("Test Supplier", result.getContent().get(0).getName());
        verify(supplierRepository, times(1)).findAll(pageable);
    }

    @Test
    void testSearchSuppliers() {
        Page<Supplier> supplierPage = new PageImpl<>(List.of(supplier), pageable, 1);
        when(supplierRepository.findByNameContainingOrAddressContainingOrZipContainingOrCityContainingOrCountryContaining(
                anyString(), anyString(), anyString(), anyString(), anyString(), eq(pageable)))
                .thenReturn(supplierPage);

        Page<SupplierDto> result = supplierService.searchSuppliers("Test", 0, 10);

        assertEquals(1, result.getTotalElements());
        assertEquals("Test Supplier", result.getContent().get(0).getName());
        verify(supplierRepository, times(1))
                .findByNameContainingOrAddressContainingOrZipContainingOrCityContainingOrCountryContaining(
                        anyString(), anyString(), anyString(), anyString(), anyString(), eq(pageable));
    }

    @Test
    void testAddSupplier() {
        when(supplierRepository.save(any(Supplier.class))).thenReturn(supplier);

        SupplierDto result = supplierService.addSupplier(supplierDto);

        assertEquals("Test Supplier", result.getName());
        verify(supplierRepository, times(1)).save(any(Supplier.class));
    }
}
