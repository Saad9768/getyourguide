package com.getourguide.interview.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.getourguide.interview.dto.ActivityDto;
import com.getourguide.interview.entity.Activity;
import com.getourguide.interview.entity.Supplier;
import com.getourguide.interview.repository.ActivityRepository;
import com.getourguide.interview.repository.SupplierRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ActivityService {
	private final ActivityRepository activityRepository;

	private final SupplierRepository supplierRepository;

	public Page<ActivityDto> getActivities(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Activity> activities = activityRepository.findAll(pageable);

		List<ActivityDto> activitiesDto = activities.getContent().stream().map(ActivityDto::convertToDto)
				.collect(Collectors.toList());

		return new PageImpl<>(activitiesDto, pageable, activities.getTotalElements());
	}

	public ActivityDto getActivity(Long activityId) {
		return activityRepository.findById(activityId).map(ActivityDto::convertToDto)
				.orElseThrow(() -> new IllegalArgumentException("Activity not found with ID: " + activityId));
	}

	public Page<ActivityDto> searchActivities(String search, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);

		Page<Activity> activities = activityRepository.findByTitleContaining(search, pageable);
		List<ActivityDto> activitiesDto = activities.getContent().stream().map(ActivityDto::convertToDto)
				.collect(Collectors.toList());

		return new PageImpl<>(activitiesDto, pageable, activities.getTotalElements());
	}

    @Transactional
    public ActivityDto addActivity(ActivityDto activityDto) {
        Supplier supplier = supplierRepository.findById(activityDto.getSupplier().getId())
                .orElseThrow(() -> new IllegalArgumentException("Supplier not found with ID: " + activityDto.getSupplier().getId()));

        activityDto.setSupplier(supplier);
        Activity activity = activityDto.convertToEntity(activityDto);

        // Save the new Activity
        Activity savedActivity = activityRepository.save(activity);

        // Convert back to DTO and return
        return ActivityDto.convertToDto(savedActivity);
    }

}
