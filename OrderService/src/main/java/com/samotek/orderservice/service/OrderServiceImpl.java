package com.samotek.orderservice.service;

import com.samotek.orderservice.entity.Order;
import com.samotek.orderservice.external.client.ProductService;
import com.samotek.orderservice.model.OrderRequest;
import com.samotek.orderservice.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * @author Saber Montassar [montassar.saber1@gmail.com]
 * @date 12/9/22
 */
@Service
@Log4j2
public class OrderServiceImpl implements OrderService {

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private ProductService productService;

  @Override
  public long placeOrder(OrderRequest request) {

//    Order Entity -> Save the data with status order
//    Product Service -> Block Products (reduce quantity)
//    Payment Service: Payments -> Success -> COMPLETE, Else CANCELLED
    log.info("Placing order request: {}", request);
    productService.reduceQuantity(request.getProductId(), request.getQuantity());
    log.info("Creating an order with status CREATED");
    var order = Order.builder()
                     .productId(request.getProductId())
                     .amount(request.getTotalAmount())
                     .orderDate(Instant.now())
                     .orderStatus("CREATED")
                     .quantity(request.getQuantity())
                     .build();

    orderRepository.save(order);
    log.info("Order successfully created with id: {}", order.getId());
    return order.getId();
  }
}
