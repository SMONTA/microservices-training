package com.samotek.CloudGateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Saber Montassar [montassar.saber1@gmail.com]
 * @date 12/27/22
 */
@RestController
public class FallbackController {

  @GetMapping("/orderServiceFallBack")
  public String orderServiceFallback(){
    return "Order Service is DOWN";
  }

  @GetMapping("/productServiceFallBack")
  public String productServiceFallback(){
    return "Product Service is DOWN";
  }

  @GetMapping("/paymentServiceFallBack")
  public String paymentServiceFallback(){
    return "Payment Service is DOWN";
  }
}
