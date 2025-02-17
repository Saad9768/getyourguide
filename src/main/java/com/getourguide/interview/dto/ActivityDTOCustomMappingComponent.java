package com.getourguide.interview.dto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.getourguide.interview.entity.Activity;

import jakarta.annotation.PostConstruct;

@Component
public class ActivityDTOCustomMappingComponent {

	@Autowired
	private ModelMapper modelMapper;

	public void configureMappings() {
		modelMapper.typeMap(Activity.class, ActivityDto.class).addMapping(src -> src.getSupplier().getName(),
				ActivityDto::setSupplierName);
	}

	@PostConstruct
	private void postConstruct() {
		configureMappings();
	}

}
