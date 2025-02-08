package com.getourguide.interview.dto;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.getourguide.interview.entity.Activity;
import com.getourguide.interview.entity.Supplier;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    
    // Convert Activity Entity to DTO
    public static ActivityDto convertToDto(Activity activity) {
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


    public static Activity convertToEntity(ActivityDto activityDto) {
        return Activity.builder()
                .id(activityDto.getId())
                .title(activityDto.getTitle())
                .price(activityDto.getPrice())
                .currency(activityDto.getCurrency())
                .rating(activityDto.getRating())
                .specialOffer(activityDto.isSpecialOffer())
                .supplier(activityDto.getSupplier()) // Set the Supplier
                .build();
    }
}
