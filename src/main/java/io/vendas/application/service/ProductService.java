package io.vendas.application.service;

import io.vendas.application.dto.ProductDTO;

public interface ProductService {

    ProductDTO createProduct(ProductDTO productDTO);

    ProductDTO getProduct(Integer id);

    void updateProduct(Integer id, ProductDTO productDTO);

    void deleteProduct(Integer id);

}
