package com.getourguide.interview.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.getourguide.interview.dto.ActivityDto;
import com.getourguide.interview.dto.SupplierDto;
import com.getourguide.interview.entity.Activity;
import com.getourguide.interview.entity.Supplier;
import com.getourguide.interview.mapper.DTOMapper;
import com.getourguide.interview.repository.ActivityRepository;
import com.getourguide.interview.repository.SupplierRepository;
import com.getourguide.interview.service.ActivityService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ActivityServiceImpl implements ActivityService {
	private final ActivityRepository activityRepository;

	private final SupplierRepository supplierRepository;
	
	private final DTOMapper dtoMapper;

	@Override
	public Page<ActivityDto> getActivities(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Activity> activities = activityRepository.findAll(pageable);
		
		List<ActivityDto> activitiesDto = dtoMapper.convertListToDtoList(activities.getContent(), ActivityDto.class);

		return new PageImpl<>(activitiesDto, pageable, activities.getTotalElements());
	}

	@Override
	public ActivityDto getActivity(Long activityId) {
		Activity activity  = activityRepository.findById(activityId)
				.orElseThrow(() -> new IllegalArgumentException("Activity not found with ID: " + activityId));
		
		return dtoMapper.convertToDto(activity, ActivityDto.class);
	}

	@Override
	public Page<ActivityDto> searchActivities(String search, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);

		Page<Activity> activities = activityRepository.findByTitleContaining(search, pageable);
		
		List<ActivityDto> activitiesDto = dtoMapper.convertListToDtoList(activities.getContent(), ActivityDto.class);

		return new PageImpl<>(activitiesDto, pageable, activities.getTotalElements());
	}

    @Transactional
    @Override
    public ActivityDto addActivity(ActivityDto activityDto) {
    	 Activity activity =  dtoMapper.convertToEntity(activityDto, Activity.class);
        Supplier supplier = supplierRepository.findById(activityDto.getSupplier().getId())
                .orElseThrow(() -> new IllegalArgumentException("Supplier not found with ID: " + activityDto.getSupplier().getId()));

        
        activity.setSupplier(supplier);
        
       

        // Save the new Activity
        Activity savedActivity = activityRepository.save(activity);

        return dtoMapper.convertToDto(savedActivity, ActivityDto.class);
    }

}
