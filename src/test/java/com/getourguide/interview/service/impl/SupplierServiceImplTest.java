package com.getourguide.interview.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.getourguide.interview.dto.SupplierDto;
import com.getourguide.interview.entity.Supplier;
import com.getourguide.interview.mapper.DTOMapper;
import com.getourguide.interview.repository.SupplierRepository;

@ExtendWith(MockitoExtension.class)
class SupplierServiceImplTest {

	@Mock
	private SupplierRepository supplierRepository;

	@Mock
	private DTOMapper dtoMapper;

	@InjectMocks
	private SupplierServiceImpl supplierService;

	private Supplier supplier;
	private SupplierDto supplierDto;

	@BeforeEach
	void setUp() {
		supplier = new Supplier();
		supplier.setId(1L);
		supplier.setName("Test Supplier");

		supplierDto = new SupplierDto();
		supplierDto.setId(1L);
		supplierDto.setName("Test Supplier");
	}

	@Test
	void testGetSuppliers() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<Supplier> supplierPage = new PageImpl<>(List.of(supplier), pageable, 1);

		when(supplierRepository.findAll(pageable)).thenReturn(supplierPage);
		when(dtoMapper.convertListToDtoList(any(), eq(SupplierDto.class))).thenReturn(List.of(supplierDto));

		Page<SupplierDto> result = supplierService.getSuppliers(0, 10);

		assertNotNull(result);
		assertEquals(1, result.getTotalElements());
		assertEquals("Test Supplier", result.getContent().get(0).getName());
	}

	@Test
	void testSearchSuppliers() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<Supplier> supplierPage = new PageImpl<>(List.of(supplier), pageable, 1);

		when(supplierRepository
				.findByNameContainingOrAddressContainingOrZipContainingOrCityContainingOrCountryContaining("Test",
						"Test", "Test", "Test", "Test", pageable))
				.thenReturn(supplierPage);
		when(dtoMapper.convertListToDtoList(any(), eq(SupplierDto.class))).thenReturn(List.of(supplierDto));

		Page<SupplierDto> result = supplierService.searchSuppliers("Test", 0, 10);

		assertNotNull(result);
		assertEquals(1, result.getTotalElements());
		assertEquals("Test Supplier", result.getContent().get(0).getName());
	}

	@Test
	void testAddSupplier() {
		when(supplierRepository.save(any(Supplier.class))).thenReturn(supplier);
		when(dtoMapper.convertToDto(any(Supplier.class), eq(SupplierDto.class))).thenReturn(supplierDto);

		SupplierDto result = supplierService.addSupplier(supplierDto);

		assertNotNull(result);
		assertEquals("Test Supplier", result.getName());
	}

}
