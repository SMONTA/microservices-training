package com.samotek.orderservice.external.decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samotek.orderservice.exception.CustomException;
import com.samotek.orderservice.external.reponse.ErrorResponse;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

/**
 * @author Saber Montassar [montassar.saber1@gmail.com]
 * @date 12/20/22
 */
@Log4j2
public class CustomErrorDecoder implements ErrorDecoder {

  @Override
  public Exception decode(String s, Response response) {
    var objectMapper = new ObjectMapper();
    log.info("::{}", response.request().url());
    log.info("::{}", response.request().headers());
    try {
      var errorResponse =
          objectMapper.readValue(response.body().asInputStream(), ErrorResponse.class);
      return new CustomException(errorResponse.getMessage(),errorResponse.getErrorCode(),
                                 response.status());
    }
    catch (IOException e) {
      throw new CustomException("Internal Server Error", "INTERNAL_SERVER_ERROR", 500);
    }
  }
}
