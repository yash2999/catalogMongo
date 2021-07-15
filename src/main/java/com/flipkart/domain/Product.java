package com.flipkart.domain;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Document
public class Product {
    @Id
    private String id;

    @Pattern(regexp = "^[a-zA-Z]+(\\s[a-zA-Z]+)?$")
    @NotEmpty
    private String sellerName;

    public void setProductName(String productName) {
        this.productName = productName;
    }

    private String productName;

    private String category;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;

    private long cost;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProductName() {
        return productName;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", sellerName='" + sellerName + '\'' +
                ", productName='" + productName + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", cost=" + cost +
                '}';
    }
}
