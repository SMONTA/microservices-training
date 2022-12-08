package com.samotek.ProductService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Saber Montassar [montassar.saber1@gmail.com]
 * @date 12/8/22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
  private String message;
  private String errorCode;
}
