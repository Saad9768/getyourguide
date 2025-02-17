package com.getourguide.interview.dto;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.getourguide.interview.entity.Activity;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

//	@JsonIgnore
//	@Autowired

//	private ModelMapper modelMapper;
//
//	public void configureMappings() {
//		
////		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
////		modelMapper.getConfiguration().setAmbiguityIgnored(true);
////		// Map Activity → ActivityDto
////		modelMapper.addMappings(new PropertyMap<Activity, ActivityDto>() {
////			@Override
////			protected void configure() {
////				map().setSupplierName(source.getSupplier().getName()); // Map supplier name separately
////			}
////		});
//		
////		// Mapping from ActivityDto -> Activity (ignore supplierName to avoid conflicts)
////		modelMapper.addMappings(new PropertyMap<ActivityDto, Activity>() {
////			@Override
////			protected void configure() {
////				skip(source.getSupplierName()); // Prevent conflicts
////			}
////		});
////		
////		modelMapper.typeMap(ActivityDto.class, Activity.class)
////		.addMappings(mapper -> mapper.skip(ActivityDto::setName));
//	}
//
//	@PostConstruct
//	private void postConstruct() {
//
//		System.out.println("========herereeeeeeeeeeeeeee9999999999999999999999999999999999999999999999999999999eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee=========");
//
//		configureMappings();
//	}

}
