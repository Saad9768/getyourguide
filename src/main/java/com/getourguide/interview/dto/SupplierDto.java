package com.getourguide.interview.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class SupplierDto {

	private Long id;
	private String name;
	private String address;
	private String zip;
	private String city;
	private String country;

	@JsonIgnoreProperties({ "supplier" })
	private List<ActivityDto> activities;
}
