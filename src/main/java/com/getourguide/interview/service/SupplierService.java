package com.getourguide.interview.service;

import org.springframework.data.domain.Page;

import com.getourguide.interview.dto.SupplierDto;

public interface SupplierService {
	Page<SupplierDto> getSuppliers(int page, int size);

	Page<SupplierDto> searchSuppliers(String search, int page, int size);

	SupplierDto addSupplier(SupplierDto supplierDto);

}
