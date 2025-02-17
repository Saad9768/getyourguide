package com.getourguide.interview.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.getourguide.interview.dto.ActivityDto;
import com.getourguide.interview.dto.SupplierDto;
import com.getourguide.interview.entity.Activity;
import com.getourguide.interview.entity.Supplier;
import com.getourguide.interview.mapper.DTOMapper;
import com.getourguide.interview.repository.ActivityRepository;
import com.getourguide.interview.repository.SupplierRepository;

@ExtendWith(MockitoExtension.class)
class ActivityServiceImplTest {

    @Mock
    private ActivityRepository activityRepository;

    @Mock
    private SupplierRepository supplierRepository;

    @Mock
    private DTOMapper dtoMapper;

    @InjectMocks
    private ActivityServiceImpl activityService;

    private Activity activity;
    private ActivityDto activityDto;
    private Supplier supplier;
    private SupplierDto supplierDto;

    @BeforeEach
    void setUp() {
        supplier = new Supplier();
        supplier.setId(1L);
        supplier.setName("Test Supplier");

        activity = new Activity();
        activity.setId(1L);
        activity.setTitle("Test Activity");
        activity.setSupplier(supplier);

        supplierDto = new SupplierDto();
        supplierDto.setId(1L);
        supplierDto.setName("Test Supplier");

        activityDto = new ActivityDto();
        activityDto.setId(1L);
        activityDto.setTitle("Test Activity");
        activityDto.setSupplier(supplierDto);
    }

    @Test
    void testGetActivities() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Activity> activityPage = new PageImpl<>(List.of(activity), pageable, 1);

        when(activityRepository.findAll(pageable)).thenReturn(activityPage);
        when(dtoMapper.convertListToDtoList(any(), eq(ActivityDto.class))).thenReturn(List.of(activityDto));

        Page<ActivityDto> result = activityService.getActivities(0, 10);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Test Activity", result.getContent().get(0).getTitle());
    }

    @Test
    void testGetActivity() {
        when(activityRepository.findById(1L)).thenReturn(Optional.of(activity));
        when(dtoMapper.convertToDto(activity, ActivityDto.class)).thenReturn(activityDto);

        ActivityDto result = activityService.getActivity(1L);

        assertNotNull(result);
        assertEquals("Test Activity", result.getTitle());
    }

    @Test
    void testGetActivity_NotFound() {
        when(activityRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> activityService.getActivity(1L));
    }

    @Test
    void testSearchActivities() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Activity> activityPage = new PageImpl<>(List.of(activity), pageable, 1);

        when(activityRepository.findByTitleContaining("Test", pageable)).thenReturn(activityPage);
        when(dtoMapper.convertListToDtoList(any(), eq(ActivityDto.class))).thenReturn(List.of(activityDto));

        Page<ActivityDto> result = activityService.searchActivities("Test", 0, 10);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Test Activity", result.getContent().get(0).getTitle());
    }

    @Test
    void testAddActivity() {
        when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));
        when(dtoMapper.convertToEntity(activityDto, Activity.class)).thenReturn(activity);
        when(activityRepository.save(activity)).thenReturn(activity);
        when(dtoMapper.convertToDto(activity, ActivityDto.class)).thenReturn(activityDto);

        ActivityDto result = activityService.addActivity(activityDto);

        assertNotNull(result);
        assertEquals("Test Activity", result.getTitle());
    }

    @Test
    void testAddActivity_SupplierNotFound() {
        when(supplierRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> activityService.addActivity(activityDto));
    }
}
