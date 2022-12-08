package com.samotek.ProductService.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Saber Montassar [montassar.saber1@gmail.com]
 * @date 12/8/22
 */
@Data
@NoArgsConstructor
@SuperBuilder
public class ProductResponse extends Product {

  private long productId;
}
