package com.getourguide.interview.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.getourguide.interview.entity.Supplier;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ActivityDto {
    private Long id;
    private String title;
    private int price;
    private String currency;
    private double rating;
    private boolean specialOffer;
    private String supplierName;
    @JsonIgnoreProperties({"activities"})
    private Supplier supplier;
}
