package com.getourguide.interview.controller;

import com.getourguide.interview.dto.ActivityDto;
import com.getourguide.interview.service.ActivityService;
import java.util.List;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/activities")
@AllArgsConstructor
public class ActivitiesController {

	private final ActivityService activityService;

	@GetMapping
	public ResponseEntity<Page<ActivityDto>> activities(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		return ResponseEntity.ok(activityService.getActivities(page, size));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ActivityDto> activities(@PathVariable Long id) {
		return ResponseEntity.ok(activityService.getActivity(id));
	}

	@GetMapping("/search/{search}")
	public ResponseEntity<Page<ActivityDto>> activitiesSearch(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @PathVariable String search) {
		return ResponseEntity.ok(activityService.searchActivities(search, page, size));
	}
}
