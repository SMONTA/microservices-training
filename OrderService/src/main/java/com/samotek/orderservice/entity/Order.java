package com.samotek.orderservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

/**
 * @author Saber Montassar [montassar.saber1@gmail.com]
 * @date 12/9/22
 */

@Entity
@Table(name = "ORDER_DETAILS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  private long productId;
  private long quantity;
  private Instant orderDate;

  @Column(name = "STATUS")
  private String orderStatus;

  @Column(name = "TOTAL_AMOUNT")
  private long amount;
}


