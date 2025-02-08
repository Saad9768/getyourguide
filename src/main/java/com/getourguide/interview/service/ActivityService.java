package com.getourguide.interview.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.getourguide.interview.dto.ActivityDto;
import com.getourguide.interview.entity.Activity;
import com.getourguide.interview.repository.ActivityRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ActivityService {
    private final ActivityRepository activityRepository;

    public List<ActivityDto> getActivities() {
        return activityRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ActivityDto getActivity(Long activityId) {
        return activityRepository.findById(activityId)
                .map(this::convertToDto)
                .orElse(null);
    }

    public List<ActivityDto> searchActivities(String search) {
        return activityRepository.findByTitleContaining(search)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private ActivityDto convertToDto(Activity activity) {
        return ActivityDto.builder()
                .id(activity.getId())
                .title(activity.getTitle())
                .price(activity.getPrice())
                .currency(activity.getCurrency())
                .rating(activity.getRating())
                .specialOffer(activity.isSpecialOffer())
                .supplier(activity.getSupplier())
                .supplierName(Objects.isNull(activity.getSupplier()) ? "" : activity.getSupplier().getName())
                .build();
    }
}
