package com.flipkart.mapper;

import com.flipkart.domain.Product;
import com.flipkart.dto.ProductDto;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class ProductMapper {

    public ProductDto from(Product product) {
        ProductDto productDto = ProductDto.builder()
                .category(product.getCategory())
                .productName(product.getProductName())
                .description(product.getDescription())
                .sellerName(product.getSellerName())
                .cost(product.getCost())
                .build();

        return productDto;
    }
}
