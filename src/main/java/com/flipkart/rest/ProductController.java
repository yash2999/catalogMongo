package com.flipkart.rest;

import com.flipkart.data.ProductRepository;
import com.flipkart.domain.Product;
import com.flipkart.domain.ProductNotFoundException;
import com.flipkart.dto.ProductDto;
import com.flipkart.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.validation.Valid;
import java.util.List;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableSwagger2
@Validated
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductRepository repo;

    @Autowired
    private ProductMapper productMapper;

    @PostMapping("/product")
    public Mono<ProductDto> create(@Valid @RequestBody Product product) {
        Mono<Product> entity = repo.save(product);
        return entity.flatMap(product1 -> Mono.just(productMapper.from(product1)));
    }

    @GetMapping("/product/{id}")
    public Mono<ProductDto> find(@PathVariable("id") String id) {
        Mono<Product> product = repo.findById(id);
        return product.flatMap(product1 -> Mono.just(productMapper.from(product1)));
    }

    @GetMapping("/product")
    public Flux<ProductDto> search(@RequestParam(value = "category", required = false, defaultValue = "") String category,
                                   @RequestParam(value = "order", required = false) Integer order) {
        Flux<Product> product;
        if (category == "") {
            if (order == 1) {
                product = repo.findAllByOrderByCostAsc();
            } else if (order == -1) {
                product = repo.findAllByOrderByCostDesc();
            } else {
                product = repo.findAll();
            }
        }
        else if (order == 1)
            product = repo.findByCategoryIgnoreCaseContainingOrderByCost(category);
        else if (order == -1)
            product = repo.findByCategoryIgnoreCaseContainingOrderByCostDesc(category);
        else product = repo.findByCategoryIgnoreCaseContaining(category);

        return product.flatMap(product1 -> Mono.just(productMapper.from(product1)));
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public ResponseEntity<Error> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> errors = result.getFieldErrors();
        Error error = new Error(400, "Invalid Product");
        for (FieldError fe : errors) {
            error.addError(fe.getField(), fe.getDefaultMessage());
        }
        return new ResponseEntity<Error>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ RuntimeException.class })
    public ResponseEntity<String> handleEmployeeNotFoundException(ProductNotFoundException ex) {
        return new ResponseEntity<String>("Product Not Found", HttpStatus.NOT_FOUND);
    }

}
