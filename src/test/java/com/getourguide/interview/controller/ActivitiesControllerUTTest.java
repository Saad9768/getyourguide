package com.getourguide.interview.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

import com.getourguide.interview.dto.ActivityDto;
import com.getourguide.interview.service.ActivityService;

class ActivitiesControllerUTTest {

	@Mock
	private ActivityService activityService;

	@InjectMocks
	private ActivitiesController activitiesController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetActivities() {
		Page<ActivityDto> mockPage = new PageImpl<>(List.of(new ActivityDto()));
		when(activityService.getActivities(0, 10)).thenReturn(mockPage);

		ResponseEntity<Page<ActivityDto>> response = activitiesController.activities(0, 10);
		assertNotNull(response);
		assertEquals(1, response.getBody().getTotalElements());
	}

	@Test
	void testGetActivityById() {
		ActivityDto mockActivity = new ActivityDto();
		when(activityService.getActivity(1L)).thenReturn(mockActivity);

		ResponseEntity<ActivityDto> response = activitiesController.activities(1L);
		assertNotNull(response);
		assertEquals(mockActivity, response.getBody());
	}

	@Test
	void testSearchActivities() {
		Page<ActivityDto> mockPage = new PageImpl<>(List.of(new ActivityDto()));
		when(activityService.searchActivities("test", 0, 10)).thenReturn(mockPage);

		ResponseEntity<Page<ActivityDto>> response = activitiesController.activitiesSearch(0, 10, "test");
		assertNotNull(response);
		assertEquals(1, response.getBody().getTotalElements());
	}

	@Test
	void testAddActivity() {
		ActivityDto mockActivity = new ActivityDto();
		when(activityService.addActivity(any(ActivityDto.class))).thenReturn(mockActivity);

		ResponseEntity<ActivityDto> response = activitiesController.addActivity(new ActivityDto());
		assertNotNull(response);
		assertEquals(mockActivity, response.getBody());
	}
}
