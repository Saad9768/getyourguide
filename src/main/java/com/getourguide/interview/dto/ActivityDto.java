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
    private SupplierDto supplier;
    
    public static ActivityDto convertToDto(Activity activity) {
    	if(activity == null) {
    		return null;
    	}
    	SupplierDto supplierDto = SupplierDto.convertToDto(activity.getSupplier(), false);
        return ActivityDto.builder()
                .id(activity.getId())
                .title(activity.getTitle())
                .price(activity.getPrice())
                .currency(activity.getCurrency())
                .rating(activity.getRating())
                .specialOffer(activity.isSpecialOffer())
                .supplier(supplierDto)
                .supplierName(Objects.isNull(supplierDto) ? "" : supplierDto.getName())
                .build();
    }


    public static Activity convertToEntity(ActivityDto activityDto) {
    	if(activityDto == null) {
    		return null;
    	}
    	Supplier supplier = SupplierDto.convertToEntity(activityDto.getSupplier());
        return Activity.builder()
                .id(activityDto.getId())
                .title(activityDto.getTitle())
                .price(activityDto.getPrice())
                .currency(activityDto.getCurrency())
                .rating(activityDto.getRating())
                .specialOffer(activityDto.isSpecialOffer())
                .supplier(supplier)
                .build();
    }
}
