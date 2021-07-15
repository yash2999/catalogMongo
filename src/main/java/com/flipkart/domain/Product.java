package com.flipkart.domain;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Document
@Data
@ToString
public class Product {
    @Id
    private String id;

    @Pattern(regexp = "^[a-zA-Z]+(\\s[a-zA-Z]+)?$")
    @NotEmpty
    private String sellerName;

    private String productName;

    private String category;

    private String description;

    private long cost;


}
