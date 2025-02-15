package com.getourguide.interview.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.getourguide.interview.dto.ActivityDto;
import com.getourguide.interview.dto.SupplierDto;
import com.getourguide.interview.entity.Activity;
import com.getourguide.interview.entity.Supplier;
import com.getourguide.interview.repository.ActivityRepository;
import com.getourguide.interview.repository.SupplierRepository;
import com.getourguide.interview.service.impl.ActivityServiceImpl;

class ActivityServiceImplTest {

	@Mock
	private ActivityRepository activityRepository;

	@Mock
	private SupplierRepository supplierRepository;

	@InjectMocks
	private ActivityServiceImpl activityService;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetActivities() {
		// Arrange
		Pageable pageable = PageRequest.of(0, 10);
		Activity activity = new Activity();
		activity.setId(1L);
		activity.setTitle("Test Activity");

		Page<Activity> mockPage = new PageImpl<>(List.of(activity), pageable, 1);
		when(activityRepository.findAll(pageable)).thenReturn(mockPage);

		// Act
		Page<ActivityDto> result = activityService.getActivities(0, 10);

		// Assert
		assertNotNull(result);
		assertEquals(1, result.getTotalElements());
		verify(activityRepository, times(1)).findAll(pageable);
	}

	@Test
	void testGetActivity_Exists() {
		// Arrange
		Activity activity = new Activity();
		activity.setId(1L);
		activity.setTitle("Test Activity");

		when(activityRepository.findById(1L)).thenReturn(Optional.of(activity));

		// Act
		ActivityDto result = activityService.getActivity(1L);

		// Assert
		assertNotNull(result);
		assertEquals("Test Activity", result.getTitle());
		verify(activityRepository, times(1)).findById(1L);
	}

	@Test
	void testGetActivity_NotFound() {
		// Arrange
		when(activityRepository.findById(1L)).thenReturn(Optional.empty());

		// Act & Assert
		Exception exception = assertThrows(IllegalArgumentException.class, () -> activityService.getActivity(1L));
		assertEquals("Activity not found with ID: 1", exception.getMessage());
	}

	@Test
	void testSearchActivities() {
		// Arrange
		Pageable pageable = PageRequest.of(0, 10);
		Activity activity = new Activity();
		activity.setId(1L);
		activity.setTitle("Test Activity");

		Page<Activity> mockPage = new PageImpl<>(List.of(activity), pageable, 1);
		when(activityRepository.findByTitleContaining("Test", pageable)).thenReturn(mockPage);

		// Act
		Page<ActivityDto> result = activityService.searchActivities("Test", 0, 10);

		// Assert
		assertNotNull(result);
		assertEquals(1, result.getTotalElements());
		verify(activityRepository, times(1)).findByTitleContaining("Test", pageable);
	}

	@Test
	void testAddActivity_Success() {
		// Arrange
		SupplierDto supplierDto = new SupplierDto();
		supplierDto.setId(1L);
		supplierDto.setName("Test Supplier");

		ActivityDto activityDto = new ActivityDto();
		activityDto.setTitle("New Activity");
		activityDto.setSupplier(supplierDto);
		
		Supplier supplier = SupplierDto.convertToEntity(supplierDto);
		Activity activity = new Activity();
		activity.setId(1L);
		activity.setTitle("New Activity");
		activity.setSupplier(supplier);

		when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));
		when(activityRepository.save(any(Activity.class))).thenReturn(activity);

		// Act
		ActivityDto result = activityService.addActivity(activityDto);

		// Assert
		assertNotNull(result);
		assertEquals("New Activity", result.getTitle());
		verify(supplierRepository, times(1)).findById(1L);
		verify(activityRepository, times(1)).save(any(Activity.class));
	}

	@Test
	void testAddActivity_SupplierNotFound() {
		// Arrange
		ActivityDto activityDto = new ActivityDto();
		SupplierDto supplierDto = new SupplierDto();
		supplierDto.setId(1L);
		activityDto.setSupplier(supplierDto);

		when(supplierRepository.findById(1L)).thenReturn(Optional.empty());

		// Act & Assert
		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> activityService.addActivity(activityDto));
		assertEquals("Supplier not found with ID: 1", exception.getMessage());
	}
}