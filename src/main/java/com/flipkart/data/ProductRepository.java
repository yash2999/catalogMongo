package com.flipkart.data;

import com.flipkart.domain.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
    Flux<Product> findByCategoryIgnoreCaseContainingOrderByCost(String category);
    Flux<Product> findByCategoryIgnoreCaseContaining(String category);

    Flux<Product> findByCategoryIgnoreCaseContainingOrderByCostDesc(String category);

    Flux<Product> findAllByOrderByCostAsc();

    Flux<Product> findAllByOrderByCostDesc();

    Mono<Product> findById(String id);
}
