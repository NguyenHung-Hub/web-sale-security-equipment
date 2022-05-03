package com.metan.websalesecurityequipment.service;

import com.metan.websalesecurityequipment.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    public List<Product> findAll();

    public Product findProductById(String theId);

    public Product saveProduct(Product product);

    public void deleteProduct(Product product);

    public void deleteProductById(String theId);

    public List<Product> findTopProduct();

    //Hao
    List<Product> findByNameContaining(String name);

    Page<Product> findAll(Pageable pageable);

    Page<Product> findByNameContaining(String name, Pageable pageable);

    public Product findBySlug(String slug);
}
