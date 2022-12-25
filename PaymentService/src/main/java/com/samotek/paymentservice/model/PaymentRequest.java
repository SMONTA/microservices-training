package com.samotek.paymentservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Saber Montassar [montassar.saber1@gmail.com]
 * @date 12/25/22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequest {
  private long orderId;
  private long amount;
  private String referenceNumber;
  private PaymentMode paymentMode;
}
