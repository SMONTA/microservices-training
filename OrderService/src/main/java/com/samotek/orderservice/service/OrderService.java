package com.samotek.orderservice.service;

import com.samotek.orderservice.model.OrderRequest;

/**
 * @author Saber Montassar [montassar.saber1@gmail.com]
 * @date 12/9/22
 */
public interface OrderService {

  long placeOrder(OrderRequest request);
}
