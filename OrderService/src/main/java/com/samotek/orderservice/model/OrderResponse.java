package com.samotek.orderservice.model;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

/**
 * @author Saber Montassar [montassar.saber1@gmail.com]
 * @date 12/25/22
 */
@Data
@Builder
public class OrderResponse {
  private long orderId;
  private Instant orderDate;
  private String orderStatus;
  private long amount;
}
