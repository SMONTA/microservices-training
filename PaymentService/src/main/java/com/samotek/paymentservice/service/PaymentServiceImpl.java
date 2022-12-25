package com.samotek.paymentservice.service;

import com.samotek.paymentservice.entity.TransactionDetails;
import com.samotek.paymentservice.model.PaymentRequest;
import com.samotek.paymentservice.reporsitory.TransactionDetailsRepository;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * @author Saber Montassar [montassar.saber1@gmail.com]
 * @date 12/25/22
 */
@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService {

  @Autowired
  private TransactionDetailsRepository transactionDetailsRepository;

  @Override
  public long doPayment(PaymentRequest request) {
    TransactionDetails transaction = TransactionDetails.builder()
                                                   .amount(request.getAmount())
                                                   .orderId(request.getOrderId())
                                                   .paymentMode(request.getPaymentMode().name())
                                                   .paymentDate(Instant.now())
                                                   .paymentStatus("SUCCESS")
                                                   .referenceNumber(request.getReferenceNumber())
                                                   .build();
    transactionDetailsRepository.save(transaction);
    log.info("Transaction completed with Id {}", transaction.getId());
    return transaction.getId();
  }
}
