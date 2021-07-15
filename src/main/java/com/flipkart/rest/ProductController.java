package com.flipkart.rest;

import com.flipkart.data.ProductRepository;
import com.flipkart.domain.Product;
import com.flipkart.domain.ProductNotFoundException;
import com.flipkart.dto.ProductDto;
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
import java.util.stream.Collectors;


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

    @PostMapping("/product")
    public ResponseEntity<ProductDto> create(@Valid @RequestBody Product product) {
        Mono<Product> entity = repo.save(product);

//        return entity;

        return new ResponseEntity<ProductDto>(new ProductDto(entity), HttpStatus.CREATED);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDto> find(@PathVariable("id") String id) {
        Mono<Product> product = repo.findById(id);
        ProductDto dto = new ProductDto(product);
        return new ResponseEntity<ProductDto>(dto, HttpStatus.OK);
    }

    @GetMapping("/product")
    public Flux<Product> search(@RequestParam(value = "category", required = false, defaultValue = "") String category,
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

        return product;

//        List<ProductDto> dto = product.stream().map(k -> new ProductDto(k)).collect(Collectors.toList());
//        return new ResponseEntity<List<ProductDto>>(dto, HttpStatus.OK);
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public ResponseEntity<Error> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> errors = result.getFieldErrors();
        Error error = new Error(400, "Invalid Employee");
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
