package com.samotek.orderservice.repository;

import com.samotek.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Saber Montassar [montassar.saber1@gmail.com]
 * @date 12/9/22
 */

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
  
}
