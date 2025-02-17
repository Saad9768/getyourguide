package com.getourguide.interview.dto;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.getourguide.interview.entity.Supplier;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierDto {

	private Long id;
	private String name;
	private String address;
	private String zip;
	private String city;
	private String country;

	@JsonIgnoreProperties({ "supplier" })
	private List<ActivityDto> activities;

//	@Autowired
//	private ModelMapper modelMapper;
//
//	public void configureMappings() {
//		modelMapper.typeMap(Supplier.class, SupplierDto.class)
//				.addMappings(mapper -> mapper.map(Supplier::getActivities, SupplierDto::setActivities));
//
//		modelMapper.typeMap(SupplierDto.class, Supplier.class)
//				.addMappings(mapper -> mapper.map(SupplierDto::getActivities, Supplier::setActivities));
//	}
//
//	@PostConstruct
//	private void postConstruct() {
//		configureMappings();
//	}
}
