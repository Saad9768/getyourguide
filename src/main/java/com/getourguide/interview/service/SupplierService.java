package com.getourguide.interview.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.getourguide.interview.dto.SupplierDto;
import com.getourguide.interview.entity.Supplier;
import com.getourguide.interview.repository.SupplierRepository;

import lombok.AllArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public Page<SupplierDto> getSuppliers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Supplier> suppliers = supplierRepository.findAll(pageable);

        List<SupplierDto> supplierDtos = suppliers.getContent().stream()
                .map(SupplierDto::convertToDto)
                .collect(Collectors.toList());

        return new PageImpl<>(supplierDtos, pageable, suppliers.getTotalElements());
    }

    public Page<SupplierDto> searchSuppliers(String search, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Supplier> suppliers = supplierRepository
                .findByNameContainingOrAddressContainingOrZipContainingOrCityContainingOrCountryContaining(
                        search, search, search, search, search, pageable);

        List<SupplierDto> supplierDtos = suppliers.getContent().stream()
                .map(SupplierDto::convertToDto)
                .collect(Collectors.toList());

        return new PageImpl<>(supplierDtos, pageable, suppliers.getTotalElements());
    }
}
