package com.samotek.orderservice.external.client;

import com.samotek.orderservice.external.request.PaymentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Saber Montassar [montassar.saber1@gmail.com]
 * @date 12/25/22
 */
@FeignClient(name="PAYMENT-SERVICE/payment/v1")
public interface PaymentService {

  @PostMapping("/doPayment")
  ResponseEntity<Long> doPayment(@RequestBody PaymentRequest request);
}
