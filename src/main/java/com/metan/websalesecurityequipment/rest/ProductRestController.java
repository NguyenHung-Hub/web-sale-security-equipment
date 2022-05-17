package com.metan.websalesecurityequipment.rest;

import com.metan.websalesecurityequipment.model.Product;
import com.metan.websalesecurityequipment.model.request.ProductFileRequest;
import com.metan.websalesecurityequipment.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    private final ProductService productService;

    @Autowired
    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> findAllProducts() {
        return productService.findAll();
    }

    @GetMapping("/{theId}")
    public Product findProductById(@PathVariable String theId) {
        return productService.findProductById(theId);
    }

    @PostMapping
    public Product saveProduct(@Validated @RequestBody Product product) {
        return productService.saveProduct(product);
    }

}
