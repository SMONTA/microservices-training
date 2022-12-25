package com.samotek.paymentservice.reporsitory;

import com.samotek.paymentservice.entity.TransactionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Saber Montassar [montassar.saber1@gmail.com]
 * @date 12/25/22
 */
@Repository
public interface TransactionDetailsRepository extends JpaRepository<TransactionDetails, Long> {
}
