package com.samotek.orderservice.external.intercepter;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

import java.io.IOException;

/**
 * @author Saber Montassar [montassar.saber1@gmail.com]
 * @date 12/28/22
 */
public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {

  private final OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager;

  public RestTemplateInterceptor(
      OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager) {
    this.oAuth2AuthorizedClientManager
        = oAuth2AuthorizedClientManager;
  }

  @Override
  public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
      throws IOException {
    request.getHeaders().add("Authorization",
                             "Bearer " +
                             oAuth2AuthorizedClientManager
                                 .authorize(OAuth2AuthorizeRequest
                                                .withClientRegistrationId("internal-client")
                                                .principal("internal")
                                                .build())
                                 .getAccessToken().getTokenValue());

    return execution.execute(request, body);
  }
}
