package com.getourguide.interview.dto;

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
public class ActivityDto {

	private Long id;
	private String title;
	private int price;
	private String currency;
	private double rating;
	private boolean specialOffer;
	private String supplierName;

	@JsonIgnoreProperties({ "activities" })
	private SupplierDto supplier;

}
