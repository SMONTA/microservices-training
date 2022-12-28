package com.samotek.CloudGateway.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

/**
 * @author Saber Montassar [montassar.saber1@gmail.com]
 * @date 12/28/22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponse {

  private String userId;
  private String accessToken;
  private String refreshToken;
  private long expireAt;
  private Collection<String> authorityList;
}
