package com.samotek.paymentservice.controller;

import com.samotek.paymentservice.model.PaymentRequest;
import com.samotek.paymentservice.model.PaymentResponse;
import com.samotek.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author Saber Montassar [montassar.saber1@gmail.com]
 * @date 12/25/22
 */
@RestController
@RequestMapping("/payment/v1")
public class PaymentController {

  @Autowired
  private PaymentService paymentService;

  @PostMapping("/doPayment")
  public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest request) {
    return new ResponseEntity<>(paymentService.doPayment(request),
                                HttpStatus.OK);
  }

  @GetMapping("/order/{orderId}")
  public ResponseEntity<PaymentResponse> getPaymentDetailsByOrderId(@PathVariable long orderId) {
    var paymentDetails = paymentService.getPaymentDetailsByOrderId(orderId);
    return new ResponseEntity<>(paymentDetails, HttpStatus.OK);
  }
}
