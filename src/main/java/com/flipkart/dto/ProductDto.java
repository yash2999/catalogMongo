package com.flipkart.dto;

import com.flipkart.domain.Product;
import reactor.core.publisher.Mono;

public class ProductDto {
    private String sellerName;

    public ProductDto(Mono<Product> product) {
        Product productTemp = product.block();
        this.sellerName = productTemp.getSellerName();
        this.productName = productTemp.getProductName();
        this.category = productTemp.getCategory();
        this.description = productTemp.getDescription();
        this.cost = productTemp.getCost();
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    private String productName;

    private String id;

    private String category;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;

    private long cost;

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
                ", sellerName='" + sellerName + '\'' +
                ", productName='" + productName + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", cost=" + cost +
                '}';
    }
}
