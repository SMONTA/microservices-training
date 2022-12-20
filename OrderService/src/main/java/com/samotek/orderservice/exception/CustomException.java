package com.samotek.orderservice.exception;

import lombok.Data;

/**
 * @author Saber Montassar [montassar.saber1@gmail.com]
 * @date 12/20/22
 */
@Data
public class CustomException extends RuntimeException {

  private String errorCode;
  private int status;

  public CustomException(String message,
                         String errorCode,
                         int status){
    super(message);
    this.errorCode = errorCode;
    this.status = status;
  }
}
