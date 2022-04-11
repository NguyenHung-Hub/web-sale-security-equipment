package com.metan.websalesecurityequipment.service;

import com.metan.websalesecurityequipment.model.Product;

import java.util.List;

public interface ProductService {
    public List<Product> findAll();
    public Product findProductById(String theId);
    public Product saveProduct(Product product);
    public void deleteProduct(Product product);
    public void deleteProductById(String theId);
}
