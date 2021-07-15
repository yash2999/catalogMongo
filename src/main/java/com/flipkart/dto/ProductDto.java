package com.flipkart.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;


@Builder
@Data
@ToString
public class ProductDto {
    private String sellerName;
    private String productName;
    private String category;
    private String description;
    private long cost;
}
