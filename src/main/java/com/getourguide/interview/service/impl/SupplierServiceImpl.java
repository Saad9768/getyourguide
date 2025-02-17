package com.getourguide.interview.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.getourguide.interview.dto.SupplierDto;
import com.getourguide.interview.entity.Supplier;
import com.getourguide.interview.mapper.DTOMapper;
import com.getourguide.interview.repository.SupplierRepository;
import com.getourguide.interview.service.SupplierService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
	
	private final DTOMapper dtoMapper;

    @Override
    public Page<SupplierDto> getSuppliers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Supplier> suppliers = supplierRepository.findAll(pageable);
        
        List<SupplierDto> supplierDtos = dtoMapper.convertListToDtoList(suppliers.getContent(), SupplierDto.class);

        return new PageImpl<>(supplierDtos, pageable, suppliers.getTotalElements());
    }
    
    @Override
    public Page<SupplierDto> searchSuppliers(String search, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Supplier> suppliers = supplierRepository
                .findByNameContainingOrAddressContainingOrZipContainingOrCityContainingOrCountryContaining(
                        search, search, search, search, search, pageable);

        List<SupplierDto> supplierDtos = dtoMapper.convertListToDtoList(suppliers.getContent(), SupplierDto.class);
        return new PageImpl<>(supplierDtos, pageable, suppliers.getTotalElements());
    }

    @Override
    public SupplierDto addSupplier(SupplierDto supplierDto) {
        Supplier supplier = new Supplier();
        supplier.setName(supplierDto.getName());
        supplier.setAddress(supplierDto.getAddress());
        supplier.setZip(supplierDto.getZip());
        supplier.setCity(supplierDto.getCity());
        supplier.setCountry(supplierDto.getCountry());

        Supplier savedSupplier = supplierRepository.save(supplier);
        return dtoMapper.convertToDto(savedSupplier, SupplierDto.class);
    }
}
