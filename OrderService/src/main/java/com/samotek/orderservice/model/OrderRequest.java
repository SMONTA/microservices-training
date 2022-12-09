package com.samotek.orderservice.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author Saber Montassar [montassar.saber1@gmail.com]
 * @date 12/9/22
 */
@Data
@Builder
public class OrderRequest {
  private long productId;
  private long totalAmount;
  private long quantity;
  private PaymentMode paymentMode;
}
