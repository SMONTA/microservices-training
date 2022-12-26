package com.samotek.orderservice.external.reponse;

import com.samotek.orderservice.model.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * @author Saber Montassar [montassar.saber1@gmail.com]
 * @date 12/26/22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
  private long paymentId;
  private String status;
  private PaymentMode paymentMode;
  private long amount;
  private Instant paymentDate;
  private long orderId;
}
