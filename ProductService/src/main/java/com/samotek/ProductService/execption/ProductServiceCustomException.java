package com.samotek.ProductService.execption;

import lombok.Data;

/**
 * @author Saber Montassar [montassar.saber1@gmail.com]
 * @date 12/8/22
 */
@Data
public class ProductServiceCustomException extends RuntimeException {

  private  String errorCode;
  public ProductServiceCustomException(String message, String errorCode){
    super(message);
    this.errorCode = errorCode;
  }
}
