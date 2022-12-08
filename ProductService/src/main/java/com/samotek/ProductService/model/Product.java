package com.samotek.ProductService.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Saber Montassar [montassar.saber1@gmail.com]
 * @date 12/8/22
 */
@Data
@SuperBuilder
@NoArgsConstructor
public abstract class Product {
  private String name;
  private long price;
  private long quantity;
}
