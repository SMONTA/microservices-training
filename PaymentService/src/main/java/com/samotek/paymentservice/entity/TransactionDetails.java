package com.samotek.paymentservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Instant;

/**
 * @author Saber Montassar [montassar.saber1@gmail.com]
 * @date 12/25/22
 */
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  private long orderId;
  private String paymentMode;
  private String referenceNumber;
  private Instant paymentDate;
  private String paymentStatus;
  private long amount;
}
