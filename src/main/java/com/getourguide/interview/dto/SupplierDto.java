package com.getourguide.interview.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.getourguide.interview.entity.Activity;
import com.getourguide.interview.entity.Supplier;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierDto {

    private Long id;
    private String name;
    private String address;
    private String zip;
    private String city;
    private String country;

    @JsonIgnoreProperties({ "supplier" })
    private List<Activity> activities;

    // Convert Supplier entity → SupplierDto
    public static SupplierDto fromEntity(Supplier supplier) {
        return SupplierDto.builder()
                .id(supplier.getId())
                .name(supplier.getName())
                .address(supplier.getAddress())
                .zip(supplier.getZip())
                .city(supplier.getCity())
                .country(supplier.getCountry())
                .activities(supplier.getActivities())
                .build();
    }
}
