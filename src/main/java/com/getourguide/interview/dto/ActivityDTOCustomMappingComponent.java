package com.getourguide.interview.dto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.getourguide.interview.entity.Activity;
import com.getourguide.interview.entity.Supplier;

import jakarta.annotation.PostConstruct;

@Component
public class ActivityDTOCustomMappingComponent {

	@Autowired
	private ModelMapper modelMapper;

	public void configureMappings() {
		modelMapper.typeMap(Activity.class, ActivityDto.class)
				.addMapping(src -> src.getSupplier().getName(), ActivityDto::setSupplierName);

		modelMapper.typeMap(Supplier.class, SupplierDto.class); 
	}

	public void configureMappings1() {
		modelMapper.typeMap(Supplier.class, SupplierDto.class)
				.addMappings(mapper -> mapper.map(Supplier::getActivities, SupplierDto::setActivities));

		modelMapper.typeMap(SupplierDto.class, Supplier.class)
				.addMappings(mapper -> mapper.map(SupplierDto::getActivities, Supplier::setActivities));
	}

	@PostConstruct
	private void postConstruct() {

		System.out.println(
				"========chafdsdfvghjabsdhjasbhj=========");

		configureMappings();
//		configureMappings1();
	}

}
