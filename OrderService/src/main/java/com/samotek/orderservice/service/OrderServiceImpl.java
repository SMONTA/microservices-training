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
import com.samotek.orderservice.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

/**
 * @author Saber Montassar [montassar.saber1@gmail.com]
 * @date 12/9/22
 */
@Service
@Log4j2
public class OrderServiceImpl implements OrderService {

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private ProductService productService;

  @Autowired
  private PaymentService paymentService;

  @Autowired
  private RestTemplate restTemplate;

  @Override
  public long placeOrder(OrderRequest request) {

//    Order Entity -> Save the data with status order
//    Product Service -> Block Products (reduce quantity)
//    Payment Service: Payments -> Success -> COMPLETE, Else CANCELLED
    log.info("Placing order request: {}", request);
    productService.reduceQuantity(request.getProductId(), request.getQuantity());
    log.info("Creating an order with status CREATED");
    var order = Order.builder()
                     .productId(request.getProductId())
                     .amount(request.getTotalAmount())
                     .orderDate(Instant.now())
                     .orderStatus("CREATED")
                     .quantity(request.getQuantity())
                     .build();

    orderRepository.save(order);
    log.info("Order successfully created with id: {}", order.getId());
    var paymentRequest = PaymentRequest.builder()
                                       .orderId(order.getId())
                                       .amount(order.getAmount())
                                       .paymentMode(request.getPaymentMode())
//                                       .referenceNumber("eee")
                                       .build();
    String orderStatus = null;
    try {
      paymentService.doPayment(paymentRequest);
      log.info("Payment done successfully. Changing the order status to PLACED");
      orderStatus = "PLACED";
    }
    catch (Exception e) {
      log.error("Error occurred in payment. Changing order status to PAYMENT_FAILED");
      orderStatus = "PAYMENT_FAILED";
    }
    order.setOrderStatus(orderStatus);
    orderRepository.save(order);
    log.info("Creating a payment request with status {}", orderStatus);
    return order.getId();
  }

  @Override
  public OrderResponse getOrderDetails(long orderId) {
//    getting order details
    log.info("Getting order details for the order id {}", orderId);
    var orderEntity = orderRepository.findById(orderId)
                                     .orElseThrow(() -> new CustomException("No order found that match the id: "
                                                                            + orderId, "ORDER_NOT_FOUND", 500));
//    getting product details
    log.info("Fetching product details for the product id: {} ", orderEntity.getProductId());
    var pResponse = restTemplate.getForObject(
        "http://PRODUCT-SERVICE/products/v1/" + orderEntity.getProductId(),
        ProductResponse.class);

    OrderResponse.ProductDetails pDetails =
        OrderResponse.ProductDetails.builder()
                                    .productId(pResponse.getProductId())
                                    .productName(pResponse.getName())
                                    .quantity(pResponse.getQuantity())
                                    .price(pResponse.getPrice())
                                    .build();

//    get PaymentDetails
    log.info("Fetching payment details of the order {}", orderId);
    var paymentResponse = restTemplate.getForObject(
        "http://PAYMENT-SERVICE/payment/v1/order/" + orderId,
        PaymentResponse.class
    );
    var paymentDetails = OrderResponse.PaymentDetails.builder()
                                                     .paymentId(paymentResponse.getPaymentId())
                                                     .paymentMode(paymentResponse.getPaymentMode())
                                                     .paymentDate(paymentResponse.getPaymentDate())
                                                     .paymentStatus(paymentResponse.getStatus())
                                                     .build();

    return OrderResponse.builder()
                        .orderId(orderId)
                        .amount(orderEntity.getAmount())
                        .orderDate(orderEntity.getOrderDate())
                        .orderStatus(orderEntity.getOrderStatus())
                        .productDetails(pDetails)
                        .paymentDetails(paymentDetails)
                        .build();

  }
}
