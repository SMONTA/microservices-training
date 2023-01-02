package com.samotek.orderservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.samotek.orderservice.OrderServiceConfig;
import com.samotek.orderservice.entity.Order;
import com.samotek.orderservice.model.OrderRequest;
import com.samotek.orderservice.model.PaymentMode;
import com.samotek.orderservice.repository.OrderRepository;
import com.samotek.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Saber Montassar [montassar.saber1@gmail.com]
 * @date 1/1/23
 */
@SpringBootTest("server.port=0")
@EnableConfigurationProperties
@AutoConfigureMockMvc
@ContextConfiguration(classes = {OrderServiceConfig.class})
class OrderControllerTest {

  static WireMockExtension wireMockServer =
      WireMockExtension.newInstance()
                       .options(WireMockConfiguration.wireMockConfig()
                                                     .port(8080))
                       .build();

  private final ObjectMapper objectMapper = new ObjectMapper()
      .findAndRegisterModules()
      .configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false)
      .configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);

  @Autowired
  private OrderService orderService;
  @Autowired
  private OrderRepository orderRepository;
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private CircuitBreakerRegistry circuitBreakerRegistry;

  @BeforeEach
  void setup() throws IOException {
    getProductDetailsResponse();
    doPayment();
    getPaymentDetails();
    reduceQuantity();
  }

  private void reduceQuantity() {
    wireMockServer.stubFor(
        WireMock.post(WireMock.urlMatching("/products/v1/reduceQuantity/.*"))
                .willReturn(WireMock.aResponse()
                                    .withStatus(HttpStatus.OK.value())
                                    .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                )
    );
  }

  private void getPaymentDetails() throws IOException {
    wireMockServer.stubFor(
        WireMock.get(WireMock.urlMatching("/payment/v1/.*"))
                .willReturn(WireMock.aResponse()
                                    .withStatus(HttpStatus.OK.value())
                                    .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                    .withBody(StreamUtils.copyToString(
                                        OrderControllerTest.class.getResourceAsStream("mock/GetPayment.json"),
                                        Charset.defaultCharset())
                                    )
                )
    );

  }

  private void doPayment() {
//    POST /doPayment
    wireMockServer.stubFor(WireMock.post(WireMock.urlEqualTo("/payment/v1"))
                                   .willReturn(WireMock.aResponse()
                                                       .withStatus(HttpStatus.OK.value())
                                                       .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE))
    );
  }

  private void getProductDetailsResponse() throws IOException {
//    GET /product/1
    wireMockServer.stubFor(
        WireMock.get("products/v1/1")
                .willReturn(WireMock.aResponse()
                                    .withStatus(HttpStatus.OK.value())
                                    .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                    .withBody(StreamUtils.copyToString(
                                        OrderControllerTest.class.getResourceAsStream("mock/GetProduct.json"),
                                        Charset.defaultCharset())))
    );

  }

  @Test
  public void test_WhenPlaceOrder_DoPayment_Success() throws Exception {
//    Place order
//    check get order by order id from db
//    check output

    OrderRequest orderRequest = getMockOrderRequest();
    MvcResult mvcResult =
        mockMvc.perform(MockMvcRequestBuilders.post("/orders/v1/placeOrder/")
                                              .with(SecurityMockMvcRequestPostProcessors.jwt()
                                                                                        .authorities(new SimpleGrantedAuthority(
                                                                                            "Customer")))
                                              .contentType(MediaType.APPLICATION_JSON_VALUE)
                                              .content(objectMapper.writeValueAsString(orderRequest)))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andReturn();

    String orderId = mvcResult.getResponse().getContentAsString();

    Optional<Order> optionalOrder = orderRepository.findById(Long.valueOf(orderId));
    assertTrue(optionalOrder.isPresent());

    var o  = optionalOrder.get();
    assertEquals(Long.parseLong(orderId), o.getId());
    assertEquals("PLACED", o.getOrderStatus());
    assertEquals(orderRequest.getTotalAmount(), o.getAmount());
    assertEquals(orderRequest.getQuantity(), o.getQuantity());

  }

  private OrderRequest getMockOrderRequest() {
    return OrderRequest.builder()
                       .productId(1)
                       .paymentMode(PaymentMode.CASH)
                       .quantity(100)
                       .totalAmount(200)
                       .build();
  }
}
