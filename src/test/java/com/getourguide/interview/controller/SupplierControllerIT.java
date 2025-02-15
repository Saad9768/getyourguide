package com.getourguide.interview.controller;

import static org.assertj.core.api.Assertions.assertThat;
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
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.getourguide.interview.dto.SupplierDto;
import com.getourguide.interview.entity.Supplier;
import com.getourguide.interview.repository.SupplierRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class SupplierControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // Clean database before each test
        supplierRepository.deleteAll();

        // Add some test data
        Supplier supplier1 = new Supplier(null, "Supplier One", "Address 1", "10001", "Berlin", "Germany", null);
        Supplier supplier2 = new Supplier(null, "Supplier Two", "Address 2", "10002", "Munich", "Germany", null);
        supplierRepository.saveAll(List.of(supplier1, supplier2));
    }

    @Test
    void testGetSuppliers() throws Exception {
        mockMvc.perform(get("/suppliers")
                .param("page", "0")
                .param("size", "10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2));
    }

    @Test
    void testSearchSuppliers() throws Exception {
        mockMvc.perform(get("/suppliers/search")
                .param("query", "Berlin")
                .param("page", "0")
                .param("size", "10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].city").value("Berlin"));
    }

    @Test
    void testAddSupplier() throws Exception {
        SupplierDto supplierDto = new SupplierDto(null, "New Supplier", "New Address", "20002", "Hamburg", "Germany", null);

        mockMvc.perform(post("/suppliers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(supplierDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Supplier"));

        // Verify that it was saved in the database
        assertThat(supplierRepository.findAll()).hasSize(3);
    }
}
