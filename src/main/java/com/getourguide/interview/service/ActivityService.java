package com.getourguide.interview.service;

import org.springframework.data.domain.Page;

import com.getourguide.interview.dto.ActivityDto;

public interface ActivityService {
	Page<ActivityDto> getActivities(int page, int size);

	ActivityDto getActivity(Long activityId);

	ActivityDto addActivity(ActivityDto activityDto);

	Page<ActivityDto> searchActivities(String search, int page, int size);
}
