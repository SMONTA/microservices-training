package com.samotek.paymentservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author Saber Montassar [montassar.saber1@gmail.com]
 * @date 12/28/22
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.authorizeRequests(authRequests -> authRequests.antMatchers("/payment/**")
                                                               .hasAuthority("SCOPE_internal")
                                                               .anyRequest()
                                                               .authenticated())
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
    return httpSecurity.build();
  }
}
