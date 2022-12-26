package com.samotek.paymentservice.reporsitory;

import com.samotek.paymentservice.entity.TransactionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Saber Montassar [montassar.saber1@gmail.com]
 * @date 12/25/22
 */
@Repository
public interface TransactionDetailsRepository extends JpaRepository<TransactionDetails, Long> {

  Optional<TransactionDetails> findByOrderId(long orderId);
}
