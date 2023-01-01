package com.samotek.orderservice.service;

import com.samotek.orderservice.entity.Order;
import com.samotek.orderservice.exception.CustomException;
import com.samotek.orderservice.external.client.PaymentService;
import com.samotek.orderservice.external.client.ProductService;
import com.samotek.orderservice.external.reponse.PaymentResponse;
import com.samotek.orderservice.external.reponse.ProductResponse;
import com.samotek.orderservice.external.request.PaymentRequest;
import com.samotek.orderservice.model.OrderRequest;
import com.samotek.orderservice.model.OrderResponse;
import com.samotek.orderservice.model.PaymentMode;
import com.samotek.orderservice.repository.OrderRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author Saber Montassar [montassar.saber1@gmail.com]
 * @date 12/30/22
 */
@SpringBootTest
public class OrderServiceImplTest {

  @InjectMocks
  OrderService orderService = new OrderServiceImpl();
  @Mock
  private OrderRepository orderRepository;
  @Mock
  private ProductService productService;
  @Mock
  private PaymentService paymentService;
  @Mock
  private RestTemplate restTemplate;

  @BeforeAll
  static void setUp() {

  }

  @AfterAll
  static void tearDown() {

  }

  @DisplayName("GetOrder - success scenario")
  @Test
  void test_getOrderDetailsWhenOrderSuccess() {
//    mocking
    Order order = getMockOrder();
    ProductResponse productResponse = getMockProductResponse();
    PaymentResponse paymentResponse = getMockPaymentResponse();

    when(orderRepository.findById(ArgumentMatchers.anyLong()))
        .thenReturn(Optional.of(order));

    when(restTemplate.getForObject("http://PRODUCT-SERVICE/products/v1/" + order.getProductId(),
                                   ProductResponse.class))
        .thenReturn(productResponse);
    when(restTemplate.getForObject("http://PAYMENT-SERVICE/payment/v1/order/" + order.getId(),
                                   PaymentResponse.class
    )).thenReturn(paymentResponse);

//    call actual method
    OrderResponse orderDetails = orderService.getOrderDetails(1);
//    Verification
    verify(orderRepository, times(1))
        .findById(ArgumentMatchers.anyLong());
    verify(restTemplate, times(1))
        .getForObject("http://PRODUCT-SERVICE/products/v1/" + order.getProductId(),
                      ProductResponse.class);
    verify(restTemplate, times(1))
        .getForObject("http://PAYMENT-SERVICE/payment/v1/order/" + order.getId(),
                      PaymentResponse.class);
//    Assert
    assertNotNull(orderDetails);
    assertEquals(order.getId(), orderDetails.getOrderId());
  }

  @DisplayName("Get Order - Failure Scenario")
  @Test
  void test_WhenGetOrder_NOT_FOUND_then_Nor_found() {
    when(orderRepository.findById(ArgumentMatchers.anyLong()))
        .thenReturn(Optional.ofNullable(null));

    CustomException exception = assertThrows(CustomException.class,
                                             () -> orderService.getOrderDetails(1));
    assertEquals("ORDER_NOT_FOUND", exception.getErrorCode());
    assertEquals(500, exception.getStatus());
    verify(orderRepository, times(1))
        .findById(anyLong());
  }


  @DisplayName("Place Order -  Success Scenario")
  @Test
  void test_When_Place_Order_Success() {
    Order order = getMockOrder();
    OrderRequest orderRequest = getMockOrderRequest();

    when(orderRepository.save(any(Order.class)))
        .thenReturn(order);
    when(productService.reduceQuantity(anyLong(),anyLong()))
        .thenReturn(new ResponseEntity<Void>(HttpStatus.OK));
    when(paymentService.doPayment(any(PaymentRequest.class)))
        .thenReturn(new ResponseEntity<Long>(1L,HttpStatus.OK));

    long orderId = orderService.placeOrder(orderRequest);

    verify(orderRepository, times(2))
        .save(any());
    verify(productService, times(1))
        .reduceQuantity(anyLong(),anyLong());
    verify(paymentService, times(1))
        .doPayment(any(PaymentRequest.class));

    assertEquals(order.getId(), orderId);
  }

  @DisplayName("Place Order -  Payment Failed Scenario")
  @Test
  void test_When_Place_Order_Payment_Fails_Then_Order_Placed() {
    Order order = getMockOrder();
    OrderRequest orderRequest = getMockOrderRequest();

    when(orderRepository.save(any(Order.class)))
        .thenReturn(order);
    when(productService.reduceQuantity(anyLong(),anyLong()))
        .thenReturn(new ResponseEntity<Void>(HttpStatus.OK));
    when(paymentService.doPayment(any(PaymentRequest.class)))
        .thenThrow(new RuntimeException());

    long orderId = orderService.placeOrder(orderRequest);

    verify(orderRepository, times(2))
        .save(any());
    verify(productService, times(1))
        .reduceQuantity(anyLong(),anyLong());
    verify(paymentService, times(1))
        .doPayment(any(PaymentRequest.class));

    assertEquals(order.getId(), orderId);
    assertEquals(order.getOrderStatus(), "PAYMENT_FAILED");
  }

  private OrderRequest getMockOrderRequest() {
    return OrderRequest.builder()
                       .productId(2)
                       .quantity(10)
                       .paymentMode(PaymentMode.CASH)
                       .totalAmount(100)
                       .build();
  }


  private PaymentResponse getMockPaymentResponse() {
    return PaymentResponse.builder()
                          .paymentId(1)
                          .paymentMode(PaymentMode.CASH)
                          .paymentDate(Instant.now())
                          .amount(200)
                          .status("ACCEPTED")
                          .build();
  }

  private ProductResponse getMockProductResponse() {
    return ProductResponse.builder()
                          .productId(2)
                          .name("IPhone")
                          .quantity(2)
                          .price(1400)
                          .quantity(200)
                          .build();
  }

  private Order getMockOrder() {
    return Order.builder()
                .id(1)
                .orderStatus("PLACED")
                .orderDate(Instant.now())
                .amount(100)
                .quantity(200)
                .productId(2)
                .build();
  }
}
