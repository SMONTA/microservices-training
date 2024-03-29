package com.samotek.CloudGateway.controller;

import com.samotek.CloudGateway.model.AuthenticationResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

/**
 * @author Saber Montassar [montassar.saber1@gmail.com]
 * @date 12/28/22
 */
@RestController
@RequestMapping("/authenticate")
@Log4j2
public class AuthenticationController {

  @GetMapping("/login")
  public ResponseEntity<AuthenticationResponse> login(
      @AuthenticationPrincipal OidcUser oidcUser,
      Model model,
      @RegisteredOAuth2AuthorizedClient("okta") OAuth2AuthorizedClient client
  ) {
    log.info("Login attempt for userId: {}",oidcUser.getEmail());
    var authenticationResponse =
        AuthenticationResponse.builder()
                              .userId(oidcUser.getEmail())
                              .accessToken(client.getAccessToken().getTokenValue())
                              .refreshToken(client.getRefreshToken().getTokenValue())
                              .expiresAt(client.getAccessToken()
                                               .getExpiresAt()
                                               .getEpochSecond())
                              .authorityList(oidcUser.getAuthorities()
                                                     .stream()
                                                     .map(GrantedAuthority::getAuthority)
                                                     .collect(Collectors.toList()))
                              .build();

    return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);

  }
}
