package com.samotek.ProductService.controller;

import com.samotek.ProductService.model.ProductRequest;
import com.samotek.ProductService.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Saber Montassar [montassar.saber1@gmail.com]
 * @date 12/8/22
 */
@RestController
@RequestMapping("/products/v1/")
public class ProductController {

  @Autowired
  private ProductService productService;


  @PostMapping
  public ResponseEntity<Long> addProduct(@RequestBody ProductRequest productRequest){
    long productId = productService.addProduct(productRequest);
    return new ResponseEntity<>(productId, HttpStatus.CREATED);
  }


}
