package com.samotek.ProductService.service;

import com.samotek.ProductService.model.ProductRequest;

/**
 * @author Saber Montassar [montassar.saber1@gmail.com]
 * @date 12/8/22
 */
public interface ProductService {

  long addProduct(ProductRequest productRequest);
}
