package com.getourguide.interview.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.getourguide.interview.dto.ActivityDto;
import com.getourguide.interview.dto.SupplierDto;
import com.getourguide.interview.entity.Activity;
import com.getourguide.interview.entity.Supplier;
import com.getourguide.interview.mapper.DTOMapper;
import com.getourguide.interview.repository.ActivityRepository;
import com.getourguide.interview.repository.SupplierRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ActivitiesControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Supplier testSupplier;
    
    @Autowired
    private DTOMapper dtoMapper; 

    @BeforeEach
    void setUp() {
        // Clean database before each test
        activityRepository.deleteAll();
        supplierRepository.deleteAll();

        // Add test supplier
        testSupplier = supplierRepository.save(new Supplier(null, "Test Supplier", "Some Address", "12345", "Berlin", "Germany", null));

        // Add some test activities
        Activity activity1 = new Activity(null, "Berlin Tour", 50, "EUR", 4.5, false, testSupplier);
        Activity activity2 = new Activity(null, "Munich Walk", 40, "EUR", 4.8, true, testSupplier);
        activityRepository.saveAll(List.of(activity1, activity2));
    }

    @Test
    void testGetActivities() throws Exception {
        mockMvc.perform(get("/activities")
                .param("page", "0")
                .param("size", "10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2));
    }

    @Test
    void testGetActivityById() throws Exception {
        Activity activity = activityRepository.findAll().get(0);

        mockMvc.perform(get("/activities/{id}", activity.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(activity.getTitle()));
    }

    @Test
    void testSearchActivities() throws Exception {
        mockMvc.perform(get("/activities/search/{search}", "Berlin")
                .param("page", "0")
                .param("size", "10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Berlin Tour"));
    }

    @Test
    void testAddActivity() throws Exception {
	
    	ActivityDto activityDto = new ActivityDto();
    	activityDto.setId(null);
    	activityDto.setTitle("Hamburg Cruise");
    	activityDto.setPrice(70);
    	activityDto.setCurrency("EUR");
    	activityDto.setRating(4.7);
    	activityDto.setSpecialOffer(false);
    	
    	SupplierDto supplierDto = dtoMapper.convertToDto(testSupplier, SupplierDto.class);
    	activityDto.setSupplier(supplierDto);
    	
        mockMvc.perform(post("/activities")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(activityDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Hamburg Cruise"));

		assertEquals(3, activityRepository.findAll().size());
    }
}
