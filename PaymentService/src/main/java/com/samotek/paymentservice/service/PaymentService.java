package com.samotek.paymentservice.service;

import com.samotek.paymentservice.model.PaymentRequest;

/**
 * @author Saber Montassar [montassar.saber1@gmail.com]
 * @date 12/25/22
 */
public interface PaymentService {

  long doPayment(PaymentRequest request);
}
