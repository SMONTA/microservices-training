package com.samotek.orderservice.external.client;

import com.samotek.orderservice.exception.CustomException;
import com.samotek.orderservice.external.request.PaymentRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Saber Montassar [montassar.saber1@gmail.com]
 * @date 12/25/22
 */
@CircuitBreaker(name = "externalCircuitBreaker", fallbackMethod = "paymentFallback")
@FeignClient(name="PAYMENT-SERVICE/payment/v1")
public interface PaymentService {

  @PostMapping("/doPayment")
  ResponseEntity<Long> doPayment(@RequestBody PaymentRequest request);

  default ResponseEntity<Long> paymentFallback(Exception e){
    throw new CustomException("PAYMENT Service is not available", "PAYMENT_SERVICE_UNAVAILABLE", 500);
  }
}
