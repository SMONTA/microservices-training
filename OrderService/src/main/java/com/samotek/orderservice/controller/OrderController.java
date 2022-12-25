package com.samotek.orderservice.controller;

import com.samotek.orderservice.model.OrderRequest;
import com.samotek.orderservice.model.OrderResponse;
import com.samotek.orderservice.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Saber Montassar [montassar.saber1@gmail.com]
 * @date 12/9/22
 */
@RestController
@RequestMapping("/orders/v1")
@Log4j2
public class OrderController {

  @Autowired
  private OrderService orderService;

  @PostMapping("/placeOrder")
  public ResponseEntity<Long> placeOrder(@RequestBody OrderRequest request) {
    var orderID = orderService.placeOrder(request);
    log.info("Order created with id: {}", orderID);
    return new ResponseEntity<>(orderID, HttpStatus.OK);
  }

  @GetMapping("/{orderId}")
  public ResponseEntity<OrderResponse> getOrderDetails(@PathVariable(name = "orderId") long orderId) {
    OrderResponse orderDetails = orderService.getOrderDetails(orderId);
    return new ResponseEntity<>(orderDetails, HttpStatus.OK);
  }

}
