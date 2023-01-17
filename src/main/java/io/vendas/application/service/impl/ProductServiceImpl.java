package io.vendas.application.service.impl;

import io.vendas.application.dto.ProductDTO;
import io.vendas.application.entity.Product;
import io.vendas.application.repository.ProductRepository;
import io.vendas.application.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private static final String PRODUCT_NOT_FOUND = "Product not found";

    private final ProductRepository productRepository;

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product productSaved = productRepository.save(new Product(null, productDTO.getDescription(), productDTO.getPrice()));
        productDTO.setId(productSaved.getId());
        return productDTO;
    }

    @Override
    public ProductDTO getProduct(Integer id) {
        ProductDTO productDTO = new ProductDTO();
        productRepository.findById(id)
                .map(product -> {
                    productDTO.setId(product.getId());
                    productDTO.setPrice(product.getPrice());
                    productDTO.setDescription(product.getDescription());
                    return product;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, PRODUCT_NOT_FOUND));
        return productDTO;
    }

    @Override
    public void updateProduct(Integer id, ProductDTO productDTO) {
        productRepository.findById(id)
                .map(product -> {
                    product.setPrice(productDTO.getPrice());
                    product.setDescription(productDTO.getDescription());
                    productRepository.save(product);
                    return product;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, PRODUCT_NOT_FOUND));
    }

    @Override
    public void deleteProduct(Integer id) {
        productRepository.findById(id)
                .map(product -> {
                    productRepository.deleteById(product.getId());
                    return product;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, PRODUCT_NOT_FOUND));
    }
}
