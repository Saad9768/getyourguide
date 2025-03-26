package com.getourguide.interview.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.getourguide.interview.entity.Activity;
import com.getourguide.interview.entity.Supplier;

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

	public static SupplierDto convertToDto(Supplier supplier, boolean includeActivities) {
		if (supplier == null) {
			return null;
		}
		List<ActivityDto> activityDtoList = null;
		if (includeActivities && supplier.getActivities() != null) {
			activityDtoList = supplier.getActivities().stream().map(ActivityDto::convertToDto)
					.collect(Collectors.toList());
		}

		return SupplierDto.builder().id(supplier.getId()).name(supplier.getName()).address(supplier.getAddress())
				.zip(supplier.getZip()).city(supplier.getCity()).country(supplier.getCountry())
				.activities(activityDtoList).build();
	}

	public static Supplier convertToEntity(SupplierDto supplierDto) {
		if (supplierDto == null) {
			return null;
		}

		List<Activity> activityList = null;
		if (supplierDto.getActivities() != null) {
			activityList = supplierDto.getActivities().stream().map(ActivityDto::convertToEntity)
					.collect(Collectors.toList());
		}
		return Supplier.builder().id(supplierDto.getId()).name(supplierDto.getName()).address(supplierDto.getAddress())
				.zip(supplierDto.getZip()).city(supplierDto.getCity()).country(supplierDto.getCountry())
				.activities(activityList).build();
	}
}
