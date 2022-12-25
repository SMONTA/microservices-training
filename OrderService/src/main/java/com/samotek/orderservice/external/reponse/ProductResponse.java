package com.samotek.orderservice.external.reponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Saber Montassar [montassar.saber1@gmail.com]
 * @date 12/25/22
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

  private String name;
  private long productId;
  private long quantity;
  private long price;
}
