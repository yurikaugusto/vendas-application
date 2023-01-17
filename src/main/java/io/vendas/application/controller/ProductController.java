package io.vendas.application.controller;

import io.vendas.application.dto.ProductDTO;
import io.vendas.application.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO createProduct(@RequestBody ProductDTO productDTO){
        return productService.createProduct(productDTO);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO getProduct(@PathVariable Integer id) {
        return productService.getProduct(id);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduct(@PathVariable Integer id, @RequestBody ProductDTO productDTO){
        productService.updateProduct(id, productDTO);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Integer id){
        productService.deleteProduct(id);
    }
}
