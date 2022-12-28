package com.samotek.orderservice.external.intercepter;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

/**
 * @author Saber Montassar [montassar.saber1@gmail.com]
 * @date 12/28/22
 */
public class OAuthRequestInterceptor implements RequestInterceptor {

  @Autowired
  private OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager;

  @Override
  public void apply(RequestTemplate requestTemplate) {
    requestTemplate.header("Authorization", "Bearer " +
                                            oAuth2AuthorizedClientManager
                                                .authorize(OAuth2AuthorizeRequest
                                                               .withClientRegistrationId("internal-client")
                                                               .principal("internal")
                                                               .build())
                                                .getAccessToken().getTokenValue()

    );

  }
}
