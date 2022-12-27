package com.samotek.orderservice.external.client;

import com.samotek.orderservice.exception.CustomException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Saber Montassar [montassar.saber1@gmail.com]
 * @date 12/20/22
 */
@CircuitBreaker(name = "externalCircuitBreaker", fallbackMethod = "productFallback")
@FeignClient(name="PRODUCT-SERVICE/products/v1/")
public interface ProductService {

  @PutMapping("/reduceQuantity/{id}")
  ResponseEntity<Void> reduceQuantity(@PathVariable("id") long productId,
                                             @RequestParam long quantity);

  default void productFallback(Exception e){
    throw new CustomException("Product Service is not available", "PRODUCT_SERVICE_UNAVAILABLE", 500);
  }
}
