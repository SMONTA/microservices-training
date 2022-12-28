package com.samotek.CloudGateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * @author Saber Montassar [montassar.saber1@gmail.com]
 * @date 12/28/22
 */

@Configuration
@EnableWebFluxSecurity
public class OktaOAuthWebSecurity {

  @Bean
  public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity httpSecurity){
     httpSecurity.authorizeExchange()
                .anyExchange().authenticated()
                .and()
                .oauth2Login()
                .and()
                .oauth2ResourceServer()
                .jwt();

     return httpSecurity.build();

  }
}
