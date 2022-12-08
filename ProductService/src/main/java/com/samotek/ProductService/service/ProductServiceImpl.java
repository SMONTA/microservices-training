package com.samotek.ProductService.service;

import com.samotek.ProductService.entity.Product;
import com.samotek.ProductService.execption.ProductServiceCustomException;
import com.samotek.ProductService.model.ProductRequest;
import com.samotek.ProductService.model.ProductResponse;
import com.samotek.ProductService.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Saber Montassar [montassar.saber1@gmail.com]
 * @date 12/8/22
 */

@Service
@Log4j2
public class ProductServiceImpl implements ProductService {

  @Autowired
  private ProductRepository productRepository;

  @Override
  public long addProduct(ProductRequest productRequest) {
    log.info("Adding Product ...");
    var product = Product.builder()
                         .productName(productRequest.getName())
                         .price(productRequest.getPrice())
                         .quantity(productRequest.getQuantity())
                         .build();
    var pEntity = productRepository.save(product);
    log.info("Product created with id {}", pEntity.getProductId());
    return pEntity.getProductId();
  }

  @Override
  public ProductResponse getProductById(long productId) {
    log.info("Fetching product with id: {}", productId);
    var referenceById = productRepository.findById(productId)
                                         .orElseThrow(() -> {
                                           var errMsg = String.format("Product with id %d not found.", productId);
                                           log.error(errMsg);
                                           return new ProductServiceCustomException(errMsg, "PRODUCT_NOT_FOUND");
                                         });
//    Only works when attributes matches between src/dest
//    var pResponse = new ProductResponse();
//    BeanUtils.copyProperties(referenceById, pResponse);
//    return pResponse;
    return ProductResponse.builder()
                          .productId(referenceById.getProductId())
                          .name(referenceById.getProductName())
                          .price(referenceById.getPrice())
                          .quantity(referenceById.getQuantity())
                          .build();
  }
}
