package com.samotek.ProductService.model;

import lombok.Data;

/**
 * @author Saber Montassar [montassar.saber1@gmail.com]
 * @date 12/8/22
 */
@Data
public class ProductRequest {
  private String name;
  private long price;
  private long quantity;
}
