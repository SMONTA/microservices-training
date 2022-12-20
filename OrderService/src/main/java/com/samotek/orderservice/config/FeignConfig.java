package com.samotek.orderservice.config;

import com.samotek.orderservice.external.decoder.CustomErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Saber Montassar [montassar.saber1@gmail.com]
 * @date 12/20/22
 */
@Configuration
public class FeignConfig {

  @Bean
  ErrorDecoder errorDecoder(){
    return new CustomErrorDecoder();
  }
}
