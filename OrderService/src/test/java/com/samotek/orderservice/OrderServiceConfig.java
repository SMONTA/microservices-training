package com.samotek.orderservice;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.annotation.Bean;

/**
 * @author Saber Montassar [montassar.saber1@gmail.com]
 * @date 1/1/23
 */
@TestConfiguration
public class OrderServiceConfig {

  @Bean
  public ServiceInstanceListSupplier supplier(){
    return new TestServiceInstanceListSupplier();
  }
}
