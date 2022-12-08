package com.samotek.ProductService.repository;

import com.samotek.ProductService.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Saber Montassar [montassar.saber1@gmail.com]
 * @date 12/8/22
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
